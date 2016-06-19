package com.xinma.base.datastore.enums;

/**
 * 并发操作时，事物锁状态
 * 
 * @author Hoctor
 *
 */
public enum OpreationLockStatus {

	/**
	 * 未锁定
	 */
	UnLock(0),

	/**
	 * 已锁定
	 */
	Locked(1);

	int status;

	OpreationLockStatus(int status) {
		this.status = status;
	}

	public int getValue() {
		return status;
	}
}
