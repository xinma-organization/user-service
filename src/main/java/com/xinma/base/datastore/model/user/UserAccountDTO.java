package com.xinma.base.datastore.model.user;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户类别表实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountDTO implements Serializable {

	private static final long serialVersionUID = -7676239056882856959L;

	/**
	 * OTS用户类别表分片建Key
	 */
	public final static String sliceKey = "s";

	/**
	 * type,用户类别key
	 */
	public final static String typeKey = "t";

	/**
	 * identification, 用户登陆标识Key
	 */
	public final static String nameKey = "n";

	/**
	 * 透云用户Idkey
	 */
	public final static String IdKey = "i";

	/**
	 * 该字段指向当前用户的根账号,目前只用于其它企业公众账号关联到透云的企业公众账号
	 */
	public final static String parentKey = "p";

	/**
	 * slice, OTS用户类别表分片建
	 */
	@JsonProperty("s")
	private int slice;

	/**
	 * category, 用户类别
	 */
	@JsonProperty("t")
	private String type;

	/**
	 * identification Id,用户账号标识
	 */
	@JsonProperty("n")
	private String name;

	/**
	 * 透云用户Id
	 */
	@JsonProperty("i")
	private Long id;

	@JsonProperty("p")
	private UserAccountDTO parent;

	/**
	 * 用户基本信息，注意：该字段存储在UserDTO表中
	 */
	@JsonProperty("m")
	private UserMetadata metadata;

	public int getSlice() {
		return slice;
	}

	public void setSlice(int slice) {
		this.slice = slice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserAccountDTO getParent() {
		return parent;
	}

	public void setParent(UserAccountDTO parent) {
		this.parent = parent;
	}

	public UserMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(UserMetadata metadata) {
		this.metadata = metadata;
	}

	public UserAccountDTO() {
		super();
	}

	public UserAccountDTO(String type, String name, Long id) {
		super();
		this.type = type;
		this.name = name;
		this.id = id;
	}
}
