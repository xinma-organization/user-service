package com.xinma.base.datastore.oldmodel.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户中奖信息实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAwardDTO implements Serializable {

	private static final long serialVersionUID = 6321531268668333908L;

	@JsonProperty("t")
	private int totalCount = 0;

	@JsonProperty("tp")
	private int totalPage = 0;

	@JsonProperty("l")
	private List<UserAwardRecord> records;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<UserAwardRecord> getRecords() {
		return records;
	}

	public void setRecords(List<UserAwardRecord> records) {
		this.records = records;
	}

	/**
	 * 添加中奖记录
	 * 
	 * @param awardRecord
	 */
	public void appendAwardRecord(UserAwardRecord awardRecord) {
		if (this.records == null) {
			this.records = new ArrayList<>();
		}
		this.records.add(awardRecord);
		this.totalCount++;
	}

	public void appendAwardRecords(List<UserAwardRecord> awardRecords) {
		if (this.records == null) {
			this.records = new ArrayList<>();
		}
		this.records.addAll(awardRecords);
		this.totalCount += awardRecords.size();
	}
}
