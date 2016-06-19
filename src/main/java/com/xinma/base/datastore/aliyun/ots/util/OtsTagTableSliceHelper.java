package com.xinma.base.datastore.aliyun.ots.util;

public class OtsTagTableSliceHelper {

	// 注意，该值一经定义，不准修改
	private final static String tablePrefixName = "tag";

	// 注意，该值一经定义，不准修改
	private static int tableCount = 10;

	private static int tablePartitionRegion = 1;

	// 注意，该值一经定义，不准修改
	private static int slicePartitionRegion = 5000;

	/**
	 * 获取ots table表名index
	 * 
	 * @param tagNumber
	 *            标签序号
	 * @return OTS表格索引号
	 */
	public static int getTableIndex(Long tagNumber) {
		return (int) (tagNumber % tableCount);
	}

	/**
	 * 获取ots table slice index
	 * 
	 * @param tagNumber
	 *            标签序号
	 * @return OTS标签Slice序号
	 */
	public static int getSliceIndex(Long tagNumber) {
		return (int) (tagNumber % slicePartitionRegion);
	}

	/**
	 * 根据序号获取表名
	 * 
	 * @param tagNumber
	 *            标签序号
	 * @return OTS表格名称
	 */
	public static String getTableName(Long tagNumber) {
		return tablePrefixName + getTableIndex(tagNumber);
	}

	/**
	 * 获取tag表数量总数
	 * 
	 * @return 获取OTS表格数量
	 */
	public static int getTableCount() {
		return tableCount;
	}
}
