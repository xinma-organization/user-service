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

	Unknown("datastore-message-001", "unknown excption when access message storage.");

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
