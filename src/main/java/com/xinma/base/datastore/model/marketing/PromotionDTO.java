package com.xinma.base.datastore.model.marketing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 促销活动信息的DTO定义
 * 
 * @author zhangyongyi
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromotionDTO implements Serializable {

	private static final long serialVersionUID = 6514908744122792194L;

	private Integer oid;

	private int status; // 见PromotionStatus

	private Integer category = 0;// 活动类型,将PromotionCategory

	private Integer points;// 活动类型为积分抽奖时，有资格参与抽奖的最小积分数要求

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date pointDeadLine; // 奖品为积分时，积分奖品的有效截至日期

	private boolean fullRatio = false; // 综合中奖率是否为100%

	private String name; // 活动名称

	private int eseId; // 活动所属企业ID

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime; // 活动开始时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime; // 活动结束时间

	private Set<Integer> datesOfMonth = new HashSet<Integer>();// 活动日(一月中哪些天)

	private List<PromotionPeriodDTO> periods = new ArrayList<PromotionPeriodDTO>();

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date awardTime;// 领奖截至时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date pdStartTime; // 产品生产时间的起始时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date pdEndTime; // 产品生产时间的截至时间

	private Set<Integer> productIds = new HashSet<Integer>(); // 参与活动的产品ID列表

	private Integer memberType;// 参与活动的会员类型（0所有会员;1新注册会员;2老会员）

	private Integer timesLimitType = 0;// 抽奖次数限制类型(0限每人抽奖次数;1限每人每天抽奖次数)

	private Integer timesLimit = 0;// 抽奖限制次数(0不限制,大于0的数字为限制次数)

	private Set<PromotionRewardDTO> rewards = new HashSet<PromotionRewardDTO>();

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Date getPointDeadLine() {
		return pointDeadLine;
	}

	public void setPointDeadLine(Date pointDeadLine) {
		this.pointDeadLine = pointDeadLine;
	}

	public boolean isFullRatio() {
		return fullRatio;
	}

	public void setFullRatio(boolean fullRatio) {
		this.fullRatio = fullRatio;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEseId() {
		return eseId;
	}

	public void setEseId(int eseId) {
		this.eseId = eseId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Set<Integer> getDatesOfMonth() {
		return datesOfMonth;
	}

	public void setDatesOfMonth(Set<Integer> datesOfMonth) {
		this.datesOfMonth = datesOfMonth;
	}

	public List<PromotionPeriodDTO> getPeriods() {
		return periods;
	}

	public void setPeriods(List<PromotionPeriodDTO> periods) {
		this.periods = periods;
	}

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public Date getPdStartTime() {
		return pdStartTime;
	}

	public void setPdStartTime(Date pdStartTime) {
		this.pdStartTime = pdStartTime;
	}

	public Date getPdEndTime() {
		return pdEndTime;
	}

	public void setPdEndTime(Date pdEndTime) {
		this.pdEndTime = pdEndTime;
	}

	public Set<Integer> getProductIds() {
		return productIds;
	}

	public void setProductIds(Set<Integer> productIds) {
		this.productIds = productIds;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getTimesLimitType() {
		return timesLimitType;
	}

	public void setTimesLimitType(Integer timesLimitType) {
		this.timesLimitType = timesLimitType;
	}

	public Integer getTimesLimit() {
		return timesLimit;
	}

	public void setTimesLimit(Integer timesLimit) {
		this.timesLimit = timesLimit;
	}

	public Set<PromotionRewardDTO> getRewards() {
		return rewards;
	}

	public void setRewards(Set<PromotionRewardDTO> rewards) {
		this.rewards = rewards;
	}

}
