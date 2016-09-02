package com.xinma.base.datastore.model.log;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinma.base.datastore.model.tag.TagBasicInfoEO;
import com.xinma.base.datastore.model.user.AccountRow;

/**
 * 标签扫码日志，用于数据分析系统用
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagQueryLogTO {

	@JsonProperty("tb")
	private TagBasicInfoEO tagBasicInfo;

	@JsonProperty("a")
	private AccountRow account;

	@JsonProperty("qt")
	private Date queryTime;

	@JsonProperty("ip")
	private String ipAddress;

	/**
	 * 浏览器访问user-agent
	 */
	@JsonProperty("ua")
	private String userAgent;

	public TagBasicInfoEO getTagBasicInfo() {
		return tagBasicInfo;
	}

	public void setTagBasicInfo(TagBasicInfoEO tagBasicInfo) {
		this.tagBasicInfo = tagBasicInfo;
	}

	public AccountRow getAccount() {
		return account;
	}

	public void setAccount(AccountRow account) {
		this.account = account;
	}

	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
