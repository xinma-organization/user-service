package com.xinma.base.datastore.model.user;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户积分详情实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointDetailDTO implements Serializable {

	private static final long serialVersionUID = 750717264353210479L;

	@JsonProperty("t")
	private int totalCount;

	@JsonProperty("l")
	private List<PointDetailRecord> records;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<PointDetailRecord> getRecords() {
		return records;
	}

	public void setRecords(List<PointDetailRecord> records) {
		this.records = records;
	}

}
