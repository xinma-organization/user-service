package com.xinma.base.datastore.model.tag;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 标签查询产品相关信息数据传输对象
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = -4873354534026972208L;

	private int oid;

	private String name; // 产品名称

	private String code; // 产品代码

	private String spec;// 产品规格

	private Integer eseId; // 企业ID

	private String eseName;

	private String icons;// 宣传图集合

	private Float price;// 产品原价

	private Float salesPrice;

	private String shelfLife;// 保质期

	private Integer status; // 状态

	private Long surveyId; // 问卷调查Id

	private Integer recommend; // 是否为推荐产品

	private String info;// 产品信息

	private Integer templateId;// 模板ID

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
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

	public String getIcons() {
		return icons;
	}

	public void setIcons(String icons) {
		this.icons = icons;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Float salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
}
