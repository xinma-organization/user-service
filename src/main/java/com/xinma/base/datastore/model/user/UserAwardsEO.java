package com.xinma.base.datastore.model.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 用户奖品详情实体类
 * 
 * @author Alauda
 *
 * @date 2016年5月30日
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAwardsEO {

	private Long tagUniqueCode;

	private Integer promotionId;

	private Integer awardId;

	private Integer awardType;

	private Integer awardAmount;

	private Integer productId;

	private int status;

	private Date winTime;

	private Date claimTime;

	private String extraBag;

	public Long getTagUniqueCode() {
		return tagUniqueCode;
	}

	public void setTagUniqueCode(Long tagUniqueCode) {
		this.tagUniqueCode = tagUniqueCode;
	}

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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public String getExtraBag() {
		return extraBag;
	}

	public void setExtraBag(String extraBag) {
		this.extraBag = extraBag;
	}
}
