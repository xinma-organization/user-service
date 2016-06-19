package com.xinma.base.datastore.model.tag;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinma.base.datastore.model.user.UserAccountDTO;

/**
 * 记录标签中奖纪录实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagAwardRecordDTO implements Serializable {

	private static final long serialVersionUID = 4223021814418351445L;

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
	 * 中奖用户
	 */
	@JsonProperty("u")
	private UserAccountDTO account;

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
	 * order number, 快递单号
	 */
	@JsonProperty("o")
	private String orderNumber;

	/**
	 * express,快递公司
	 */
	@JsonProperty("e")
	private String express;

	/**
	 * 标签元数据
	 */
	@JsonProperty("tm")
	private TagMetadataDTO tagMetadata;

	/**
	 * location,消费者地理位置信息
	 */
	@JsonProperty("l")
	private LocationDTO location;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public UserAccountDTO getAccount() {
		return account;
	}

	public void setAccount(UserAccountDTO account) {
		this.account = account;
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public TagMetadataDTO getTagMetadata() {
		return tagMetadata;
	}

	public void setTagMetadata(TagMetadataDTO tagMetadata) {
		this.tagMetadata = tagMetadata;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}
}
