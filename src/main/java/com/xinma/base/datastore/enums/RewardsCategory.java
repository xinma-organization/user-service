package com.xinma.base.datastore.enums;

/**
 * 本系统中的扫码奖品类型定义
 * 
 * @author zhangyongyi
 *
 */
public enum RewardsCategory {

	/**
	 * 0:实物奖品
	 */
	PhysicalReward(0, "实物奖品"),

	/**
	 * 1:微信红包
	 */
	WechatRedEnvelopes(1, "微信红包"),

	/**
	 * 2:支付宝红包
	 */
	AlipayRedEnvelopes(2, "支付宝红包"),

	/**
	 * 3:淘宝流量钱包
	 */
	AlipayFlowCoupon(3, "淘宝流量钱包"),

	/**
	 * 4:手机话费
	 */
	MobileBillCoupon(4, "手机话费"),

	/**
	 * 5:银联现金券
	 */
	UnionPayCashCoupon(5, "银联现金券"),

	/**
	 * 6:积分
	 */
	Points(6, "积分"),

	/**
	 * 7:第三方电子券
	 */
	ThirdPartyElectronicCoupons(7, "第三方电子券");

	Integer value = 0;

	String remark;

	RewardsCategory(Integer value, String remark) {
		this.value = value;
		this.remark = remark;
	}

	public Integer getValue() {
		return value;
	}

	public String getRemark() {
		return remark;
	}

	public static RewardsCategory valueOf(int value) {
		switch (value) {
		case 0:
			return PhysicalReward;
		case 1:
			return WechatRedEnvelopes;
		case 2:
			return AlipayRedEnvelopes;
		case 3:
			return AlipayFlowCoupon;
		case 4:
			return MobileBillCoupon;
		case 5:
			return UnionPayCashCoupon;
		case 6:
			return Points;
		case 7:
			return ThirdPartyElectronicCoupons;

		default:
			return WechatRedEnvelopes;
		}
	}

}
