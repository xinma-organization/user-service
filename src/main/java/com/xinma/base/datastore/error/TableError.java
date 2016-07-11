package com.xinma.base.datastore.error;

import com.xinma.base.core.error.CustomError;

/**
 * Table存储错误代码定义
 * 
 * @author Alauda
 *
 * @date 2015年6月29日
 *
 */
public enum TableError implements CustomError {

	UnknownErr("datastore-table-001", "Unknown excption when access table storage."),

	NetWorkErr("datastore-table-002", "Network excption when access table storage."),

	OtsClientErr("datastore-table-003", "OTSClient excption when access table storage."),

	OTSErr("datastore-table-004", "OTSExcption when access table storage."),

	ReachRetryLimitErr("datastore-table-005", "reach ots retry limit error.");

	String value;

	String description;

	TableError(String value, String description) {
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
