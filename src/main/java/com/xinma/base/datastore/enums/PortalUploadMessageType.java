package com.xinma.base.datastore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * portal服务发送消息类型
 * 
 * @author Alauda
 *
 */
public enum PortalUploadMessageType implements DataStoreMessageType {
	/**
	 * 0:用户标签抽奖中兑奖信息
	 */
	AwardingInfoDTO(0, "用户标签中兑奖信息"),

	/**
	 * 1:用户积分抽奖中奖信息
	 */
	UserPointAwardingInfo(1, "用户中兑奖信息");

	Integer value = 0;

	String remark;

	PortalUploadMessageType(Integer value, String remark) {
		this.value = value;
		this.remark = remark;
	}

	@JsonValue
	public Integer getValue() {
		return value;
	}

	public String getRemark() {
		return remark;
	}

	@JsonCreator
	public static PortalUploadMessageType valueOf(int value) {
		switch (value) {
		case 0:
			return AwardingInfoDTO;

		default:
			return AwardingInfoDTO;
		}
	}

}
