package com.xinma.base.datastore.impl.aliyun.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinma.base.datastore.error.MessageError;
import com.xinma.base.datastore.exceptions.MessageException;
import com.xinma.base.datastore.ext.message.MessageService;
import com.xinma.base.datastore.model.message.QueueMessageTO;

/**
 * 阿里云MNS消息服务中间件实现业务消息队列
 * 
 * @author Alauda
 *
 * @date Jul 22, 2016
 *
 */
public class MessageServiceImpl implements MessageService {

	private MNSClient client;

	private final ObjectMapper mapper = new ObjectMapper();

	private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	private Map<String, MnsPoppedMsgRecord> poppedMsgMap = new ConcurrentHashMap<String, MnsPoppedMsgRecord>();

	private ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

	public MessageServiceImpl(MNSClient client) {
		super();
		this.client = client;
		// start a new Schedule
		scheduledExecutor.scheduleWithFixedDelay(new PoppedMessageExpiredTask(), 1, 2, TimeUnit.MINUTES);
	}

	@Override
	public QueueMessageTO putMessage(String queueName, final QueueMessageTO message) throws MessageException {
		try {
			CloudQueue queue = client.getQueueRef(queueName);
			message.setMessageId(UUID.randomUUID().toString());
			message.setSendTime(new Date());

			Message queueMessage = new Message(mapper.writeValueAsString(message));
			queue.putMessage(queueMessage);

		} catch (ClientException ce) {
			throw new MessageException(ce, MessageError.ClientExceptionErr, ce.getErrorCode());
		} catch (ServiceException se) {
			throw new MessageException(se, MessageError.ServiceException, se.getErrorCode());
		} catch (JsonProcessingException je) {
			throw new MessageException(je, MessageError.JsonProcessingExceptionErr);
		}

		return message;
	}

	@Override
	public List<QueueMessageTO> batchPutMessage(String queueName, final List<QueueMessageTO> messages)
			throws MessageException {
		try {
			CloudQueue queue = client.getQueueRef(queueName);

			List<Message> queueMessages = new ArrayList<Message>();
			Date time = new Date();
			for (QueueMessageTO message : messages) {
				message.setMessageId(UUID.randomUUID().toString());
				message.setSendTime(time);

				Message queueMessage = new Message(mapper.writeValueAsString(message));
				queueMessages.add(queueMessage);
			}

			List<Message> retQueueMessages = queue.batchPutMessage(queueMessages);

			// check if all messages send success.
			if (retQueueMessages.size() != messages.size()) {
				logger.error("阿里云batchPutMessage()方法操作失败。");
				List<QueueMessageTO> retMessages = new ArrayList<QueueMessageTO>();
				for (Message msg : retQueueMessages) {
					retMessages.add(mapper.readValue(msg.getMessageBodyAsString(), QueueMessageTO.class));
				}

				for (QueueMessageTO message : messages) {
					boolean isSuccessed = false;
					for (QueueMessageTO retMessage : retMessages) {
						if (message.getMessageId().equals(retMessage.getMessageId())) {
							isSuccessed = true;
							break;
						}
					}
					if (!isSuccessed) {
						MessageException me = new MessageException("batchPutMessage操作部分消息发送失败。",
								MessageError.BatchPutErr, message.getMessageId());
						logger.error("batchPutMessage发送失败消息：" + mapper.writeValueAsString(message), me);
					}
				}

				return retMessages;
			}

		} catch (ClientException ce) {
			throw new MessageException(ce, MessageError.ClientExceptionErr, ce.getErrorCode());
		} catch (ServiceException se) {
			throw new MessageException(se, MessageError.ServiceException, se.getErrorCode());
		} catch (JsonProcessingException je) {
			throw new MessageException(je, MessageError.JsonProcessingExceptionErr);
		} catch (IOException ie) {
			throw new MessageException(ie, MessageError.UnknownErr);
		}

		return messages;
	}

