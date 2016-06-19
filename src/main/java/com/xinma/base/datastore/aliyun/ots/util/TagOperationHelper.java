package com.xinma.base.datastore.aliyun.ots.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ots.OTSClient;
import com.aliyun.openservices.ots.model.ColumnValue;
import com.aliyun.openservices.ots.model.Condition;
import com.aliyun.openservices.ots.model.GetRowResult;
import com.aliyun.openservices.ots.model.PrimaryKeyValue;
import com.aliyun.openservices.ots.model.RowExistenceExpectation;
import com.aliyun.openservices.ots.model.RowPrimaryKey;
import com.aliyun.openservices.ots.model.RowUpdateChange;
import com.aliyun.openservices.ots.model.SingleRowQueryCriteria;
import com.aliyun.openservices.ots.model.condition.ColumnCondition;
import com.aliyun.openservices.ots.model.condition.RelationalCondition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinma.base.datastore.enums.OpreationLockStatus;
import com.xinma.base.datastore.enums.RewardStatus;
import com.xinma.base.datastore.model.tag.QueryLogDTO;
import com.xinma.base.datastore.model.tag.QueryRecordDTO;
import com.xinma.base.datastore.model.tag.TagAwardRecordDTO;
import com.xinma.base.datastore.model.tag.TagDTO;
import com.xinma.base.datastore.model.tag.TagFamilyDTO;
import com.xinma.base.datastore.model.tag.TagLotteryRecord;
import com.xinma.base.datastore.model.tag.TagMetadataDTO;
import com.xinma.base.datastore.model.tag.TraceInfoDTO;
import com.xinma.base.datastore.model.user.UserAccountDTO;
import com.xinma.base.util.DateUtil;
import com.xinma.base.util.JacksonUtils;

public class TagOperationHelper {
	private static Logger logger = LoggerFactory.getLogger(TagOperationHelper.class);

	private static OTSClient otsClient = null;

	public static void setOtsClient(OTSClient client) {
		otsClient = client;
	}

	/**
	 * 获取标签表行主键
	 */
	private static RowPrimaryKey getRowPrimaryKey(Long clearTag) {
		RowPrimaryKey rowPrimaryKey = new RowPrimaryKey();
		rowPrimaryKey.addPrimaryKeyColumn(TagDTO.sliceKey,
				PrimaryKeyValue.fromLong(OtsTagTableSliceHelper.getSliceIndex(clearTag)));
		rowPrimaryKey.addPrimaryKeyColumn(TagDTO.clearTagKey, PrimaryKeyValue.fromLong(clearTag));
		return rowPrimaryKey;
	}

