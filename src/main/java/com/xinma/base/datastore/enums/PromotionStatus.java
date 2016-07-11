package com.xinma.base.datastore.enums;

/**
 * 促销活动的状态
 * 
 * @author zhangyongyi
 *
 */

public enum PromotionStatus {

	/**
	 * 0:新建
	 */
	Created(0),

	/**
	 * 1:已启动
	 */
	Actived(1),

	/**
	 * 2:进行中
	 */
	Progressing(2),

	/**
	 * 3:暂停
	 */
	Paused(3),

	/**
	 * 4:已结束
	 */
	Finished(4);

	int status;

	PromotionStatus(int status) {
		this.status = status;
	}

	public int getValue() {
		return status;
	}

}
