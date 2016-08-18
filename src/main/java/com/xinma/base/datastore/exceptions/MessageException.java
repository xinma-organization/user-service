package com.xinma.base.datastore.exceptions;

import com.xinma.base.core.error.CustomError;
import com.xinma.base.core.exceptions.CustomException;

/**
 * Message存储访问异常，继承自DataStoreException
 * 
 * @author Alauda
 *
 * @date 2015年5月19日
 *
 */
public class MessageException extends CustomException {

	private static final long serialVersionUID = -8721524556504784780L;

	public MessageException() {
		super();
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageException(Throwable cause) {
		super(cause);
	}

	public MessageException(CustomError error, String... params) {
		super(error, params);
	}

	public MessageException(String message, CustomError error, String... params) {
		super(message, error, params);
	}

	public MessageException(Throwable cause, CustomError error, String... params) {
		super(cause, error, params);
	}
}
