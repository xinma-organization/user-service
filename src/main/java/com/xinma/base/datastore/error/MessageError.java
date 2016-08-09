package com.xinma.base.datastore.error;

import com.xinma.base.core.error.CustomError;

/**
 * Message存储错误代码定义
 * 
 * @author Alauda
 *
 * @date 2015年6月29日
 *
 */
public enum MessageError implements CustomError {

	UnknownErr("datastore-mnsmessage-001", "unknown excption when access message storage."),

	ClientExceptionErr("datastore-mnsmessage-002",
			"Something wrong with the network connection between client and MNS service."),

	ServiceException("datastore-mnsmessage-003",
			"Something wrong with the interaction of MNS service, please check the specific errorcode."),

	JsonProcessingExceptionErr("datastore-mnsmessage-004", "MNS operation JsonProcessingException error."),

	BatchPutErr("datastore-mnsmessage-005", "MNS batch put message operation error."),

	InvalidMessageIdErr("datastore-mnsmessage-006", "invalid messageId when delete a message from mns queue."),

	FailedToStopSchedulerErr("datastore-mnsmessage-007",
			"failed to stop the scheduledExecutor in MessageServiceImpl Object."),

	PoppedMsgNotDeleteErr("datastore-mnsmessage-008",
			"popped message not deleted when the MessageServiceImpl Object destroyed.");

	String value;

	String description;

	MessageError(String value, String description) {
		this.value = value;
		this.description = description;
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public String description() {
		return description;
	}

}
