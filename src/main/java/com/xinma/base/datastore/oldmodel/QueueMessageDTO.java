package com.xinma.base.datastore.oldmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 其他系统与云端系统的消息DTO定义
 * 
 * @author zhangyongyi
 *
 */
public class QueueMessageDTO {

	@JsonProperty("ct")
	private int contentType; // 消息类型

	private String content; // 消息内容

	private boolean update; // 是否为修改

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

}
