package com.xinma.base.datastore.oldmodel.user;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户单条中奖记录实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAwardRecord implements Serializable {

	private static final long serialVersionUID = 4223021814418353215L;

	/**
	 * clearTag,标签
	 */
	@JsonProperty("t")
	private Long clearTag;

	/**
	 * promotionId,活动Id
	 */
	@JsonProperty("p")
	private int promotionId;

	/**
	 * rewardId,奖品Id
	 */
	@JsonProperty("r")
	private int rewardId;

	/**
	 * rewardCategory,奖品类别
	 */
	@JsonProperty("rc")
	private int rewardCategory;

	/**
	 * 奖品面值，单位为元
	 */
	@JsonProperty("ra")
	private Float rewardAmount;

	/**
	 * 交易类型(收入或支出，目前用于君乐宝君畅的红包记录和提现记录类型)，
	 */
	@JsonProperty("tt")
	private Integer tradeType = 0;
	/**
	 * 产品Id
	 */
	@JsonProperty("pd")
	private int productId;

	/**
	 * 奖品状态,对应RewardStatus枚举类
	 */
	@JsonProperty("s")
	private int status;

	/**
	 * winningTime,中奖时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("wt")
	private Date winningTime;

	/**
	 * cashTime,领奖时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("ct")
	private Date cashTime;

	/**
	 * extraBag, 存储自定义信息
	 */
	@JsonProperty("eb")
	private String extraBag;

	public Long getClearTag() {
		return clearTag;
	}

	public void setClearTag(Long clearTag) {
		this.clearTag = clearTag;
	}

	public int getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}

	public int getRewardId() {
		return rewardId;
	}

	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}

	public int getRewardCategory() {
		return rewardCategory;
	}

	public void setRewardCategory(int rewardCategory) {
		this.rewardCategory = rewardCategory;
	}

	public Float getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(Float rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Date getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Date winningTime) {
		this.winningTime = winningTime;
	}

	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExtraBag() {
		return extraBag;
	}

	public void setExtraBag(String extraBag) {
		this.extraBag = extraBag;
	}
}
