package com.xinma.base.datastore.oldmodel.tag;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xinma.base.datastore.oldmodel.user.UserAccountDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 标签查询记录日志信息数据传输对象
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryRecordDTO implements Serializable {

	private static final long serialVersionUID = -5312902417223468651L;

	/**
	 * time,标签扫码时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("t")
	private Date time;

	/**
	 * 标签扫码次数
	 */
	@JsonProperty("st")
	private int numberOfScanTimes = 0;

	/**
	 * location,扫码地址
	 */
	@JsonProperty("l")
	private LocationDTO location;

	/**
	 * account,标签被那个用户查询的
	 */
	@JsonProperty("u")
	private UserAccountDTO account;

	/**
	 * 标签元信息
	 */
	@JsonProperty("tm")
	private TagMetadataDTO tagMetadata;

	/**
	 * 客户端浏览器user-agent
	 */
	@JsonProperty("ua")
	private String clientUserAgent;

	private boolean isNewAccount;

	/**
	 * 标签参与的活动Id
	 */
	@JsonProperty("p")
	private Integer promotionId;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getNumberOfScanTimes() {
		return numberOfScanTimes;
	}

	public void setNumberOfScanTimes(int numberOfScanTimes) {
		this.numberOfScanTimes = numberOfScanTimes;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public UserAccountDTO getAccount() {
		return account;
	}

	public void setAccount(UserAccountDTO account) {
		this.account = account;
	}

	public TagMetadataDTO getTagMetadata() {
		return tagMetadata;
	}

	public void setTagMetadata(TagMetadataDTO tagMetadata) {
		this.tagMetadata = tagMetadata;
	}

	public String getClientUserAgent() {
		return clientUserAgent;
	}

	public void setClientUserAgent(String clientUserAgent) {
		this.clientUserAgent = clientUserAgent;
	}

	public boolean isNewAccount() {
		return isNewAccount;
	}

	public void setNewAccount(boolean isNewAccount) {
		this.isNewAccount = isNewAccount;
	}

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
}
