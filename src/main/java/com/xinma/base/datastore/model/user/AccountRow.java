package com.xinma.base.datastore.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 标签账户信息表
 * 
 * @author Alauda
 *
 * @date 2015年6月30日
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRow {

	/**
	 * 账户名称
	 */
	private String name;

	/**
	 * 账户类型
	 */
	private int type = 0;

	/**
	 * 组织Id
	 */
	private int orgId;

	/**
	 * 用户表用户ID
	 */
	private int userId;

	private String passward;

	private UserBasicInfoEO basicInfo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public UserBasicInfoEO getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(UserBasicInfoEO basicInfo) {
		this.basicInfo = basicInfo;
	}
}
