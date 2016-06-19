package com.xinma.base.datastore.aliyun.ots.util;

/**
 * 
 * @author zhangyongyi
 *
 */
public class OtsUserTableSliceHelper {
	// 注意，该值一经定义，不准修改
	private final static String userTableName = "user";
	// 注意，该值一经定义，不准修改
	private final static String userCategoryTableName = "user_category";
	// 注意，该值一经定义，不准修改
	private static int slicePartitionRegion = 5000;

	/**
	 * 获取用户表名
	 * 
	 * @return OTS上存储用户信息的表格名称
	 */
	public static String getUserTableName() {
		return userTableName;
	}

	/**
	 * 获取用户类别表名
	 * 
	 * @return OTS上存储用户类型信息的表格名称
	 */
	public static String getUserCategoryTableName() {
		return userCategoryTableName;
	}

	/**
	 * 获取ots 用户表的分片键; 注意,该方法一经使用不得修改
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户ID在用户表的Slice索引
	 */
	public static int getUserSliceIndex(Long userId) {
		return (int) (userId % slicePartitionRegion);
	}

	/**
	 * 获取ots 用户类别表的分片键; 注意,该方法一经使用不得修改
	 * 
	 * @param category
	 *            用户类别
	 * @param id
	 *            类别索引
	 * @return 用户类别Slice索引
	 */
	public static int getUserCategorySliceIndex(String category, String id) {
		return (DJBHash(category + id) % slicePartitionRegion);
	}

	/**
	 * * DJB算法
	 * 
	 * @param str
	 *            需要DJB处理的字符串
	 * @return DJB结果
	 */

	public static int DJBHash(String str) {
		int hash = 5381;

		for (int i = 0; i < str.length(); i++) {
			hash = ((hash << 5) + hash) + str.charAt(i);
		}

		return (hash & 0x7FFFFFFF);
	}
}
