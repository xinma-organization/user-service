package com.xinma.base.datastore.impl.aliyun.table;

/**
 * OTS标签表不变方法操作封装,该类内所有常量和方法实现不允许修改
 * 
 * @author Alauda
 *
 * @date 2015年7月20日
 *
 */
class OtsTagTableConstant {

	/**
	 * 标签表名前缀
	 */
	private final static String tagTablePrefixName = "tag";

	/**
	 * 标签基本信息表名前缀
	 */
	private final static String tagBaseTablePrefixName = "tagbase";

	/**
	 * 标签表个数
	 */
	private static int tagTableCnt = 16;

	/**
	 * 标签基本信息表个数
	 */
	private static int tagBaseTableCnt = 16;

	/**
	 * 单表分区个数
	 */
	private static int tablePartitionRegionCnt = 5000;

	/*************** table column name define ****************/
	protected static String partitionKey = "p"; // 表分区主键名
	protected static String tagIdKey = "t"; // 表标签序号主键名
	protected static String basicInfoKey = "b"; // 标签基本信息列名
	protected static String accessLogKey = "al"; // 标签访问日志列名
	protected static String awardsKey = "a"; // 标签奖品列表列名
	protected static String lotteryEnrolledKey = "le"; // 标签抽奖记录列名

	/**
	 * 定位tagId所在tag table的index
	 * 
	 * @param tagId
	 *            标签Id
	 * @return tag table索引
	 */
	private static int getTagTableIndex(Long tagId) {
		return (int) (tagId % tagTableCnt);
	}

	/**
	 * 定位tagId所在tagbase table的index
	 * 
	 * @param tagId
	 *            标签Id
	 * @return tagbase table索引
	 */
	private static int getTagBaseTableIndex(Long tagId) {
		return (int) (tagId % tagBaseTableCnt);
	}

	/**
	 * 获取ots tag table partition index
	 * 
	 * @param tagId
	 *            标签Id
	 * @return OTS tag Table标签partition序号
	 */
	protected static int getPartitionIndex(Long tagId) {
		return (int) (tagId % tablePartitionRegionCnt);
	}

	/**
	 * 根据标签序号定位tag表名
	 * 
	 * @param tagId
	 *            标签序号
	 * @return OTS tag table name
	 */
	protected static String getTagTableName(Long tagId) {
		return tagTablePrefixName + getTagTableIndex(tagId);
	}

	/**
	 * 根据标签序号定位tagbase表名
	 * 
	 * @param tagId
	 *            标签序号
	 * @return OTS tagbase table name
	 */
	protected static String getTagBaseTableName(Long tagId) {
		return tagBaseTablePrefixName + getTagBaseTableIndex(tagId);
	}
}
