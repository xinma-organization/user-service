package com.xinma.base.datastore.aliyun.ots.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinma.base.datastore.enums.RewardStatus;
import com.xinma.base.datastore.enums.UserCategory;
import com.xinma.base.datastore.oldmodel.user.PromotionPointDetailRecord;
import com.xinma.base.datastore.oldmodel.user.UserAccountDTO;
import com.xinma.base.datastore.oldmodel.user.UserAwardDTO;
import com.xinma.base.datastore.oldmodel.user.UserAwardRecord;
import com.xinma.base.datastore.oldmodel.user.UserDTO;
import com.xinma.base.datastore.oldmodel.user.UserMetadata;
import com.xinma.base.datastore.oldmodel.user.UserTagRecord;
import com.xinma.base.datastore.oldmodel.user.UserWallet;
import com.xinma.base.util.DateUtil;

// TODO consider RewardStatus enum
public class OtsUserHelper {
	private static Logger logger = LoggerFactory.getLogger(OtsUserHelper.class);

	private static OTSClient otsClient = null;

	public static void setOtsClient(OTSClient client) {
		otsClient = client;
	}

	/**
	 * 从用户类别表中获取userId
	 * 
	 * @param userCategory
	 *            用户类别
	 * @param name
	 *            用户登录名
	 * @return
	 */
	public static Long getUserIdFromOTS(String userCategory, String name) {

		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(
				OtsUserTableSliceHelper.getUserCategoryTableName());
		criteria.setPrimaryKey(getUserCateRowPrimaryKey(userCategory, name));

		criteria.addColumnsToGet(new String[] { UserAccountDTO.IdKey });
		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			return columnMaps.get(UserAccountDTO.IdKey).asLong();
		} else {
			return null;
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userType
	 *            用户类别
	 * @param name
	 *            用户登录名
	 * @return
	 */
	public static UserAccountDTO getAccount(UserCategory userType, String name) {
		Long id = OtsUserHelper.getUserIdFromOTS(userType.getValue(), name);
		if (id == null) {
			return null;
		}

		UserAccountDTO account = new UserAccountDTO();
		account.setId(id);
		account.setName(name);
		account.setType(userType.getValue());

		return account;
	}

	/**
	 * 获取用户类别表行主键
	 */
	public static RowPrimaryKey getUserCateRowPrimaryKey(String userType, String userName) {
		RowPrimaryKey rowPrimaryKey = new RowPrimaryKey();
		rowPrimaryKey.addPrimaryKeyColumn(UserAccountDTO.sliceKey,
				PrimaryKeyValue.fromLong(OtsUserTableSliceHelper.getUserCategorySliceIndex(userType, userName)));
		rowPrimaryKey.addPrimaryKeyColumn(UserAccountDTO.typeKey, PrimaryKeyValue.fromString(userType));
		rowPrimaryKey.addPrimaryKeyColumn(UserAccountDTO.nameKey, PrimaryKeyValue.fromString(userName));
		return rowPrimaryKey;
	}

	/**
	 * 获取用户表行主键
	 */
	public static RowPrimaryKey getUserRowPrimaryKey(Long userId) {
		RowPrimaryKey rowPrimaryKey = new RowPrimaryKey();
		rowPrimaryKey.addPrimaryKeyColumn(UserDTO.sliceKey,
				PrimaryKeyValue.fromLong(OtsUserTableSliceHelper.getUserSliceIndex(userId)));
		rowPrimaryKey.addPrimaryKeyColumn(UserDTO.idKey, PrimaryKeyValue.fromLong(userId));
		return rowPrimaryKey;
	}

	/**
	 * 获取用户中奖记录集合(第一页)
	 * 
	 * @param userType
	 *            用户类别
	 * @param userName
	 *            用户登录名
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer, UserAwardDTO> getUserAwardFromOTS(String userType, String userName) throws IOException {
		Long userId = getUserIdFromOTS(userType, userName);
		if (userId != null) {
			return getUserAwardFromOTS(userId);
		} else {
			return null;
		}
	}

	/**
	 * 获取所用用户中奖记录集合(所有页)
	 * 
	 * @param userType
	 *            用户类别
	 * @param userName
	 *            用户登录名
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer, UserAwardDTO> getAllUserAwardsPageFromOTS(String userType, String userName)
			throws IOException {
		Long userId = getUserIdFromOTS(userType, userName);
		if (userId != null) {
			return getAllUserAwardsPageFromOTS(userId);
		} else {
			return null;
		}
	}

	private static Map<Integer, UserAwardDTO> mergeUserAwardMap(Long userId, Map<Integer, UserAwardDTO> awardsMap)
			throws IOException {
		if (MapUtils.isEmpty(awardsMap)) {
			return null;
		} else {
			Collection<UserAwardDTO> values = awardsMap.values();
			for (UserAwardDTO userAward : values) {
				if (userAward.getTotalPage() >= 1) {
					for (int i = 1; i <= userAward.getTotalPage(); i++) {
						Map<Integer, UserAwardDTO> pageMap = getUserAwardPageFromOTS(userId, i);
						if (MapUtils.isNotEmpty(pageMap)) {
							Set<Integer> keys = pageMap.keySet();
							for (Integer key : keys) {
								if (pageMap.get(key) != null
										&& CollectionUtils.isNotEmpty(pageMap.get(key).getRecords())) {
									if (awardsMap.containsKey(key)) {
										awardsMap.get(key).appendAwardRecords(pageMap.get(key).getRecords());
									} else {
										awardsMap.put(key, pageMap.get(key));
									}
								}
							}
						}
					}
				}
			}
		}

		return awardsMap;
	}

	public static Map<Integer, UserAwardDTO> getUserAwardPageFromOTS(Long userId, int page) throws IOException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsUserTableSliceHelper.getUserTableName());
		criteria.setPrimaryKey(getUserRowPrimaryKey(userId));

		String columnName = UserDTO.awardKey;
		if (page >= 1) {
			columnName = columnName + page;

		}
		criteria.addColumnsToGet(new String[] { columnName });
		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String awardJson = columnMaps.get(columnName) == null ? null : columnMaps.get(columnName).asString();
			if (awardJson != null && !awardJson.isEmpty()) {
				return new ObjectMapper().readValue(awardJson, new TypeReference<Map<Integer, UserAwardDTO>>() {
				});
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取用户中奖记录集合
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer, UserAwardDTO> getUserAwardFromOTS(Long userId) throws IOException {
		return getUserAwardPageFromOTS(userId, 0);
	}

	/**
	 * 获取所有用户中奖记录集合
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer, UserAwardDTO> getAllUserAwardsPageFromOTS(Long userId) throws IOException {
		Map<Integer, UserAwardDTO> awardsMap = getUserAwardPageFromOTS(userId, 0);
		return mergeUserAwardMap(userId, awardsMap);
	}

	/**
	 * 获取用户钱包
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer, UserWallet> getUserWalletFromOTS(Long userId) throws IOException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsUserTableSliceHelper.getUserTableName());
		criteria.setPrimaryKey(getUserRowPrimaryKey(userId));

		criteria.addColumnsToGet(new String[] { UserDTO.walletKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String walletJson = columnMaps.get(UserDTO.walletKey) == null ? null
					: columnMaps.get(UserDTO.walletKey).asString();
			if (walletJson != null && !walletJson.isEmpty()) {
				return new ObjectMapper().readValue(walletJson, new TypeReference<Map<Integer, UserWallet>>() {
				});
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取用户相对于某企业的钱包
	 * 
	 * @param userId
	 *            用户Id
	 * @param eseId
	 *            企业Id
	 * @return
	 * @throws IOException
	 */
	public static UserWallet getUserWalletFromOTS(Long userId, int eseId) throws IOException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsUserTableSliceHelper.getUserTableName());
		criteria.setPrimaryKey(getUserRowPrimaryKey(userId));

