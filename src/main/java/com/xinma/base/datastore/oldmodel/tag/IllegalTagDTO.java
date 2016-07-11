package com.xinma.base.datastore.oldmodel.tag;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 扫码时，非法标签实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IllegalTagDTO {
	/**
	 * 错误代码
	 */
	private String errorCode;

	/**
	 * 透云Id
	 */
	private String touyunId;

	/**
	 * 标签序号
	 */
	private Long clearTag;

	/**
	 * 扫码时间
	 */
	private Date scanTime;

	/**
	 * 访问者ip
	 */
	private String ip;
	
	/**
	 * 访问者的openid
	 */
	private String openid;
	
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getTouyunId() {
		return touyunId;
	}

	public void setTouyunId(String touyunId) {
		this.touyunId = touyunId;
	}

	public Long getClearTag() {
		return clearTag;
	}

	public void setClearTag(Long clearTag) {
		this.clearTag = clearTag;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public IllegalTagDTO() {
		super();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public IllegalTagDTO(String errorCode, String touyunId, Long clearTag, Date scanTime, String ip) {
		super();
		this.errorCode = errorCode;
		this.touyunId = touyunId;
		this.clearTag = clearTag;
		this.scanTime = scanTime;
		this.ip = ip;
	}
}
