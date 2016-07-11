package com.xinma.base.datastore.oldmodel.promotion;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinma.base.datastore.enums.EtraceUploadMessageType;

/**
 * 抽奖中奖信息的DTO定义
 * 
 * @author zhangyongyi
 *
 */
/**
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AwardingInfoDTO implements Serializable {

	private static final long serialVersionUID = -1227784824629540238L;

	@JsonProperty("at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date awardTime;// 中奖时间

	@JsonProperty("sn")
	private Long seqNo;

	@JsonProperty("suc")
	private boolean success; // 兑奖是否成功

	@JsonProperty("mi")
	private Long memberId;// 中奖会员ID

	@JsonProperty("mn")
	private String memberName;// 会员姓名

	@JsonProperty("ei")
	private Integer eseId; // 企业ID

	@JsonProperty("pi")
	private Integer promotionId; // 活动ID

	@JsonProperty("pn")
	private String promotionName; // 活动名称

	@JsonProperty("ri")
	private Integer rewardId; // 奖项ID

	@JsonProperty("rn")
	private String rewardName; // 奖项名称

	@JsonProperty("rc")
	private Integer rewardCate; // 奖品类型(RewardsCategory)

	@JsonProperty("zn")
	private String prizeName; // 奖品名

	@JsonProperty("a")
	private Float amount; // 奖品面值

	@JsonProperty("st")
	private String shipTelNum;// 收货人联系电话

	@JsonProperty("sa")
	private String shipAddress;// 收货地址

	@JsonProperty("su")
	private String shipUserName;// 收货人姓名

	@JsonProperty("pc")
	private String postCode;// 收货人邮编

	@JsonProperty("icn")
	private String idCardNumber;

	@JsonProperty("udd")
	private String userDefineData;// 用户自定义属性

	@JsonIgnore
	private EtraceUploadMessageType uploadMessageType = EtraceUploadMessageType.AwardingInfoDTO;

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getEseId() {
		return eseId;
	}

	public void setEseId(Integer eseId) {
		this.eseId = eseId;
	}

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public Integer getRewardId() {
		return rewardId;
	}

	public void setRewardId(Integer rewardId) {
		this.rewardId = rewardId;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public Integer getRewardCate() {
		return rewardCate;
	}

	public void setRewardCate(Integer rewardCate) {
		this.rewardCate = rewardCate;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getShipTelNum() {
		return shipTelNum;
	}

	public void setShipTelNum(String shipTelNum) {
		this.shipTelNum = shipTelNum;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getShipUserName() {
		return shipUserName;
	}

	public void setShipUserName(String shipUserName) {
		this.shipUserName = shipUserName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public EtraceUploadMessageType getUploadMessageType() {
		return uploadMessageType;
	}

	public void setUploadMessageType(EtraceUploadMessageType uploadMessageType) {
		this.uploadMessageType = uploadMessageType;
	}

	public String getUserDefineData() {
		return userDefineData;
	}

	public void setUserDefineData(String userDefineData) {
		this.userDefineData = userDefineData;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

}
