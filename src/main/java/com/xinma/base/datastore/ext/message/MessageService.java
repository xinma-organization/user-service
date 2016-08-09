package com.xinma.base.datastore.ext.message;

import java.util.List;

import com.xinma.base.datastore.exceptions.MessageException;
import com.xinma.base.datastore.model.message.QueueMessageTO;

/**
 * 消息队列相关操作接口定义
 * 
 * @author Alauda
 *
 * @date Jul 21, 2016
 *
 */
public interface MessageService {

	/**
	 * 向消息队列发送一条消息
	 * 
	 * @param queueName
	 *            队列名
	 * @param message
	 *            队列消息对象
	 * @return 消息对象
	 * @throws MessageException
	 *             操作失败，抛出异常
	 */
	QueueMessageTO putMessage(String queueName, final QueueMessageTO message) throws MessageException;

	/**
	 * 向消息队列批量发送消息
	 * 
	 * @param queueName
	 *            队列名
	 * @param messages
	 *            待发送的消息
	 * @return 发送成功的消息
	 * @throws MessageException
	 *             操作失败，抛出异常
	 */
	List<QueueMessageTO> batchPutMessage(String queueName, final List<QueueMessageTO> messages) throws MessageException;

	/**
	 * 从队列中取出一条消息
	 * 
	 * @param queueName
	 *            队列名
	 * @param deleteMessage
	 *            true表示从队列中取出消息后立即删除消息，false表示取出消息后不删除消息
	 * @return 从队列中取出一条消息，如果队列中没有消息返回null
	 * @throws MessageException
	 *             操作失败，抛出异常
	 */
	QueueMessageTO popMessage(String queueName, boolean deleteMessage) throws MessageException;

	/**
	 * 从队列中取出一条消息
	 * 
	 * @param queueName
	 *            队列名
	 * @param deleteMessage
	 *            true表示从队列中取出消息后立即删除消息，false表示取出消息后不删除消息
	 * @param waitSeconds
	 *            waitSeconds 长轮询等待时间，单位是秒
	 * @return 从队列中取出一条消息，如果队列中没有消息返回null
	 * @throws MessageException
	 *             操作失败，抛出异常
	 */
	QueueMessageTO popMessage(String queueName, boolean deleteMessage, int waitSeconds) throws MessageException;

	/**
	 * 批量获取队列中的消息
	 * 
	 * @param queueName
	 *            队列名
	 * @param deleteMessage
	 *            true表示从队列中取出消息后立即删除消息，false表示取出消息后不删除消息
	 * @param batchSize
	 *            本次最多获取消息的条数
	 * @return 消息列表,如果队列中没有消息返回null
	 * @throws MessageException
	 *             操作失败，抛出异常
	 */
	List<QueueMessageTO> batchPopMessage(String queueName, boolean deleteMessage, int batchSize)
			throws MessageException;

	/**
	 * 批量获取队列中的消息
	 * 
	 * @param queueName
	 *            队列名
	 * @param deleteMessage
	 *            true表示从队列中取出消息后立即删除消息，false表示取出消息后不删除消息
	 * @param batchSize
	 *            本次最多获取消息的条数
	 * @param waitSeconds
	 *            长轮询等待时间，单位是秒
	 * @return 消息列表,如果队列中没有消息返回null
	 * @throws MessageException
	 *             操作失败，抛出异常
	 */
	List<QueueMessageTO> batchPopMessage(String queueName, boolean deleteMessage, int batchSize, int waitSeconds)
			throws MessageException;

	/**
	 * 从队列中删除一条消息
	 * 
	 * @param queueName
	 *            队列名
	 * @param messageId
	 *            消息Id
	 * @throws MessageException
	 *             操作失败，抛出异常
	 */
	void deleteMessage(String queueName, final String messageId) throws MessageException;

	/**
	 * 从队列中批量删除消息
	 * 
	 * @param queueName
	 *            队列名
	 * @param messageIds
	 *            消息Id
	 * @throws MessageException
	 *             操作失败，抛出异常
	 */
	void batchDeleteMessage(String queueName, final List<String> messageIds) throws MessageException;
}
