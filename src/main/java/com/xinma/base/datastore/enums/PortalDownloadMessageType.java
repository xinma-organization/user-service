package com.xinma.base.datastore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * portal服务获取消息类型
 * 
 * @author Alauda
 *
 */
public enum PortalDownloadMessageType implements DataStoreMessageType {

	ProductTO(0, "产品消息类型定义");

	Integer value = 0;

	String remark;

	PortalDownloadMessageType(Integer value, String remark) {
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
	public static PortalDownloadMessageType valueOf(int value) {
		switch (value) {
		case 0:
			return ProductTO;
		default:
			return ProductTO;
		}
	}

}
