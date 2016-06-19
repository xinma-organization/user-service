package com.xinma.base.datastore.model.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 活动积分明细实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromotionPointDetailRecord {
	/**
	 * 积分状态，收益或消耗，0为收益
	 */
	@JsonProperty("s")
	private Integer status;

	/**
	 * 收益或消耗状态明细， 参照PointDetailStatus
	 */
	@JsonProperty("ds")
	private Integer detailStatus;

	@JsonProperty("v")
	private Integer value;

	/**
	 * 时间戳
	 */
	@JsonProperty("t")
	private Date timestamp;

	/**
	 * 标签序号
	 */
	@JsonProperty("ct")
	private Long clearTag;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(Integer detailStatus) {
		this.detailStatus = detailStatus;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Long getClearTag() {
		return clearTag;
	}

	public void setClearTag(Long clearTag) {
		this.clearTag = clearTag;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public PromotionPointDetailRecord(Integer status, Integer detailStatus, Integer value, Date timestamp) {
		super();
		this.status = status;
		this.detailStatus = detailStatus;
		this.value = value;
		this.timestamp = timestamp;
	}

	public PromotionPointDetailRecord() {
		super();
	}
}
