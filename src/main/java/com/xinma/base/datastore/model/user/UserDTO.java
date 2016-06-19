package com.xinma.base.datastore.model.user;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户表实体类
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 4563314758718625511L;

	public final static String sliceKey = "s";
	public final static String idKey = "i";
	public final static String metadataKey = "m";
	public final static String pointKey = "p";
	public final static String pointDetailKey = "pd";
	public final static String pointDetailHistoryKey = "ph";
	public final static String awardKey = "a";
	public final static String awardHistoryKey = "ah";
	public final static String awardEnrollLockKey = "ael";
	public final static String awardClaimLockKey = "acl";
	public final static String scanedTagKey = "t";
	public final static String scanedTagHistoryKey = "th";
	public final static String promotionPointKey = "pp";
	public final static String promotionPointDetailKey = "ppd";
	public final static String walletKey = "w";
	/**
	 * slice, OTS用户表分片建
	 */
	@JsonProperty("s")
	private int slice;

	/**
	 * userId
	 */
	@JsonProperty("i")
	private Long id;

	/**
	 * metadata,用户信息
	 */
	@JsonProperty("m")
	private UserMetadata metadata;

	/**
	 * point, 积分; key为企业Id, value为积分值
	 */
	@JsonProperty("p")
	private Map<Integer, Integer> point;

	/**
	 * point detail,企业积分详情;key为企业Id, value为积分获取详情
	 */
	@JsonProperty("pd")
	private Map<Integer, PointDetailDTO> pointDetail;

	/**
	 * point detail history,企业积分详情历史;key为企业Id, value为积分获取详情
	 */
	@JsonProperty("ph")
	private Map<Integer, PointDetailDTO> pointDetailHistory;

	/**
	 * award,用户中奖信息; key为企业Id, value为获取企业奖品的列表
	 */
	@JsonProperty("a")
	private Map<Integer, UserAwardDTO> award;

	/**
	 * award history,用户中奖信息历史记录; key为企业Id, value为获取企业奖品的列表记录
	 */
	@JsonProperty("ah")
	private Map<Integer, UserAwardDTO> awardHistory;

	/**
	 * 用户抽奖控制锁
	 */
	@JsonProperty("ael")
	private int awardEnrollLock;

	/**
	 * 用户兑奖控制锁
	 */
	@JsonProperty("acl")
	private int awardClaimLock;

	/**
	 * scaned tag, 用户扫码记录; key为企业Id, value为用户扫过某企业标签的集合
	 */
	@JsonProperty("t")
	private Map<Integer, UserTagRecord> tags;

	/**
	 * scaned tag history, 用户扫码记录历史; key为企业Id, value为用户扫过某企业标签的历史集合
	 */
	@JsonProperty("th")
	private Map<Integer, UserTagRecord> tagsHistory;

	/**
	 * promotion points, 用户记录企业活动积分,活动积分用于活动结束后，积分失效; key为活动Id, value为该活动获取总量
	 */
	@JsonProperty("pp")
	private Map<Integer, Integer> promotionPoint;

	/**
	 * promotion points detail, 用户记录企业活动积分,活动积分用于活动结束后，积分失效; key为活动Id,
	 * value积分明细记录
	 */
	@JsonProperty("ppd")
	private Map<Integer, List<PromotionPointDetailRecord>> promotionPointDetail;

	/**
	 * 用户钱包，key为企业Id, value为用户相对于某企业的钱包
	 */
	@JsonProperty("w")
	private Map<Integer, UserWallet> userWallet;

	public int getSlice() {
		return slice;
	}

	public void setSlice(int slice) {
		this.slice = slice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(UserMetadata metadata) {
		this.metadata = metadata;
	}

	public Map<Integer, Integer> getPoint() {
		return point;
	}

	public void setPoint(Map<Integer, Integer> point) {
		this.point = point;
	}

	public Map<Integer, PointDetailDTO> getPointDetail() {
		return pointDetail;
	}

	public void setPointDetail(Map<Integer, PointDetailDTO> pointDetail) {
		this.pointDetail = pointDetail;
	}

	public Map<Integer, PointDetailDTO> getPointDetailHistory() {
		return pointDetailHistory;
	}

	public void setPointDetailHistory(Map<Integer, PointDetailDTO> pointDetailHistory) {
		this.pointDetailHistory = pointDetailHistory;
	}

	public Map<Integer, UserAwardDTO> getAward() {
		return award;
	}

	public void setAward(Map<Integer, UserAwardDTO> award) {
		this.award = award;
	}

	public Map<Integer, UserAwardDTO> getAwardHistory() {
		return awardHistory;
	}

	public void setAwardHistory(Map<Integer, UserAwardDTO> awardHistory) {
		this.awardHistory = awardHistory;
	}

	public int getAwardEnrollLock() {
		return awardEnrollLock;
	}

	public void setAwardEnrollLock(int awardEnrollLock) {
		this.awardEnrollLock = awardEnrollLock;
	}

	public int getAwardClaimLock() {
		return awardClaimLock;
	}

	public void setAwardClaimLock(int awardClaimLock) {
		this.awardClaimLock = awardClaimLock;
	}

	public Map<Integer, UserTagRecord> getTags() {
		return tags;
	}

	public void setTags(Map<Integer, UserTagRecord> tags) {
		this.tags = tags;
	}

	public Map<Integer, UserTagRecord> getTagsHistory() {
		return tagsHistory;
	}

	public void setTagsHistory(Map<Integer, UserTagRecord> tagsHistory) {
		this.tagsHistory = tagsHistory;
	}

	public Map<Integer, Integer> getPromotionPoint() {
		return promotionPoint;
	}

	public void setPromotionPoint(Map<Integer, Integer> promotionPoint) {
		this.promotionPoint = promotionPoint;
	}

	public Map<Integer, List<PromotionPointDetailRecord>> getPromotionPointDetail() {
		return promotionPointDetail;
	}

	public void setPromotionPointDetail(Map<Integer, List<PromotionPointDetailRecord>> promotionPointDetail) {
		this.promotionPointDetail = promotionPointDetail;
	}

	public Map<Integer, UserWallet> getUserWallet() {
		return userWallet;
	}

	public void setUserWallet(Map<Integer, UserWallet> userWallet) {
		this.userWallet = userWallet;
	}
}
