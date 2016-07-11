package com.xinma.base.datastore.oldmodel.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户扫码记录实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTagRecord implements Serializable {
	private static final long serialVersionUID = 4919428935082808333L;

	@JsonProperty("t")
	private int totalCount = 0;

	@JsonProperty("tp")
	private int totalPage = 1;

	@JsonProperty("s")
	private Set<Long> tags;

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

	public Set<Long> getTags() {
		return tags;
	}

	public void setTags(Set<Long> tags) {
		this.tags = tags;
	}

	public void appendTag2Set(Long tag) {
		if (this.tags == null) {
			this.tags = new HashSet<Long>();
		}

		this.tags.add(tag);
		increaseTotalCount();
	}

	public void appendTags2Set(Set<Long> tagsSet) {
		if (this.tags == null) {
			this.tags = new HashSet<Long>();
		}

		this.tags.addAll(tagsSet);
		this.totalCount += tagsSet.size();
	}

	private void increaseTotalCount() {
		this.totalCount++;
	}
}
