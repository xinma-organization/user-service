package com.xinma.base.datastore.model.tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 标签查询记录日志信息数据传输对象
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryLogDTO implements Serializable {

	private static final long serialVersionUID = 182311426104286454L;

	/**
	 * logCount,日志记录数
	 */
	@JsonProperty("c")
	private int logCount = 0;

	/**
	 * firstQueryRecord,第一次扫码记录
	 */
	@JsonProperty("f")
	private QueryRecordDTO firstQueryRecord;

	/**
	 * queryRecords,标签查询记录列表
	 */
	@JsonProperty("l")
	private List<QueryRecordDTO> queryRecords;

	public int getLogCount() {
		return logCount;
	}

	public void setLogCount(int logCount) {
		this.logCount = logCount;
	}

	public QueryRecordDTO getFirstQueryRecord() {
		return firstQueryRecord;
	}

	public void setFirstQueryRecord(QueryRecordDTO firstQueryRecord) {
		this.firstQueryRecord = firstQueryRecord;
	}

	public List<QueryRecordDTO> getQueryRecords() {
		return queryRecords;
	}

	public void setQueryRecords(List<QueryRecordDTO> queryRecords) {
		this.queryRecords = queryRecords;
	}

	/**
	 * 递增扫码次数
	 */
	public void increaseLogCount() {
		this.logCount++;
	}

	/**
	 * 追加扫码记录
	 * 
	 * @param record
	 */
	public void appendQueryRecord(QueryRecordDTO record) {
		if (this.queryRecords == null) {
			this.queryRecords = new ArrayList<>();
		}
		this.queryRecords.add(record);

		this.logCount++;
	}
}