	private QueueMessageTO dealPoppedMessage(CloudQueue queue, Message queueMessage, boolean deleteMessage)
			throws JsonParseException, JsonMappingException, IOException {
		if (queueMessage == null) {
			return null;
		}

		QueueMessageTO message = mapper.readValue(queueMessage.getMessageBodyAsString(), QueueMessageTO.class);
		if (deleteMessage) {
			queue.deleteMessage(queueMessage.getReceiptHandle());
		} else {
			MnsPoppedMsgRecord poppedRecord = new MnsPoppedMsgRecord(queueMessage.getReceiptHandle(),
					queueMessage.getNextVisibleTime());

			poppedMsgMap.put(message.getMessageId(), poppedRecord);
		}

		return message;
	}

	@Override
	public QueueMessageTO popMessage(String queueName, boolean deleteMessage) throws MessageException {
		try {
			CloudQueue queue = client.getQueueRef(queueName);

			return dealPoppedMessage(queue, queue.popMessage(), deleteMessage);

		} catch (ClientException ce) {
			throw new MessageException(ce, MessageError.ClientExceptionErr, ce.getErrorCode());
		} catch (ServiceException se) {
			throw new MessageException(se, MessageError.ServiceException, se.getErrorCode());
		} catch (JsonProcessingException je) {
			throw new MessageException(je, MessageError.JsonProcessingExceptionErr);
		} catch (IOException ie) {
			throw new MessageException(ie, MessageError.UnknownErr);
		}
	}

	@Override
	public QueueMessageTO popMessage(String queueName, boolean deleteMessage, int waitSeconds) throws MessageException {
		try {
			CloudQueue queue = client.getQueueRef(queueName);

			return dealPoppedMessage(queue, queue.popMessage(waitSeconds), deleteMessage);

		} catch (ClientException ce) {
			throw new MessageException(ce, MessageError.ClientExceptionErr, ce.getErrorCode());
		} catch (ServiceException se) {
			throw new MessageException(se, MessageError.ServiceException, se.getErrorCode());
		} catch (JsonProcessingException je) {
			throw new MessageException(je, MessageError.JsonProcessingExceptionErr);
		} catch (IOException ie) {
			throw new MessageException(ie, MessageError.UnknownErr);
		}
	}

	@Override
	public List<QueueMessageTO> batchPopMessage(String queueName, boolean deleteMessage, int batchSize)
			throws MessageException {

		List<QueueMessageTO> messages = null;
		try {
			CloudQueue queue = client.getQueueRef(queueName);
			List<Message> queueMessages = queue.batchPopMessage(batchSize);
			if (queueMessages != null) {
				messages = new ArrayList<QueueMessageTO>();
				for (Message queueMessage : queueMessages) {
					messages.add(dealPoppedMessage(queue, queueMessage, deleteMessage));
				}
			}

			return messages;
		} catch (ClientException ce) {
			throw new MessageException(ce, MessageError.ClientExceptionErr, ce.getErrorCode());
		} catch (ServiceException se) {
			throw new MessageException(se, MessageError.ServiceException, se.getErrorCode());
		} catch (JsonProcessingException je) {
			throw new MessageException(je, MessageError.JsonProcessingExceptionErr);
		} catch (IOException ie) {
			throw new MessageException(ie, MessageError.UnknownErr);
		}
	}

	@Override
	public List<QueueMessageTO> batchPopMessage(String queueName, boolean deleteMessage, int batchSize, int waitSeconds)
			throws MessageException {
		List<QueueMessageTO> messages = null;
		try {
			CloudQueue queue = client.getQueueRef(queueName);
			List<Message> queueMessages = queue.batchPopMessage(batchSize, waitSeconds);
			if (queueMessages != null) {
				messages = new ArrayList<QueueMessageTO>();
				for (Message queueMessage : queueMessages) {
					messages.add(dealPoppedMessage(queue, queueMessage, deleteMessage));
				}
			}

			return messages;
		} catch (ClientException ce) {
			throw new MessageException(ce, MessageError.ClientExceptionErr, ce.getErrorCode());
		} catch (ServiceException se) {
			throw new MessageException(se, MessageError.ServiceException, se.getErrorCode());
		} catch (JsonProcessingException je) {
			throw new MessageException(je, MessageError.JsonProcessingExceptionErr);
		} catch (IOException ie) {
			throw new MessageException(ie, MessageError.UnknownErr);
		}
	}

