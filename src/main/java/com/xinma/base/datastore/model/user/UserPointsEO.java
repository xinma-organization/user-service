package com.xinma.base.datastore.model.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Alauda
 *
 * @date 2015年6月30日
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPointsEO {

	private int totalIncome;

	private int totalExpend;

	/**
	 * 历史纪录链接
	 */
	private String historyRecordsLink;

	private List<UserPointRecordEO> records;

	public int getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(int totalIncome) {
		this.totalIncome = totalIncome;
	}

	public int getTotalExpend() {
		return totalExpend;
	}

	public void setTotalExpend(int totalExpend) {
		this.totalExpend = totalExpend;
	}

	public String getHistoryRecordsLink() {
		return historyRecordsLink;
	}

	public void setHistoryRecordsLink(String historyRecordsLink) {
		this.historyRecordsLink = historyRecordsLink;
	}

	public List<UserPointRecordEO> getRecords() {
		return records;
	}

	public void setRecords(List<UserPointRecordEO> records) {
		this.records = records;
	}
}
