package com.xinma.base.datastore.model.tag;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinma.base.datastore.model.user.UserAccountDTO;

/**
 * 记录标签抽奖信息
 * 
 * @author Hoctor
 *
 */
public class TagLotteryRecord implements Serializable {
	private static final long serialVersionUID = -303769251393583148L;

	/**
	 * 活动报名时间
	 */
	@JsonProperty("t")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date enrollTime;

	/**
	 * 活动报名用户
	 */
	@JsonProperty("u")
	private UserAccountDTO enrollAccount;

	public Date getEnrollTime() {
		return enrollTime;
	}

	public void setEnrollTime(Date enrollTime) {
		this.enrollTime = enrollTime;
	}

	public UserAccountDTO getEnrollAccount() {
		return enrollAccount;
	}

	public void setEnrollAccount(UserAccountDTO enrollAccount) {
		this.enrollAccount = enrollAccount;
	}

}