	@Override
	public void deleteMessage(String queueName, String messageId) throws MessageException {
		try {
			MnsPoppedMsgRecord record = poppedMsgMap.get(messageId);
			if (record == null) {
				// throw msgId Invalid
				MessageException exception = new MessageException(MessageError.InvalidMessageIdErr);
				logger.error("消息ID " + messageId + "不存在或者已过期。", exception);
				return;
			} else {
				if (new Date().after(record.getNextVisibleTime())) {
					MessageException exception = new MessageException(MessageError.InvalidMessageIdErr);
					logger.error("消息ID " + messageId + "已过期。", exception);
					poppedMsgMap.remove(messageId);
					return;
				}

				CloudQueue queue = client.getQueueRef(queueName);
				queue.deleteMessage(record.getReceiptHandle());
				poppedMsgMap.remove(messageId);
			}
		} catch (ClientException ce) {
			throw new MessageException(ce, MessageError.ClientExceptionErr, ce.getErrorCode());
		} catch (ServiceException se) {
			throw new MessageException(se, MessageError.ServiceException, se.getErrorCode());
		}
	}

	@Override
	public void batchDeleteMessage(String queueName, List<String> messageIds) throws MessageException {
		try {
			List<String> receiptHandles = new ArrayList<String>();
			for (String messageId : messageIds) {
				MnsPoppedMsgRecord record = poppedMsgMap.get(messageId);
				if (record == null) {
					MessageException exception = new MessageException(MessageError.InvalidMessageIdErr);
					logger.error("消息ID " + messageId + "不存在或者已过期。", exception);
				} else {
					if (new Date().after(record.getNextVisibleTime())) {
						MessageException exception = new MessageException(MessageError.InvalidMessageIdErr);
						logger.error("消息ID " + messageId + "已过期。", exception);
					}
					receiptHandles.add(record.getReceiptHandle());
				}
			}

			if (receiptHandles.size() > 0) {
				CloudQueue queue = client.getQueueRef(queueName);
				queue.batchDeleteMessage(receiptHandles);
			}

			// remove msgs record from map
			for (String messageId : messageIds) {
				poppedMsgMap.remove(messageId);
			}
		} catch (ClientException ce) {
			throw new MessageException(ce, MessageError.ClientExceptionErr, ce.getErrorCode());
		} catch (ServiceException se) {
			throw new MessageException(se, MessageError.ServiceException, se.getErrorCode());
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		scheduledExecutor.shutdown();
		if (scheduledExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
			logger.info("MessageServiceImpl对象内scheduledExecutor正常关闭。");
		} else {
			scheduledExecutor.shutdownNow();
			MessageException ex = new MessageException(MessageError.FailedToStopSchedulerErr);
			logger.error("MessageServiceImpl对象内scheduledExecutor非正常关闭", ex);
		}

		// destroy the maps
		Iterator<Entry<String, MnsPoppedMsgRecord>> it = poppedMsgMap.entrySet().iterator();
		while (it.hasNext()) {
			MessageException ex = new MessageException(MessageError.PoppedMsgNotDeleteErr);
			logger.error(
					"MessageId " + it.next().getKey() + " not deleted when the MessageServiceImpl Object destroyed.",
					ex);
		}
	}

	class PoppedMessageExpiredTask implements Runnable {

		@Override
		public void run() {
			Iterator<Entry<String, MnsPoppedMsgRecord>> it = poppedMsgMap.entrySet().iterator();

			while (it.hasNext()) {
				Entry<String, MnsPoppedMsgRecord> entry = it.next();
				if (new Date().after(entry.getValue().getNextVisibleTime())) {
					it.remove();
				}
			}
		}

	}
}