		criteria.addColumnsToGet(new String[] { UserDTO.walletKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String walletJson = columnMaps.get(UserDTO.walletKey) == null ? null
					: columnMaps.get(UserDTO.walletKey).asString();
			if (walletJson != null && !walletJson.isEmpty()) {
				Map<Integer, UserWallet> walletMap = new ObjectMapper().readValue(walletJson,
						new TypeReference<Map<Integer, UserWallet>>() {
						});

				return walletMap.get(eseId);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 根据用户Id,获取所中某企业的奖品
	 * 
	 * @param userId
	 *            用户Id
	 * @param eseId
	 *            企业Id
	 * @return UserAwardDTO
	 * @throws IOException
	 */
	public static UserAwardDTO getEnterpriseUserAwardFromOTS(Long userId, Integer eseId) throws IOException {
		Map<Integer, UserAwardDTO> awardMap = getUserAwardFromOTS(userId);
		if (awardMap != null) {
			return awardMap.get(eseId);
		} else {
			return null;
		}
	}

	/**
	 * 根据用户Id,获取所中某企业的奖品
	 * 
	 * @param userType
	 * @param userName
	 * @param eseId
	 * @return
	 * @throws IOException
	 */
	public static UserAwardDTO getEnterpriseUserAwardFromOTS(String userType, String userName, Integer eseId)
			throws IOException {
		Map<Integer, UserAwardDTO> awardMap = getUserAwardFromOTS(userType, userName);
		if (awardMap != null) {
			return awardMap.get(eseId);
		} else {
			return null;
		}
	}

	/**
	 * 获取用户元信息
	 * 
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public static UserMetadata getUserMetadataFromOTS(Long userId) throws IOException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsUserTableSliceHelper.getUserTableName());
		criteria.setPrimaryKey(getUserRowPrimaryKey(userId));

		criteria.addColumnsToGet(new String[] { UserDTO.metadataKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String metadataJson = columnMaps.get(UserDTO.metadataKey) == null ? null
					: columnMaps.get(UserDTO.metadataKey).asString();
			if (metadataJson != null && !metadataJson.isEmpty()) {
				return new ObjectMapper().readValue(metadataJson, UserMetadata.class);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取用户元信息
	 * 
	 * @param userType
	 * @param userName
	 * @return
	 * @throws IOException
	 */
	public static UserMetadata getUserMetadataFromOTS(String userType, String userName) throws IOException {
		Long userId = getUserIdFromOTS(userType, userName);
		if (userId != null) {
			return getUserMetadataFromOTS(userId);
		} else {
			return null;
		}
	}

	/**
	 * 更新用户元信息
	 * 
	 * @param userType
	 *            用户类型
	 * @param userName
	 *            用户名字
	 * @param userMetadata
	 * @return
	 * @throws IOException
	 */
	public static boolean updateUserMetadata(String userType, String userName, UserMetadata userMetadata)
			throws IOException {

		Long userId = getUserIdFromOTS(userType, userName);
		return updateUserMetadata(userId, userMetadata);
	}

	/**
	 * 更新用户元信息
	 * 
	 * @param userId
	 *            用户Id
	 * @param userMetadata
	 * @return
	 * @throws IOException
	 */
	public static boolean updateUserMetadata(Long userId, UserMetadata userMetadata) {
		if (userId != null) {
			RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsUserTableSliceHelper.getUserTableName());

			rowUpdateChange.setPrimaryKey(getUserRowPrimaryKey(userId));
			String metadataString = null;
			try {
				metadataString = new ObjectMapper().writeValueAsString(userMetadata);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (StringUtils.isNotBlank(metadataString)) {
				rowUpdateChange.addAttributeColumn(UserDTO.metadataKey, ColumnValue.fromString(metadataString));
			}

			rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));

			OtsRowHelper.updateRowWithCLient(otsClient, rowUpdateChange);

			return true;
		} else {
			return false;
		}
	}

	/**
	 * 用户奖品分页
	 * 
	 * @param eseId
	 *            企业ID
	 * @param userAward
	 *            用户企业奖品集合
	 * @return 分页集合
	 */
	public static Map<Integer, UserAwardDTO> userAwardPagination(int eseId, UserAwardDTO userAward) {
		Map<Integer, UserAwardDTO> pageMap = new HashMap<>();

		UserAwardDTO pageUserAward = new UserAwardDTO();
		pageMap.put(eseId, pageUserAward);

		Iterator<UserAwardRecord> it = userAward.getRecords().iterator();
		while (it.hasNext()) {
			UserAwardRecord userAwardRecord = it.next();
			if (userAwardRecord.getStatus() == RewardStatus.ClaimSuccess.getValue()) {
				pageUserAward.appendAwardRecord(userAwardRecord);
				it.remove();
			} else if (userAwardRecord.getProductId() == 13 && userAwardRecord.getTradeType().equals(0)) {
				// 君畅奖品特殊处理
				pageUserAward.appendAwardRecord(userAwardRecord);
				it.remove();
			}
		}

		return pageMap;
	}

	/**
	 * 获取用户扫描标签记录集合
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer, UserTagRecord> getUserTagRecordsFromOTS(Long userId) throws IOException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsUserTableSliceHelper.getUserTableName());
		criteria.setPrimaryKey(getUserRowPrimaryKey(userId));

		criteria.addColumnsToGet(new String[] { UserDTO.scanedTagKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String tagsJson = columnMaps.get(UserDTO.scanedTagKey) == null ? null
					: columnMaps.get(UserDTO.scanedTagKey).asString();
			if (tagsJson != null && !tagsJson.isEmpty()) {
				return new ObjectMapper().readValue(tagsJson, new TypeReference<Map<Integer, UserTagRecord>>() {
				});
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static boolean updateUserTagRecord(long userId, int eseId, long clearTagId) throws IOException {
		boolean isNewAccountForEnterprise = false;

		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsUserTableSliceHelper.getUserTableName());

		rowUpdateChange.setPrimaryKey(getUserRowPrimaryKey(userId));
		Map<Integer, UserTagRecord> map = getUserTagRecordsFromOTS(userId);
		UserTagRecord record = null;
		if (map == null) {
			record = new UserTagRecord();
			map = new HashMap<>();
			isNewAccountForEnterprise = true;
		} else {
			record = map.get(eseId);
			if (record == null) {
				record = new UserTagRecord();
				isNewAccountForEnterprise = true;
			}
		}
		if (record.getTotalCount() >= 100) {
			Map<Integer, UserTagRecord> pageMap = new HashMap<>();
			UserTagRecord pageRecord = new UserTagRecord();
			pageRecord.appendTags2Set(record.getTags());

			record.setTotalPage(record.getTotalPage() + 1);
			record.getTags().clear();
			record.setTotalCount(0);

			rowUpdateChange.addAttributeColumn(UserDTO.scanedTagKey + record.getTotalPage(),
					ColumnValue.fromString(new ObjectMapper().writeValueAsString(pageMap)));
		}

		record.appendTag2Set(clearTagId);
		map.put(eseId, record);

		rowUpdateChange.addAttributeColumn(UserDTO.scanedTagKey,
				ColumnValue.fromString(new ObjectMapper().writeValueAsString(map)));

		rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));

		OtsRowHelper.updateRowWithCLient(otsClient, rowUpdateChange);

		return isNewAccountForEnterprise;
	}

	/**
	 * 更新用户中奖的奖品状态
	 * 
	 * @param userId
	 *            用户Id
	 * @param eseId
	 *            企业Id
	 * @param clearTag
	 *            标签序号
	 * @param rewardId
	 *            奖品Id
	 * @param status
	 *            兑奖状态
	 * @throws IOException
	 */
	public static boolean updateRewardStatus(long userId, int eseId, long clearTag, int rewardId, RewardStatus status)
			throws IOException {
		Map<Integer, UserAwardDTO> awardMap = getUserAwardFromOTS(userId);
		if (awardMap != null) {
			UserAwardDTO userAward = awardMap.get(eseId);
			if (userAward != null) {
				for (UserAwardRecord awardRecord : userAward.getRecords()) {
					if (awardRecord.getRewardId() == rewardId && awardRecord.getClearTag().longValue() == clearTag) {
						awardRecord.setCashTime(DateUtil.currentTime());
						awardRecord.setStatus(status.getValue());

						return updateRewardStatus(userId, awardMap);
					}
				}
			}
		}

		return false;
	}

	/**
	 * 更新用户中奖的奖品状态及额外信息
	 * 
	 * @param userId
	 *            用户Id
	 * @param eseId
	 *            企业Id
	 * @param clearTag
	 *            标签序号
	 * @param rewardId
	 *            奖品Id
	 * @param status
	 *            兑奖状态
	 * @param extraBag
	 *            附加信息
	 * @return
	 * @throws IOException
	 */
	public static boolean updateRewardStatus(long userId, int eseId, long clearTag, int rewardId, RewardStatus status,
			Object extraBag) throws IOException {
		Map<Integer, UserAwardDTO> awardMap = getUserAwardFromOTS(userId);
		if (awardMap != null) {
			UserAwardDTO userAward = awardMap.get(eseId);
			if (userAward != null) {
				for (UserAwardRecord awardRecord : userAward.getRecords()) {
					if (awardRecord.getRewardId() == rewardId && awardRecord.getClearTag().longValue() == clearTag) {
						awardRecord.setCashTime(DateUtil.currentTime());
						awardRecord.setStatus(status.getValue());
						awardRecord.setExtraBag(new ObjectMapper().writeValueAsString(extraBag));

						return updateRewardStatus(userId, awardMap);
					}
				}
			}
		}

		return false;
	}

	/**
	 * 更新用户中奖的奖品状态
	 * 
	 * @param userId
	 *            用户Id
	 * @param eseId
	 *            企业Id
	 * @param rewardId
	 *            奖品Id
	 * @param status
	 *            兑奖状态
	 * @return
	 * @throws IOException
	 */
	public static boolean updateRewardStatus(long userId, int eseId, int rewardId, RewardStatus status)
			throws IOException {
		Map<Integer, UserAwardDTO> awardMap = getUserAwardFromOTS(userId);
		if (awardMap != null) {
			UserAwardDTO userAward = awardMap.get(eseId);
			if (userAward != null) {
				for (UserAwardRecord awardRecord : userAward.getRecords()) {
					if (awardRecord.getRewardId() == rewardId) {
						awardRecord.setCashTime(DateUtil.currentTime());
						awardRecord.setStatus(status.getValue());

						return updateRewardStatus(userId, awardMap);
					}
				}
			}
		}

		return false;
	}

	/**
	 * 更新用户中奖的奖品状态
	 * 
	 * @param userId
	 *            用户Id
	 * @param eseId
	 *            企业Id
	 * @param clearTag
	 *            标签序号Id
	 * @param status
	 *            兑奖状态
	 * @return
	 * @throws IOException
	 */
	public static boolean updateRewardStatus(long userId, int eseId, long clearTag, RewardStatus status)
			throws IOException {
		Map<Integer, UserAwardDTO> awardMap = getUserAwardFromOTS(userId);
		if (awardMap != null) {
			UserAwardDTO userAward = awardMap.get(eseId);
			if (userAward != null) {
				for (UserAwardRecord awardRecord : userAward.getRecords()) {
					if (awardRecord.getClearTag().longValue() == clearTag) {
						awardRecord.setCashTime(DateUtil.currentTime());
						awardRecord.setStatus(status.getValue());

						return updateRewardStatus(userId, awardMap);
					}
				}
			}
		}

		return false;
	}

	/**
	 * 更新用户中奖的奖品状态
	 * 
	 * @param userId
	 *            用户Id
	 * @param awardMap
	 *            奖项记录
	 * @return
	 * @throws IOException
	 */
	public static boolean updateRewardStatus(long userId, Map<Integer, UserAwardDTO> awardMap) throws IOException {
		RowUpdateChange rowUpdateChange = new RowUpdateChange(OtsUserTableSliceHelper.getUserTableName());

		rowUpdateChange.setPrimaryKey(getUserRowPrimaryKey(userId));
		rowUpdateChange.addAttributeColumn(UserDTO.awardKey,
				ColumnValue.fromString(new ObjectMapper().writeValueAsString(awardMap)));
		rowUpdateChange.setCondition(new Condition(RowExistenceExpectation.EXPECT_EXIST));
		OtsRowHelper.updateRowWithCLient(otsClient, rowUpdateChange);

		return true;
	}

	/**
	 * 获取用户活动积分信息
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer, Integer> getPromotionPointsFromOTS(Long userId) throws IOException {
		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsUserTableSliceHelper.getUserTableName());
		criteria.setPrimaryKey(getUserRowPrimaryKey(userId));

		criteria.addColumnsToGet(new String[] { UserDTO.promotionPointKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			String promotionPoints = columnMaps.get(UserDTO.promotionPointKey) == null ? null
					: columnMaps.get(UserDTO.promotionPointKey).asString();
			if (promotionPoints != null && !promotionPoints.isEmpty()) {
				return new ObjectMapper().readValue(promotionPoints, new TypeReference<Map<Integer, Integer>>() {
				});
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取用户活动积分信息
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 * @throws IOException
	 */
	public static Pair<Map<Integer, Integer>, Map<Integer, List<PromotionPointDetailRecord>>> getPromotionPointsDetailFromOTS(
			Long userId) throws IOException {

		SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsUserTableSliceHelper.getUserTableName());
		criteria.setPrimaryKey(getUserRowPrimaryKey(userId));

		criteria.addColumnsToGet(new String[] { UserDTO.promotionPointKey, UserDTO.promotionPointDetailKey });

		GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
		Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
		if (columnMaps.size() > 0) {
			ObjectMapper mapper = new ObjectMapper();
			Map<Integer, Integer> promotionPointsMap = null;
			String promotionPoints = columnMaps.get(UserDTO.promotionPointKey) == null ? null
					: columnMaps.get(UserDTO.promotionPointKey).asString();
			if (promotionPoints != null && !promotionPoints.isEmpty()) {
				promotionPointsMap = mapper.readValue(promotionPoints, new TypeReference<Map<Integer, Integer>>() {
				});
			}

			Map<Integer, List<PromotionPointDetailRecord>> promotionPointDetailMap = null;
			String promotionPointsDetail = columnMaps.get(UserDTO.promotionPointDetailKey) == null ? null
					: columnMaps.get(UserDTO.promotionPointDetailKey).asString();
			if (promotionPointsDetail != null && !promotionPointsDetail.isEmpty()) {
				promotionPointDetailMap = mapper.readValue(promotionPointsDetail,
						new TypeReference<Map<Integer, List<PromotionPointDetailRecord>>>() {
						});
			}

			return Pair.with(promotionPointsMap, promotionPointDetailMap);

		} else {
			return Pair.with(null, null);
		}
	}

