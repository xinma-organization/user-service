package com.xinma.base.datastore.oldmodel.tag;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 标签查询视图数据传输对象
 * 
 * @author Hoctor
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagDTO implements Serializable {

	private static final long serialVersionUID = -7064263053099021958L;

	public final static String sliceKey = "s";
	public final static String clearTagKey = "c";
	public final static String tagFamilyKey = "f";
	public final static String tagAwardKey = "a";
	public final static String lotteryEnrollKey = "le";
	public final static String lotteryEnrollLockKey = "lel";
	public final static String tagMetadataKey = "m";
	public final static String queryLogKey = "l";
	public final static String traceInfoKey = "t";
	public final static String extraBagKey = "e";
	public final static String rrpKey = "rrp";
	public final static String presetRewardIdKey = "prd";

	/**
	 * slice, ots表分片建
	 */
	private int slice;

	/**
	 * clearTag, clear tag sequence
	 */
	private Long clearTag;

	/**
	 * tagFamily,标签的家庭成员节点信息
	 */
	private TagFamilyDTO tagFamily;

	/**
	 * productionBag,标签生产信息数据
	 */
	private TagMetadataDTO medadata;

	/**
	 * product,产品信息，该信息不存储到OTS中
	 */
	private ProductDTO product;

	/**
	 * queryLog,标签查询记录
	 */
	private QueryLogDTO queryLog;

	/**
	 * traceInfo, 标签追溯信息
	 */
	private List<TraceInfoDTO> traceInfo;

	/**
	 * tagAward, 标签奖品列表
	 */
	private List<TagAwardRecordDTO> tagAward;

	/**
	 * 标签参与的抽奖活动,Integer key为活动Id(promotionId)
	 */
	private Map<Integer, List<TagLotteryRecord>> lotteryEnroll;

	/**
	 * 标签抽奖活动活动锁
	 */
	private int lotteryEnrollLock;

	/**
	 * extraBag, 存储自定义信息
	 */
	private Object extraBag;

	/**
	 * referResourcePrefix, 扫码页面引用资源路径前缀，比如扫码模板引用模板包下的js,css,iamge等文件
	 */
	private String rrp;

	/**
	 * presetRewardId,标签预置的奖项ID
	 */
	private int presetRewardId = -1;

	public int getSlice() {
		return slice;
	}

	public void setSlice(int slice) {
		this.slice = slice;
	}

	public Long getClearTag() {
		return clearTag;
	}

	public void setClearTag(Long clearTag) {
		this.clearTag = clearTag;
	}

	public TagMetadataDTO getMedadata() {
		return medadata;
	}

	public void setMedadata(TagMetadataDTO medadata) {
		this.medadata = medadata;
	}

	public Map<Integer, List<TagLotteryRecord>> getLotteryEnroll() {
		return lotteryEnroll;
	}

	public void setLotteryEnroll(Map<Integer, List<TagLotteryRecord>> lotteryEnroll) {
		this.lotteryEnroll = lotteryEnroll;
	}

	/**
	 * 获取指定活动的抽奖记录
	 * 
	 * @param promotionId
	 * @return
	 */
	public List<TagLotteryRecord> getPromotionLotteryEnroll(int promotionId) {
		if (MapUtils.isEmpty(this.lotteryEnroll)) {
			return null;
		} else {
			return this.lotteryEnroll.get(promotionId);
		}
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public QueryLogDTO getQueryLog() {
		return queryLog;
	}

	public void setQueryLog(QueryLogDTO queryLog) {
		this.queryLog = queryLog;
	}

	public List<TraceInfoDTO> getTraceInfo() {
		return traceInfo;
	}

	public void setTraceInfo(List<TraceInfoDTO> traceInfo) {
		this.traceInfo = traceInfo;
	}

	public TagFamilyDTO getTagFamily() {
		return tagFamily;
	}

	public void setTagFamily(TagFamilyDTO tagFamily) {
		this.tagFamily = tagFamily;
	}

	public List<TagAwardRecordDTO> getTagAward() {
		return tagAward;
	}

	public void setTagAward(List<TagAwardRecordDTO> tagAward) {
		this.tagAward = tagAward;
	}

	public Object getExtraBag() {
		return extraBag;
	}

	public void setExtraBag(Object extraBag) {
		this.extraBag = extraBag;
	}

	public String getRrp() {
		return rrp;
	}

	public void setRrp(String rrp) {
		this.rrp = rrp;
	}

	public int getLotteryEnrollLock() {
		return lotteryEnrollLock;
	}

	public void setLotteryEnrollLock(int lotteryEnrollLock) {
		this.lotteryEnrollLock = lotteryEnrollLock;
	}

	public int getPresetRewardId() {
		return presetRewardId;
	}

	public void setPresetRewardId(int presetRewardId) {
		this.presetRewardId = presetRewardId;
	}
	
	public boolean ifHasChildren() {
		if(tagFamily != null && CollectionUtils.isNotEmpty(tagFamily.getChildern()) && tagFamily.getChildern().size()  > 0) {
			return true;	
		} else {
			return false;
		}
	}

}
