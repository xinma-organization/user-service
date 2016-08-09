package com.xinma.base.datastore.impl.aliyun.table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.aliyun.openservices.ots.OTSClient;
import com.aliyun.openservices.ots.model.ColumnValue;
import com.aliyun.openservices.ots.model.Condition;
import com.aliyun.openservices.ots.model.GetRowResult;
import com.aliyun.openservices.ots.model.PrimaryKeyValue;
import com.aliyun.openservices.ots.model.RowExistenceExpectation;
import com.aliyun.openservices.ots.model.RowPrimaryKey;
import com.aliyun.openservices.ots.model.RowPutChange;
import com.aliyun.openservices.ots.model.RowUpdateChange;
import com.aliyun.openservices.ots.model.SingleRowQueryCriteria;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.xinma.base.datastore.error.TableError;
import com.xinma.base.datastore.exceptions.TableException;
import com.xinma.base.datastore.ext.table.TagTableService;
import com.xinma.base.datastore.model.tag.LotteryEnrolledEO;
import com.xinma.base.datastore.model.tag.TagAccessLogEO;
import com.xinma.base.datastore.model.tag.TagAwardEO;
import com.xinma.base.datastore.model.tag.TagBasicInfoEO;
import com.xinma.base.datastore.model.tag.TagRow;

/**
 * TagTableService阿里云OTS实现方式
 * 
 * @author Alauda
 *
 * @date Jul 20, 2016
 *
 */
public class TagTableServiceImpl implements TagTableService {

	private OTSClient otsClient;

	private ObjectMapper objectMapper = new ObjectMapper();

	public TagTableServiceImpl(OTSClient otsClient) {
		super();
		this.otsClient = otsClient;
	}

	private RowPrimaryKey getTagRowPrimaryKey(Long tagId) {
		RowPrimaryKey rowPrimaryKey = new RowPrimaryKey();
		rowPrimaryKey.addPrimaryKeyColumn(OtsTagTableConstant.partitionKey,
				PrimaryKeyValue.fromLong(OtsTagTableConstant.getPartitionIndex(tagId)));
		rowPrimaryKey.addPrimaryKeyColumn(OtsTagTableConstant.tagIdKey, PrimaryKeyValue.fromLong(tagId));
		return rowPrimaryKey;
	}

	@Override
	public TagRow getTagRow(long tagId) throws TableException {
		TagRow tagRow = null;
		TagBasicInfoEO tagBasicInfo = getTagBasicInfo(tagId);

		if (tagBasicInfo != null) {
			tagRow = new TagRow();
			// 标签基本信息
			tagRow.setBasicInfo(tagBasicInfo);

			SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableConstant.getTagTableName(tagId));
			criteria.setPrimaryKey(getTagRowPrimaryKey(tagId));
			criteria.addColumnsToGet(new String[] {});

			GetRowResult result = OtsRowUtils.getRow(otsClient, criteria);
			Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
			if (columnMaps.size() > 0) {
				try {
					// 标签访问日志
					String accessLogsJson = columnMaps.get(OtsTagTableConstant.accessLogKey) == null ? null
							: columnMaps.get(OtsTagTableConstant.accessLogKey).asString();
					if (StringUtils.isNotBlank(accessLogsJson)) {
						List<TagAccessLogEO> accessLogs = objectMapper.readValue(accessLogsJson,
								new TypeReference<List<TagAccessLogEO>>() {
								});
						tagRow.setAccessLog(accessLogs);
					}

					// 标签奖品记录
					String awardsJson = columnMaps.get(OtsTagTableConstant.awardsKey) == null ? null
							: columnMaps.get(OtsTagTableConstant.awardsKey).asString();
					if (StringUtils.isNotBlank(awardsJson)) {
						List<TagAwardEO> awards = objectMapper.readValue(awardsJson,
								new TypeReference<List<TagAwardEO>>() {
								});
						tagRow.setAwards(awards);
					}

					// 标签抽奖记录
					String lotteryEnrolledJson = columnMaps.get(OtsTagTableConstant.lotteryEnrolledKey) == null ? null
							: columnMaps.get(OtsTagTableConstant.lotteryEnrolledKey).asString();
					if (StringUtils.isNotBlank(lotteryEnrolledJson)) {
						List<LotteryEnrolledEO> lotteryEnrolls = objectMapper.readValue(lotteryEnrolledJson,
								new TypeReference<List<LotteryEnrolledEO>>() {
								});
						tagRow.setLotteryEnrolled(lotteryEnrolls);
					}
				} catch (IOException e) {
					throw new TableException(e, TableError.JsonProcessingExceptionErr);
				}
			}
		}

