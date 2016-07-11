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
 * 用户每次抽奖日志记录，将该消息追加到日志分析系统
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LotteryRecord implements Serializable {

	private static final long serialVersionUID = 359299111225082937L;

	/**
	 * 抽奖结果状态
	 */
	@JsonProperty("s")
	private Integer status;

	/**
	 * 抽奖活动Id
	 */
	@JsonProperty("p")
	private Integer promotionId;

	/**
	 * 中奖奖品Id
	 */
	@JsonProperty("r")
	private Integer rewardId;

	/**
	 * 抽奖用户信息
	 */
	@JsonProperty("u")
	private UserAccountDTO account;

	/**
	 * 抽奖时间
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

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
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

	public LotteryRecord() {
		super();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRewardId() {
		return rewardId;
	}

	public void setRewardId(Integer rewardId) {
		this.rewardId = rewardId;
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

	public LotteryRecord(TagMetadataDTO tagMetadata, Integer promotionId, UserAccountDTO account, Date time,
			int status) {
		super();
		this.tagMetadata = tagMetadata;
		this.promotionId = promotionId;
		this.account = account;
		this.time = time;
		this.status = status;
	}
}
