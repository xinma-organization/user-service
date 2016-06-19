package com.xinma.base.datastore.model.tag;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 标签查询经销商相关信息数据传输对象
 * 
 * @author zhangyongyi
 */

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FranchiserDTO implements Serializable {

	private static final long serialVersionUID = -3674479268103371380L;

	private Integer oid;

	private String code;

	private String name;

	private Integer eseId;

	private String eseName;

	private Integer status;

	private String remark;

	private String areaCodes;

	private String areaNames;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEseId() {
		return eseId;
	}

	public void setEseId(Integer eseId) {
		this.eseId = eseId;
	}

	public String getEseName() {
		return eseName;
	}

	public void setEseName(String eseName) {
		this.eseName = eseName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(String areaCodes) {
		this.areaCodes = areaCodes;
	}

	public String getAreaNames() {
		return areaNames;
	}

	public void setAreaNames(String areaNames) {
		this.areaNames = areaNames;
	}

}
