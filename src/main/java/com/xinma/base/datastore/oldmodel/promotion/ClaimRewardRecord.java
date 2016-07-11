package com.xinma.base.datastore.oldmodel.promotion;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinma.base.datastore.oldmodel.tag.LocationDTO;
import com.xinma.base.datastore.oldmodel.tag.TagMetadataDTO;
import com.xinma.base.datastore.oldmodel.user.UserAccountDTO;

/**
 * 兑奖操作生成的兑奖记录，该记录追加到日志分析系统
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClaimRewardRecord implements Serializable {

	private static final long serialVersionUID = 3887312251461464429L;

	/**
	 * 兑奖状态
	 */
	@JsonProperty("s")
	private Integer status;

	/**
	 * 奖项信息Id
	 */
	@JsonProperty("r")
	private Integer rewardId;

	/**
	 * 奖品面值，单位为元
	 */
	@JsonProperty("ra")
	private Float rewardAmount;

	/**
	 * 活动信息Id
	 */
	@JsonProperty("p")
	private Integer promotionId;

	/**
	 * 兑奖用户信息
	 */
	@JsonProperty("a")
	private UserAccountDTO account;

	/**
	 * 兑奖时间
	 */
	@JsonProperty("t")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UserAccountDTO getAccount() {
		return account;
	}

	public void setAccount(UserAccountDTO account) {
		this.account = account;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getRewardId() {
		return rewardId;
	}

	public void setRewardId(Integer rewardId) {
		this.rewardId = rewardId;
	}

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
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

	public Float getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(Float rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
}
