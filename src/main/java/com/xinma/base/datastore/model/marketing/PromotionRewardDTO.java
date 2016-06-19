package com.xinma.base.datastore.model.marketing;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 促销活动奖品的DTO定义
 * 
 * @author zhangyongyi
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromotionRewardDTO implements Serializable, Comparable<PromotionRewardDTO> {

	private static final long serialVersionUID = -6120937789754291730L;

	private Integer oid;

	private String name; // 奖项名称

	private Integer cnt; // 数量

	private Double rate; // 中奖率(%)

	private Double keyAreaRate; // 重点区域中奖率(%)

	private String prizeName; // 奖品名

	private String prizeImg;// 奖品图片URL

	private Float amount; // 奖品面值

	private Integer rewardCate; // 奖品类型(RewardsCategory)

	private double minHitValue; // 中奖概率区间最小值

	private double maxHitValue; // 中奖概率区间最大值

	private double minKeyAreaHitValue; // 重点区域中奖概率区间最小值

	private double maxKeyAreaHitValue; // 重点区域中奖概率区间最大值

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getKeyAreaRate() {
		return keyAreaRate;
	}

	public void setKeyAreaRate(Double keyAreaRate) {
		this.keyAreaRate = keyAreaRate;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getPrizeImg() {
		return prizeImg;
	}

	public void setPrizeImg(String prizeImg) {
		this.prizeImg = prizeImg;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Integer getRewardCate() {
		return rewardCate;
	}

	public void setRewardCate(Integer rewardCate) {
		this.rewardCate = rewardCate;
	}

	public double getMinHitValue() {
		return minHitValue;
	}

	public void setMinHitValue(double minHitValue) {
		this.minHitValue = minHitValue;
	}

	public double getMaxHitValue() {
		return maxHitValue;
	}

	public void setMaxHitValue(double maxHitValue) {
		this.maxHitValue = maxHitValue;
	}

	public double getMinKeyAreaHitValue() {
		return minKeyAreaHitValue;
	}

	public void setMinKeyAreaHitValue(double minKeyAreaHitValue) {
		this.minKeyAreaHitValue = minKeyAreaHitValue;
	}

	public double getMaxKeyAreaHitValue() {
		return maxKeyAreaHitValue;
	}

	public void setMaxKeyAreaHitValue(double maxKeyAreaHitValue) {
		this.maxKeyAreaHitValue = maxKeyAreaHitValue;
	}

	@Override
	public int compareTo(PromotionRewardDTO o) {
		if (this.rate > o.getRate()) {
			return 1;
		} else if (this.rate == o.getRate()) {
			return 0;
		} else {
			return -1;
		}
	}
}