	/**
	 * 获取用户某企业未兑奖中奖列表
	 * 
	 * @param account
	 *            用户账户信息
	 * @param eseId
	 *            企业Id
	 * @return
	 * @throws IOException
	 */
	public static List<UserAwardRecord> getUnClaimRewards(UserAccountDTO account, int eseId) throws IOException {
		List<UserAwardRecord> unClaimAwardRecords = new ArrayList<>();
		if (account != null) {
			// 校验用户兑奖条件
			Map<Integer, UserAwardDTO> userAwardMap = OtsUserHelper.getUserAwardFromOTS(account.getId());
			if (MapUtils.isNotEmpty(userAwardMap)) {
				UserAwardDTO userAward = userAwardMap.get(eseId);
				if (userAward != null) {
					List<UserAwardRecord> awardRecords = userAward.getRecords();
					if (CollectionUtils.isNotEmpty(awardRecords)) {

						for (UserAwardRecord awardRecord : awardRecords) {
							if (awardRecord.getStatus() != RewardStatus.ClaimSuccess.getValue()) {
								unClaimAwardRecords.add(awardRecord);
							}
						}
					}
				}
			}
		}

		return unClaimAwardRecords;
	}

	/**
	 * 获取用户列操作锁值
	 * 
	 * @param account
	 *            用户信息
	 * @param columnName
	 *            列名
	 * @return 锁值
	 */
	public static Long getOperationLockStatus(UserAccountDTO account, String columnName) {
		Long userId = account.getId();
		if (userId == null) {
			userId = getUserIdFromOTS(account.getType(), account.getName());
			account.setId(userId);
		}

		try {
			if (userId == null) {
				logger.error("获取操作锁信息失败，用户{}不存在", new ObjectMapper().writeValueAsString(account));
				return null;
			}

			SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(OtsUserTableSliceHelper.getUserTableName());
			criteria.setPrimaryKey(getUserRowPrimaryKey(userId));

			criteria.addColumnsToGet(new String[] { columnName });

			GetRowResult result = OtsRowHelper.getRowWithClient(otsClient, criteria);
			Map<String, ColumnValue> columnMaps = result.getRow().getColumns();
			if (columnMaps.size() > 0) {
				return columnMaps.get(columnName) == null ? null : columnMaps.get(columnName).asLong();
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

}
