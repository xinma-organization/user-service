package com.xinma.base.datastore.ext.table;

import java.util.List;

import com.xinma.base.datastore.exceptions.TableException;
import com.xinma.base.datastore.model.tag.LotteryEnrolledEO;
import com.xinma.base.datastore.model.tag.TagAccessLogEO;
import com.xinma.base.datastore.model.tag.TagAwardEO;
import com.xinma.base.datastore.model.tag.TagBasicInfoEO;
import com.xinma.base.datastore.model.tag.TagRow;

/**
 * 标签表数据访问接口定义
 * 
 * @author Alauda
 *
 * @date 2016年5月29日
 *
 */
public interface TagTableService {

	/**
	 * 获取标签所用信息
	 * 
	 * @param tagId
	 *            标签Id
	 * @return 标签记录
	 * @throws TableException
	 *             操作失败，抛出异常
	 */
	TagRow getTagRow(long tagId) throws TableException;

	/**
	 * 新增或更新标签基本信息，如果标签记录存在则覆盖原记录，否则新增；标签的tagId必须存在
	 * 
	 * @param tagBasicInfo
	 *            标签基本信息
	 * @throws TableException
	 *             操作失败，抛出异常
	 */
	void putOrUpdateTagBasicInfo(TagBasicInfoEO tagBasicInfo) throws TableException;

	/**
	 * 获取标签基本信息
	 * 
	 * @param tagId
	 *            标签Id
	 * @return 标签基本信息
	 * @throws TableException
	 *             操作失败，抛出异常
	 */
	TagBasicInfoEO getTagBasicInfo(long tagId) throws TableException;

	/**
	 * 新增一条标签访问日志
	 * 
	 * @param tagId
	 *            标签Id
	 * @param accessLog
	 *            标签访问日志记录
	 * @throws TableException
	 *             操作失败，抛出异常
	 */
	void addTagAccessLog(long tagId, TagAccessLogEO accessLog) throws TableException;

	/**
	 * 获取标签访问日志记录
	 * 
	 * @param tagId
	 *            标签Id
	 * @return 标签访问日志记录列表
	 * @throws TableException
	 *             操作失败，抛出异常
	 */
	List<TagAccessLogEO> getTagAccessLogs(long tagId) throws TableException;

	/**
	 * 标签内新增一条奖品记录
	 * 
	 * @param tagId
	 *            标签Id
	 * @param tagAward
	 *            奖品记录
	 * @throws TableException
	 *             操作失败，抛出异常
	 */
	void addTagAward(long tagId, TagAwardEO tagAward) throws TableException;

	/**
	 * 获取标签奖品列表
	 * 
	 * @param tagId
	 *            标签序号
	 * @return 标签奖品列表
	 * @throws TableException
	 *             操作失败，抛出异常
	 */
	List<TagAwardEO> getTagAwards(long tagId) throws TableException;

	/**
	 * 标签新增一条抽奖记录
	 * 
	 * @param tagId
	 *            标签Id
	 * @param lotteryEnrolled
	 *            标签抽奖记录
	 * @throws TableException
	 *             操作失败，抛出异常
	 */
	void addLotteryEnrolled(long tagId, LotteryEnrolledEO lotteryEnrolled) throws TableException;

	/**
	 * 获取标签抽奖记录日志
	 * 
	 * @param tagId
	 *            标签Id
	 * @return 标签抽奖日志记录
	 * @throws TableException
	 *             操作失败，抛出异常
	 */
	List<LotteryEnrolledEO> getTagLotteryEnrolled(long tagId) throws TableException;
}