	/**
	 * 从ots中获取TagDTO对象
	 * 
	 * @param clearTag
	 *            标签序号
	 * @return
	 * @throws IOException
	 */
	public static TagDTO getTagRowFromOTS(Long clearTag) throws IOException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableSliceHelper.getTableName(clearTag));
		criteria.setPrimaryKey(getRowPrimaryKey(clearTag));

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			TagDTO dto = new TagDTO();
			ObjectMapper mapper = new ObjectMapper();
			// 获取标签元信息
			String metadataJson = columnMaps.get(TagDTO.tagMetadataKey) == null ? null
					: columnMaps.get(TagDTO.tagMetadataKey).asString();
			if (metadataJson != null && !metadataJson.isEmpty()) {
				dto.setMedadata(mapper.readValue(metadataJson, TagMetadataDTO.class));
				dto.getMedadata().setClearTag(clearTag);
			}
			// 获取标签扫码日志
			String queryLogJson = columnMaps.get(TagDTO.queryLogKey) == null ? null
					: columnMaps.get(TagDTO.queryLogKey).asString();
			if (queryLogJson != null && !queryLogJson.isEmpty()) {
				dto.setQueryLog(mapper.readValue(queryLogJson, QueryLogDTO.class));
			}
			// 获取标签中奖记录
			String awardJson = columnMaps.get(TagDTO.tagAwardKey) == null ? null
					: columnMaps.get(TagDTO.tagAwardKey).asString();
			if (awardJson != null && !awardJson.isEmpty()) {
				dto.setTagAward(JacksonUtils.readJson2EntityList(awardJson, TagAwardRecordDTO.class));
			}
			// 获取标签追溯信息
			String traceInfoJson = columnMaps.get(TagDTO.traceInfoKey) == null ? null
					: columnMaps.get(TagDTO.traceInfoKey).asString();
			if (traceInfoJson != null && !traceInfoJson.isEmpty()) {
				dto.setTraceInfo(JacksonUtils.readJson2EntityList(traceInfoJson, TraceInfoDTO.class));
			}
			// 获取标签参加的抽奖活动记录
			String lotteryEnrollJson = columnMaps.get(TagDTO.lotteryEnrollKey) == null ? null
					: columnMaps.get(TagDTO.lotteryEnrollKey).asString();
			if (lotteryEnrollJson != null && !lotteryEnrollJson.isEmpty()) {

				dto.setLotteryEnroll((Map<Integer, List<TagLotteryRecord>>) mapper.readValue(lotteryEnrollJson,
						new TypeReference<Map<Integer, List<TagLotteryRecord>>>() {
						}));
			}
			// 获取标签的附加信息
			String extraBagJson = columnMaps.get(TagDTO.extraBagKey) == null ? null
					: columnMaps.get(TagDTO.extraBagKey).asString();
			if (extraBagJson != null && !extraBagJson.isEmpty()) {
				dto.setExtraBag(mapper.readTree(extraBagJson));
			}

			// 获取标签的家庭成员节点信息
			String familyJson = columnMaps.get(TagDTO.tagFamilyKey) == null ? null
					: columnMaps.get(TagDTO.tagFamilyKey).asString();
			if (familyJson != null && !familyJson.isEmpty()) {
				dto.setTagFamily(mapper.readValue(familyJson, TagFamilyDTO.class));
			}

			Long presetRewardId = columnMaps.get(TagDTO.presetRewardIdKey) == null ? -1L
					: columnMaps.get(TagDTO.presetRewardIdKey).asLong();
			dto.setPresetRewardId(presetRewardId.intValue());

			dto.setClearTag(clearTag);

			return dto;
		}

		return null;
	}

	/**
	 * 从ots中获取TagMetadataDTO对象
	 * 
	 * @param clearTag
	 *            标签序号
	 * @return
	 * @throws IOException
	 */
	public static TagMetadataDTO getTagMetadataFromOTS(Long clearTag) throws IOException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableSliceHelper.getTableName(clearTag));
		criteria.setPrimaryKey(getRowPrimaryKey(clearTag));

		criteria.addColumnsToGet(new String[] { TagDTO.tagMetadataKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			// 获取标签元信息
			String metadataJson = columnMaps.get(TagDTO.tagMetadataKey) == null ? null
					: columnMaps.get(TagDTO.tagMetadataKey).asString();
			if (metadataJson != null && !metadataJson.isEmpty()) {
				return new ObjectMapper().readValue(metadataJson, TagMetadataDTO.class);
			}
		}

		return null;
	}

	/**
	 * 获取标签扫码记录
	 * 
	 * @param clearTag
	 *            标签序号
	 * @return
	 * @throws IOException
	 */
	public static QueryLogDTO getQueryLogFromOTS(Long clearTag) throws IOException {
		QueryLogDTO queryLog = null;
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableSliceHelper.getTableName(clearTag));
		criteria.setPrimaryKey(getRowPrimaryKey(clearTag));

		criteria.addColumnsToGet(new String[] { TagDTO.queryLogKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String queryLogJson = columnMaps.get(TagDTO.queryLogKey) == null ? null
					: columnMaps.get(TagDTO.queryLogKey).asString();
			if (queryLogJson != null && !queryLogJson.isEmpty()) {
				queryLog = new ObjectMapper().readValue(queryLogJson, QueryLogDTO.class);
			}
		}

		return queryLog;
	}

	/**
	 * 获取标签抽奖记录
	 * 
	 * @param clearTag
	 *            标签序号
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer, List<TagLotteryRecord>> getLotteryLogFromOTS(Long clearTag) throws IOException {

		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableSliceHelper.getTableName(clearTag));
		criteria.setPrimaryKey(getRowPrimaryKey(clearTag));

		criteria.addColumnsToGet(new String[] { TagDTO.lotteryEnrollKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String lotteryEnrollJson = columnMaps.get(TagDTO.lotteryEnrollKey) == null ? null
					: columnMaps.get(TagDTO.lotteryEnrollKey).asString();
			if (lotteryEnrollJson != null && !lotteryEnrollJson.isEmpty()) {
				return new ObjectMapper().readValue(lotteryEnrollJson,
						new TypeReference<Map<Integer, List<TagLotteryRecord>>>() {
						});
			}
		}

		return null;
	}

	/**
	 * 获取标签活动抽奖Id
	 * 
	 * @param clearTag
	 * @param promotionId
	 * @return
	 * @throws IOException
	 */
	public static List<TagLotteryRecord> getPromotionLotteryLogFromOTS(Long clearTag, int promotionId)
			throws IOException {
		Map<Integer, List<TagLotteryRecord>> map = getLotteryLogFromOTS(clearTag);
		if (map == null) {
			return null;
		} else {
			return map.get(promotionId);
		}
	}

	/**
	 * 获取标签奖品列表
	 * 
	 * @param clearTag
	 *            标签序号
	 * @return
	 */
	public static List<TagAwardRecordDTO> getTagAwardFromOTS(Long clearTag) {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableSliceHelper.getTableName(clearTag));
		criteria.setPrimaryKey(getRowPrimaryKey(clearTag));

		criteria.addColumnsToGet(new String[] { TagDTO.tagAwardKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String awardJson = columnMaps.get(TagDTO.tagAwardKey) == null ? null
					: columnMaps.get(TagDTO.tagAwardKey).asString();
			if (awardJson != null && !awardJson.isEmpty()) {
				return JacksonUtils.readJson2EntityList(awardJson, TagAwardRecordDTO.class);
			}
		}

		return null;
	}

	/**
	 * 给标签新增抽奖记录和中奖信息
	 * 
	 * @param clearTag
	 *            标签序号
	 * @param lotteryRecord
	 *            抽奖记录
	 * @param promotionId
	 *            活动Id
	 * @param tagAward
	 *            标签奖品对象
	 * @throws IOException
	 */
	public static void addLotteryRecordAndAward2Tag(Long clearTag, TagLotteryRecord lotteryRecord, Integer promotionId,
			TagAwardRecordDTO tagAward) throws IOException {

		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsTagTableSliceHelper.getTableName(clearTag));

		rowUpdateChange.setPrimaryKey(getRowPrimaryKey(clearTag));

		// TODO 如果用的acountId不存在user表内,是否要创建新用户
		Map<Integer, List<TagLotteryRecord>> map = getLotteryLogFromOTS(clearTag);
		List<TagLotteryRecord> list = null;
		if (map == null) {
			map = new HashMap<Integer, List<TagLotteryRecord>>();
			list = new ArrayList<TagLotteryRecord>();
		} else {
			list = map.get(promotionId);
			if (list == null) {
				list = new ArrayList<TagLotteryRecord>();
			}
		}
		// TODO 由于ots column列有65536最大限制，所以这里只保存前50条抽奖记录
		if (list.size() < 50) {
			list.add(lotteryRecord);
		}

		map.put(promotionId, list);

		rowUpdateChange.addAttributeColumn(TagDTO.lotteryEnrollKey,
				ColumnValue.fromString(new ObjectMapper().writeValueAsString(map)));

		if (tagAward != null) {
			List<TagAwardRecordDTO> tagAwards = getTagAwardFromOTS(clearTag);
			if (tagAwards == null) {
				tagAwards = new ArrayList<TagAwardRecordDTO>();
			}

			tagAwards.add(tagAward);
			rowUpdateChange.addAttributeColumn(TagDTO.tagAwardKey,
					ColumnValue.fromString(new ObjectMapper().writeValueAsString(tagAwards)));
		}

		rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));

		OtsRowHelper.updateRowWithCLient(otsClient, rowUpdateChange);
	}

	/**
	 * 更新标签抽奖记录
	 * 
	 * @param clearTag
	 *            标签序号
	 * @param map
	 *            标签抽奖记录集合
	 * @throws IOException
	 */
	public static void updateLotteryRecord2Tag(Long clearTag, Map<Integer, List<TagLotteryRecord>> map)
			throws IOException {

		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsTagTableSliceHelper.getTableName(clearTag));

		rowUpdateChange.setPrimaryKey(getRowPrimaryKey(clearTag));
		rowUpdateChange.addAttributeColumn(TagDTO.lotteryEnrollKey,
				ColumnValue.fromString(new ObjectMapper().writeValueAsString(map)));
		rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));

		OtsRowHelper.updateRowWithCLient(otsClient, rowUpdateChange);
	}

	/**
	 * 追加标签扫码记录
	 * 
	 * @param clearTag
	 *            标签号
	 * @param queryRecord
	 *            扫码记录
	 * @throws IOException
	 */
	public static void addQueryLog2Tag(Long clearTag, QueryRecordDTO queryRecord) throws IOException {
		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsTagTableSliceHelper.getTableName(clearTag));

		QueryLogDTO queryLog = getQueryLogFromOTS(clearTag);
		if (queryLog == null) {
			queryLog = new QueryLogDTO();
			queryLog.setFirstQueryRecord(queryRecord);
		}
		// TODO 历史记录转移
		queryLog.appendQueryRecord(queryRecord);

		rowUpdateChange.setPrimaryKey(getRowPrimaryKey(clearTag));

		rowUpdateChange.addAttributeColumn(TagDTO.queryLogKey,
				ColumnValue.fromString(new ObjectMapper().writeValueAsString(queryLog)));

		rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));

		OtsRowHelper.updateRowWithCLient(otsClient, rowUpdateChange);
	}

	/**
	 * 更新标签中奖奖品状态
	 * 
	 * @param clearTag
	 *            标签序号
	 * @param account
	 *            用户信息
	 * @return
	 * @throws IOException
	 */
	public static boolean updateTagRewardStatus(long clearTag, UserAccountDTO account, RewardStatus status)
			throws IOException {
		List<TagAwardRecordDTO> awardList = getTagAwardFromOTS(clearTag);
		return updateTagRewardStatus(awardList, clearTag, account, status);
	}

	/**
	 * 更新标签中奖奖品状态
	 * 
	 * @param awardList
	 *            标签奖品列表
	 * @param clearTag
	 *            标签序号
	 * @param account
	 *            用户信息
	 * @return
	 * @throws IOException
	 */
	public static boolean updateTagRewardStatus(List<TagAwardRecordDTO> awardList, long clearTag,
			UserAccountDTO account, RewardStatus status) throws IOException {
		if (CollectionUtils.isNotEmpty(awardList)) {
			for (TagAwardRecordDTO tagAwardRecord : awardList) {
				if (account.getName().equals(tagAwardRecord.getAccount().getName())) {
					tagAwardRecord.setCashTime(DateUtil.currentTime());
					tagAwardRecord.setStatus(status.getValue());

					return updateTagRewardStatus(clearTag, awardList);
				}
			}
		}

		return false;
	}

	/**
	 * 更新标签中奖奖品状态
	 * 
	 * @param clearTag
	 *            标签序号
	 * @param account
	 *            用户信息
	 * @param rewardId
	 *            奖品Id
	 * @return
	 * @throws IOException
	 */
	public static boolean updateTagRewardStatus(long clearTag, UserAccountDTO account, int rewardId,
			RewardStatus status) throws IOException {
		List<TagAwardRecordDTO> awardList = getTagAwardFromOTS(clearTag);
		return updateTagRewardStatus(awardList, clearTag, account, rewardId, status);
	}

	/**
	 * 更新标签中奖奖品状态
	 * 
	 * @param awardList
	 *            标签奖品列表
	 * @param clearTag
	 *            标签序号
	 * @param account
	 *            用户信息
	 * @param rewardId
	 *            奖品Id
	 * @return
	 * @throws IOException
	 */
	public static boolean updateTagRewardStatus(List<TagAwardRecordDTO> awardList, long clearTag,
			UserAccountDTO account, int rewardId, RewardStatus status) throws IOException {
		if (CollectionUtils.isNotEmpty(awardList)) {
			for (TagAwardRecordDTO tagAwardRecord : awardList) {

				if (tagAwardRecord.getRewardId() == rewardId) {
					if (account.getId() != null) {
						if (account.getName().equals(tagAwardRecord.getAccount().getName())) {
							tagAwardRecord.setCashTime(DateUtil.currentTime());
							tagAwardRecord.setStatus(status.getValue());

							return updateTagRewardStatus(clearTag, awardList);
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * 更新标签中奖奖品状态
	 * 
	 * @param clearTag
	 *            标签序号
	 * @param awardList
	 *            奖品列表
	 * @return
	 * @throws IOException
	 */
	public static boolean updateTagRewardStatus(long clearTag, List<TagAwardRecordDTO> awardList) throws IOException {
		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsTagTableSliceHelper.getTableName(clearTag));

		rowUpdateChange.setPrimaryKey(getRowPrimaryKey(clearTag));

		rowUpdateChange.addAttributeColumn(TagDTO.tagAwardKey,
				ColumnValue.fromString(new ObjectMapper().writeValueAsString(awardList)));

		rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));

		OtsRowHelper.updateRowWithCLient(otsClient, rowUpdateChange);

		return true;
	}

	/**
	 * 更新指定标签列锁状态
	 * 
	 * @param clearTag
	 *            标签序号
	 * @param columnName
	 *            列名
	 * @param status
	 *            锁状态O
	 * @return 更新成功为true
	 */
	public static boolean updateOperationLockStatus(long clearTag, String columnName, OpreationLockStatus status) {
		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsTagTableSliceHelper.getTableName(clearTag));
		rowUpdateChange.setPrimaryKey(getRowPrimaryKey(clearTag));

		Condition condition = new Condition(RowExistenceExpectation.EXPECT_EXIST);
		ColumnCondition columnCondition = new RelationalCondition(columnName,
				RelationalCondition.CompareOperator.NOT_EQUAL, ColumnValue.fromLong(status.getValue()));
		condition.setColumnCondition(columnCondition);
		rowUpdateChange.setCondition(condition);
		rowUpdateChange.addAttributeColumn(columnName, ColumnValue.fromLong(Long.valueOf(status.getValue())));
		try {
			OtsRowHelper.updateRowWithCLient(otsClient, rowUpdateChange);
		} catch (Exception e) {
			if (OtsRowHelper.isOTSConditionCheckFailException(e)) {
				logger.info("ots condition check fail in updateOperationLockStatus() method.");
			} else {
				logger.error(e.getMessage());
			}

			return false;
		}

		return true;
	}

	/**
	 * 获取标签列锁值
	 * 
	 * @param clearTag
	 *            标签序号
	 * @param columnName
	 *            列名
	 * @return
	 * @throws IOException
	 */
	public static Long getOperationLockStatus(long clearTag, String columnName) throws IOException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableSliceHelper.getTableName(clearTag));
		criteria.setPrimaryKey(getRowPrimaryKey(clearTag));

		criteria.addColumnsToGet(new String[] { columnName });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			// 获取标签列锁值
			return columnMaps.get(columnName) == null ? null : columnMaps.get(columnName).asLong();
		}

		return null;
	}
}
