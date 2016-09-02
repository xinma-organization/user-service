package com.xinma.base.datastore.impl.aliyun.message;

import java.util.Date;

/**
 * 用于记录从MNS中pop出来的消息，用于后期删除消息
 * 
 * @author Alauda
 *
 * @date Jul 23, 2016
 *
 */
class MnsPoppedMsgRecord {

	private String receiptHandle;

	private Date nextVisibleTime;

	public String getReceiptHandle() {
		return receiptHandle;
	}

	public void setReceiptHandle(String receiptHandle) {
		this.receiptHandle = receiptHandle;
	}

	public Date getNextVisibleTime() {
		return nextVisibleTime;
	}

	public void setNextVisibleTime(Date nextVisibleTime) {
		this.nextVisibleTime = nextVisibleTime;
	}

	public MnsPoppedMsgRecord() {
		super();
	}

	public MnsPoppedMsgRecord(String receiptHandle, Date nextVisibleTime) {
		super();
		this.receiptHandle = receiptHandle;
		this.nextVisibleTime = nextVisibleTime;
	}
}
