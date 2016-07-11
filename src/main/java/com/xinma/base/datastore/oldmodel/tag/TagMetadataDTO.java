package com.xinma.base.datastore.oldmodel.tag;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 标签生产数据实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagMetadataDTO implements Serializable {
	private static final long serialVersionUID = 8185072455549544047L;

	public final static String mixedTagKey = "m";
	public final static String touyunIdKey = "o";
	public final static String productIdKey = "p";
	public final static String enterpriseIdKey = "e";
	public final static String factoryIdKey = "f";
	public final static String terminalIdKey = "t";
	public final static String franchiserIdKey = "fr";

	/**
	 * 标签序号
	 */
	@JsonProperty("c")
	private Long clearTag;

	/**
	 * mixedTag, mixed tag sequence
	 */
	@JsonProperty("m")
	private String mixedTag;

	/**
	 * touyunId,打印在标签上的码
	 */
	@JsonProperty("o")
	private String touyunId;

	/**
	 * productId, 产品Id
	 */
	@JsonProperty("p")
	private Integer productId;

	/**
	 * enterpriseId,公司Id
	 */
	@JsonProperty("e")
	private Integer enterpriseId;

	/**
	 * factoryId
	 */
	@JsonProperty("f")
	private Integer factoryId;

	/**
	 * terminalId,终端Id
	 */
	@JsonProperty("t")
	private Integer terminalId;

	/**
	 * productionTime,生产时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("pt")
	private Date productionTime;

	/**
	 * productionSite,生产地址
	 */
	@JsonProperty("ps")
	private String productionSite;

	/**
	 * productionBatch,生产批次
	 */
	@JsonProperty("pb")
	private String productionBatch;

	/**
	 * franchiserId,经销商
	 */
	@JsonProperty("fr")
	private Integer franchiserId;

	public Long getClearTag() {
		return clearTag;
	}

	public void setClearTag(Long clearTag) {
		this.clearTag = clearTag;
	}

	public String getMixedTag() {
		return mixedTag;
	}

	public void setMixedTag(String mixedTag) {
		this.mixedTag = mixedTag;
	}

	public String getTouyunId() {
		return touyunId;
	}

	public void setTouyunId(String touyunId) {
		this.touyunId = touyunId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(Integer factoryId) {
		this.factoryId = factoryId;
	}

	public Integer getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Integer terminalId) {
		this.terminalId = terminalId;
	}

	public Date getProductionTime() {
		return productionTime;
	}

	public void setProductionTime(Date productionTime) {
		this.productionTime = productionTime;
	}

	public String getProductionSite() {
		return productionSite;
	}

	public void setProductionSite(String productionSite) {
		this.productionSite = productionSite;
	}

	public String getProductionBatch() {
		return productionBatch;
	}

	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}

	public Integer getFranchiserId() {
		return franchiserId;
	}

	public void setFranchiserId(Integer franchiserId) {
		this.franchiserId = franchiserId;
	}

}
