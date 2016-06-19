package com.xinma.base.datastore.enums;

public enum UserCategory {

	/**
	 * 透云企业微信公众账号，该类型为与wechat,phone,QQ,淘宝等是一个级别的
	 */
	TyWechatPublicAccount("tp"),

	/**
	 * 微信企业公众账号,该账号为TyWechatPublicAccount和Wechat的子账号
	 */
	WechatPublicAccount("wp"),

	/**
	 * 微信号
	 */
	Wechat("w"),

	/**
	 * QQ
	 */
	QQ("q"),

	/**
	 * 淘宝
	 */
	Taobao("t"),

	/**
	 * 手机
	 */
	Phone("p"),

	/**
	 * 邮箱
	 */
	Email("e");

	String category;

	UserCategory(String category) {
		this.category = category;
	}

	public String getValue() {
		return this.category;
	}
}
