package com.xinma.base.datastore.enums;

public enum RewardStatus {
	/**
	 * 0:新中奖,还没有兑奖
	 */
	Enrolled(0),

	/**
	 * 1:领奖失败
	 */
	ClaimFailed(1),

	/**
	 * 2:领奖成功
	 */
	ClaimSuccess(2),

	/**
	 * 已登记
	 */
	Registered(3);

	int status;

	RewardStatus(int status) {
		this.status = status;
	}

	public int getValue() {
		return status;
	}
}
