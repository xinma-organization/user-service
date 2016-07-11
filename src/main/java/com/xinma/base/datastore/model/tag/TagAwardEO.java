package com.xinma.base.datastore.model.tag;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xinma.base.datastore.model.location.AddressEO;
import com.xinma.base.datastore.model.user.AccountRow;

/**
 * 标签奖品信息
 * 
 * @author Alauda
 *
 * @date 2015年6月30日
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagAwardEO {

	private Integer promotionId;

	private Integer awardId;

	private Integer awardType;

	private Integer awardAmount;

	/**
	 * 当前标签奖品状态
	 */
	private int status;

	private Date winTime;

	/**
	 * 奖品兑奖时间，(无论成功失败，时间都更新)
	 */
	private Date claimTime;

	private AccountRow user;

	private AddressEO address;

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public Integer getAwardId() {
		return awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}

	public Integer getAwardType() {
		return awardType;
	}

	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}

	public Integer getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(Integer awardAmount) {
		this.awardAmount = awardAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getWinTime() {
		return winTime;
	}

	public void setWinTime(Date winTime) {
		this.winTime = winTime;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	public AccountRow getUser() {
		return user;
	}

	public void setUser(AccountRow user) {
		this.user = user;
	}

	public AddressEO getAddress() {
		return address;
	}

	public void setAddress(AddressEO address) {
		this.address = address;
	}
}