		return tagRow;
	}

	@Override
	public void putOrUpdateTagBasicInfo(TagBasicInfoEO tagBasicInfo) throws TableException {
		Preconditions.checkNotNull(tagBasicInfo);
		Preconditions.checkNotNull(tagBasicInfo.getMeta());
		Long tagId = tagBasicInfo.getMeta().getTagId();
		Preconditions.checkNotNull(tagId, "标签序号不允许为空。");

		RowPutChange rowPutChange = new RowPutChange(OtsTagTableConstant.getTagBaseTableName(tagId));
		rowPutChange.setPrimaryKey(getTagRowPrimaryKey(tagId));
		rowPutChange.setCondition(new Condition(RowExistenceExpectation.IGNORE));

		try {
			rowPutChange.addAttributeColumn(OtsTagTableConstant.basicInfoKey,
					ColumnValue.fromString(objectMapper.writeValueAsString(tagBasicInfo)));
		} catch (JsonProcessingException e) {
			throw new TableException(e, TableError.JsonProcessingExceptionErr);
		}

		OtsRowUtils.putRow(otsClient, rowPutChange);
	}

	@Override
	public TagBasicInfoEO getTagBasicInfo(long tagId) throws TableException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableConstant.getTagBaseTableName(tagId));
		criteria.setPrimaryKey(getTagRowPrimaryKey(tagId));
		criteria.addColumnsToGet(new String[] { OtsTagTableConstant.basicInfoKey });

		GetRowResult result = OtsRowUtils.getRow(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String basicInfoJson = columnMaps.get(OtsTagTableConstant.basicInfoKey) == null ? null
					: columnMaps.get(OtsTagTableConstant.basicInfoKey).asString();

			if (StringUtils.isNotBlank(basicInfoJson)) {
				try {
					return objectMapper.readValue(basicInfoJson, TagBasicInfoEO.class);
				} catch (IOException e) {
					throw new TableException(e, TableError.JsonProcessingExceptionErr);
				}
			}
		}

		return null;
	}

	@Override
	public void addTagAccessLog(long tagId, TagAccessLogEO accessLog) throws TableException {
		List<TagAccessLogEO> tagAccessLogs = getTagAccessLogs(tagId);
		if (tagAccessLogs == null) {
			tagAccessLogs = new ArrayList<TagAccessLogEO>();
		}
		tagAccessLogs.add(accessLog);

		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsTagTableConstant.getTagTableName(tagId));
		rowUpdateChange.setPrimaryKey(getTagRowPrimaryKey(tagId));
		try {
			rowUpdateChange.addAttributeColumn(OtsTagTableConstant.accessLogKey,
					ColumnValue.fromString(objectMapper.writeValueAsString(tagAccessLogs)));
		} catch (JsonProcessingException e) {
			throw new TableException(e, TableError.JsonProcessingExceptionErr);
		}

		rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));

		OtsRowUtils.updateRow(otsClient, rowUpdateChange);
	}

	@Override
	public List<TagAccessLogEO> getTagAccessLogs(long tagId) throws TableException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableConstant.getTagTableName(tagId));
		criteria.setPrimaryKey(getTagRowPrimaryKey(tagId));
		criteria.addColumnsToGet(new String[] { OtsTagTableConstant.accessLogKey });

		GetRowResult result = OtsRowUtils.getRow(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String accessLogsJson = columnMaps.get(OtsTagTableConstant.accessLogKey) == null ? null
					: columnMaps.get(OtsTagTableConstant.accessLogKey).asString();

			if (StringUtils.isNotBlank(accessLogsJson)) {
				try {
					return objectMapper.readValue(accessLogsJson, new TypeReference<List<TagAccessLogEO>>() {
					});
				} catch (IOException e) {
					throw new TableException(e, TableError.JsonProcessingExceptionErr);
				}
			}
		}

		return null;
	}

	@Override
	public void addTagAward(long tagId, TagAwardEO tagAward) throws TableException {
		List<TagAwardEO> tagAwards = getTagAwards(tagId);
		if (tagAwards == null) {
			tagAwards = new ArrayList<TagAwardEO>();
		}
		tagAwards.add(tagAward);

		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsTagTableConstant.getTagTableName(tagId));
		rowUpdateChange.setPrimaryKey(getTagRowPrimaryKey(tagId));
		try {
			rowUpdateChange.addAttributeColumn(OtsTagTableConstant.awardsKey,
					ColumnValue.fromString(objectMapper.writeValueAsString(tagAwards)));
		} catch (JsonProcessingException e) {
			throw new TableException(e, TableError.JsonProcessingExceptionErr);
		}

		rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));

		OtsRowUtils.updateRow(otsClient, rowUpdateChange);

	}

	@Override
	public List<TagAwardEO> getTagAwards(long tagId) throws TableException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableConstant.getTagTableName(tagId));
		criteria.setPrimaryKey(getTagRowPrimaryKey(tagId));
		criteria.addColumnsToGet(new String[] { OtsTagTableConstant.awardsKey });

		GetRowResult result = OtsRowUtils.getRow(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String awardsJson = columnMaps.get(OtsTagTableConstant.awardsKey) == null ? null
					: columnMaps.get(OtsTagTableConstant.awardsKey).asString();

			if (StringUtils.isNotBlank(awardsJson)) {
				try {
					return objectMapper.readValue(awardsJson, new TypeReference<List<TagAwardEO>>() {
					});
				} catch (IOException e) {
					throw new TableException(e, TableError.JsonProcessingExceptionErr);
				}
			}
		}

		return null;
	}

	@Override
	public void addLotteryEnrolled(long tagId, LotteryEnrolledEO lotteryEnrolled) throws TableException {
		List<LotteryEnrolledEO> lotteryEnrolls = getTagLotteryEnrolled(tagId);
		if (lotteryEnrolls == null) {
			lotteryEnrolls = new ArrayList<LotteryEnrolledEO>();
		}
		lotteryEnrolls.add(lotteryEnrolled);

		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsTagTableConstant.getTagTableName(tagId));
		rowUpdateChange.setPrimaryKey(getTagRowPrimaryKey(tagId));
		try {
			rowUpdateChange.addAttributeColumn(OtsTagTableConstant.lotteryEnrolledKey,
					ColumnValue.fromString(objectMapper.writeValueAsString(lotteryEnrolls)));
		} catch (JsonProcessingException e) {
			throw new TableException(e, TableError.JsonProcessingExceptionErr);
		}

		rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));

		OtsRowUtils.updateRow(otsClient, rowUpdateChange);
	}

	@Override
	public List<LotteryEnrolledEO> getTagLotteryEnrolled(long tagId) throws TableException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsTagTableConstant.getTagTableName(tagId));
		criteria.setPrimaryKey(getTagRowPrimaryKey(tagId));
		criteria.addColumnsToGet(new String[] { OtsTagTableConstant.lotteryEnrolledKey });

		GetRowResult result = OtsRowUtils.getRow(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String lotteryEnrolledJson = columnMaps.get(OtsTagTableConstant.lotteryEnrolledKey) == null ? null
					: columnMaps.get(OtsTagTableConstant.lotteryEnrolledKey).asString();

			if (StringUtils.isNotBlank(lotteryEnrolledJson)) {
				try {
					return objectMapper.readValue(lotteryEnrolledJson, new TypeReference<List<LotteryEnrolledEO>>() {
					});
				} catch (IOException e) {
					throw new TableException(e, TableError.JsonProcessingExceptionErr);
				}
			}
		}

		return null;
	}

}
