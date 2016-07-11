package com.xinma.base.datastore.model.tag;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 云端标签表储存信息
 * 
 * @author Alauda
 *
 * @date 2015年6月30日
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagRow {

	/**
	 * 标签基本信息
	 */
	private TagBasicInfoEO basicInfo;

	/**
	 * 标签访问日志
	 */
	private List<TagAccessLogEO> accessLog;

	/**
	 * 标签奖品列表
	 */
	private List<TagAwardEO> awards;

	/**
	 * 标签抽奖日志
	 */
	private List<LotteryEnrolledEO> lotteryEnrolled;

	public TagBasicInfoEO getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(TagBasicInfoEO basicInfo) {
		this.basicInfo = basicInfo;
	}

	public List<TagAccessLogEO> getAccessLog() {
		return accessLog;
	}

	public void setAccessLog(List<TagAccessLogEO> accessLog) {
		this.accessLog = accessLog;
	}

	public List<TagAwardEO> getAwards() {
		return awards;
	}

	public void setAwards(List<TagAwardEO> awards) {
		this.awards = awards;
	}

	public List<LotteryEnrolledEO> getLotteryEnrolled() {
		return lotteryEnrolled;
	}

	public void setLotteryEnrolled(List<LotteryEnrolledEO> lotteryEnrolled) {
		this.lotteryEnrolled = lotteryEnrolled;
	}
}
