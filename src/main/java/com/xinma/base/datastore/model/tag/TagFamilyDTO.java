package com.xinma.base.datastore.model.tag;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 记录标签家族实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagFamilyDTO implements Serializable {

	private static final long serialVersionUID = -7645347952540821101L;

	/**
	 * 标签父节点sequence number
	 */
	@JsonProperty("p")
	private Long parent;

	/**
	 * 标签子节点sequence number list
	 */
	@JsonProperty("c")
	private List<Long> childern;

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public List<Long> getChildern() {
		return childern;
	}

	public void setChildern(List<Long> childern) {
		this.childern = childern;
	}
}
