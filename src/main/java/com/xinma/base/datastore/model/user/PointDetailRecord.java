package com.xinma.base.datastore.model.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户积分单条记录实体类
 * 
 * @author Hoctor
 *
 */
/**
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointDetailRecord {

	/**
	 * 该条记录获取积分值
	 */
	@JsonProperty("p")
	private int point;

	/**
	 * point describe,积分详情描述
	 */
	@JsonProperty("d")
	private String describe;

	/**
	 * time,积分获取时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("t")
	private Date time;

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
