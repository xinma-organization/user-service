package com.xinma.base.datastore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Etrace下载的消息类型
 * 
 * @author zhangyongyi
 *
 */
public enum EtraceDownloadMessageType implements EtraceMessageType {

	/**
	 * 0:促销活动定义
	 */
	PromotionDTO(0, "促销活动定义"),

	/**
	 * 1:企业微信公众号设置
	 */
	WechatSetting(1, "企业微信公众号设置"),

	/**
	 * 2:企业微信公众号设置
	 */
	WechatRedEnvelopSetting(2, "企业微信红包设置"),

	/**
	 * 3:企业产品信息
	 */
	ProductDTO(3, "企业产品信息"),

	/**
	 * 4:企业经销商信息
	 */
	FranchiserDTO(4, "企业经销商信息"),

	/**
	 * 5:微信红包重发信息
	 */
	WechatRedBagResendDTO(5, "微信红包重发信息");

	Integer value = 0;

	String remark;

	EtraceDownloadMessageType(Integer value, String remark) {
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
	public static EtraceDownloadMessageType valueOf(int value) {
		switch (value) {
		case 0:
			return PromotionDTO;
		case 1:
			return WechatSetting;
		case 2:
			return WechatRedEnvelopSetting;
		case 3:
			return ProductDTO;
		case 4:
			return FranchiserDTO;
		case 5:
			return WechatRedBagResendDTO;
		default:
			return PromotionDTO;
		}
	}

}
