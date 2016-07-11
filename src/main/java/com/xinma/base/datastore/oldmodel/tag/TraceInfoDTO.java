package com.xinma.base.datastore.oldmodel.tag;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 标签查询页面追溯信息数据传输对象
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraceInfoDTO implements Serializable {

	private static final long serialVersionUID = -8446243830015685115L;

	/**
	 * index,追溯链中的顺序
	 */
	@JsonProperty("i")
	private int index;

	/**
	 * operator,操作员
	 */
	@JsonProperty("o")
	private String operator;

	/**
	 * time,操作时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("t")
	private Date time;

	/**
	 * content, 操作内容
	 */
	@JsonProperty("c")
	private String content;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
