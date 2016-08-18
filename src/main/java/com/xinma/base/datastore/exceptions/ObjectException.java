package com.xinma.base.datastore.exceptions;

import com.xinma.base.core.error.CustomError;
import com.xinma.base.core.exceptions.CustomException;

/**
 * Object存储访问异常，继承自DataStoreException
 * 
 * @author Alauda
 *
 * @date 2015年5月19日
 *
 */
public class ObjectException extends CustomException {

	private static final long serialVersionUID = -2080403315079064179L;

	public ObjectException() {
		super();
	}

	public ObjectException(String message) {
		super(message);
	}

	public ObjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectException(Throwable cause) {
		super(cause);
	}

	public ObjectException(CustomError error, String... params) {
		super(error, params);
	}

	public ObjectException(String message, CustomError error, String... params) {
		super(message, error, params);
	}

	public ObjectException(Throwable cause, CustomError error, String... params) {
		super(cause, error, params);
	}
}
