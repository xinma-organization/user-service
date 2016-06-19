package com.xinma.base.datastore.aliyun.ots.util;

import java.util.List;

import org.javatuples.KeyValue;

import com.aliyun.openservices.ots.OTSClient;
import com.aliyun.openservices.ots.model.CapacityUnit;
import com.aliyun.openservices.ots.model.CreateTableRequest;
import com.aliyun.openservices.ots.model.DeleteTableRequest;
import com.aliyun.openservices.ots.model.DeleteTableResult;
import com.aliyun.openservices.ots.model.DescribeTableRequest;
import com.aliyun.openservices.ots.model.DescribeTableResult;
import com.aliyun.openservices.ots.model.PrimaryKeyType;
import com.aliyun.openservices.ots.model.ReservedThroughputChange;
import com.aliyun.openservices.ots.model.TableMeta;
import com.aliyun.openservices.ots.model.UpdateTableRequest;
import com.aliyun.openservices.ots.model.UpdateTableResult;
import com.xinma.base.datastore.aliyun.ots.model.OtsRequestParam;

public class OtsTableHelper {

	/**
	 * 建表
	 * 
	 * @param requestParam
	 *            对服务的endpoint,accessKeyId,accessKeySecret,instanceName的封装
	 * @param tableName
	 *            表名
	 * @param primaryKeyList
	 *            主键设置
	 * @param capacityUnit
	 *            吞吐量设置
	 */
	public static void createTable(final OtsRequestParam requestParam, final String tableName,
			final List<KeyValue<String, PrimaryKeyType>> primaryKeyList, final CapacityUnit capacityUnit) {

		if (primaryKeyList == null || primaryKeyList.isEmpty()) {
			throw new RuntimeException("OTS表不能没有主键");
		}
		OTSClient client = null;
		try {
			client = new OTSClient(requestParam.getEndpoint(), requestParam.getAccessKeyId(),
					requestParam.getAccessKeySecret(), requestParam.getInstanceName());

			TableMeta tableMeta = new TableMeta(tableName);

			for (KeyValue<String, PrimaryKeyType> keyValue : primaryKeyList) {
				tableMeta.addPrimaryKeyColumn(keyValue.getKey(), keyValue.getValue());
			}

			CreateTableRequest request = new CreateTableRequest();
			request.setTableMeta(tableMeta);
			request.setReservedThroughput(capacityUnit);
			client.createTable(request);
		} finally {
		}

	}

	/**
	 * 删除表
	 * 
	 * @param requestParam
	 *            OTS请求参数
	 * @param tableName
	 *            OTS表名
	 * @return 删除OTS表格操作结果
	 */
	public static DeleteTableResult deleteTable(final OtsRequestParam requestParam, final String tableName) {
		OTSClient client = null;
		try {
			client = new OTSClient(requestParam.getEndpoint(), requestParam.getAccessKeyId(),
					requestParam.getAccessKeySecret(), requestParam.getInstanceName());
			DeleteTableRequest request = new DeleteTableRequest(tableName);
			return client.deleteTable(request);
		} finally {
		}

	}

	/**
	 * 修改表读写吞吐量
	 * 
	 * @param requestParam
	 *            OTS请求参数
	 * @param tableName
	 *            OTS表名
	 * @param readCapacityUnit
	 *            OTS读吞吐量
	 * @param writeCapacityUnit
	 *            OTS写吞吐量
	 * @return OTS表吞吐量修改结果
	 */
	public static UpdateTableResult updateTable(final OtsRequestParam requestParam, final String tableName,
			final int readCapacityUnit, final int writeCapacityUnit) {
		if (readCapacityUnit < 0 || writeCapacityUnit < 0) {
			throw new RuntimeException("读吞吐量或写吞吐量不能小于0");
		}
		OTSClient client = null;
		try {
			client = new OTSClient(requestParam.getEndpoint(), requestParam.getAccessKeyId(),
					requestParam.getAccessKeySecret(), requestParam.getInstanceName());

			ReservedThroughputChange capacityChange = new ReservedThroughputChange();
			capacityChange.setReadCapacityUnit(readCapacityUnit);
			capacityChange.setWriteCapacityUnit(writeCapacityUnit);
			UpdateTableRequest request = new UpdateTableRequest(tableName, capacityChange);
			return client.updateTable(request);
		} finally {
		}
	}

	/**
	 * 获取表写吞吐量
	 * 
	 * @param requestParam
	 *            OTS请求参数
	 * @param tableName
	 *            OTS表名
	 * @return OTS指定表的写吞吐量
	 */
	public static int getTableWriteCapacity(final OtsRequestParam requestParam, final String tableName) {
		OTSClient client = null;
		try {
			client = new OTSClient(requestParam.getEndpoint(), requestParam.getAccessKeyId(),
					requestParam.getAccessKeySecret(), requestParam.getInstanceName());

			DescribeTableRequest request = new DescribeTableRequest(tableName);
			DescribeTableResult result = client.describeTable(request);
			return result.getReservedThroughputDetails().getCapacityUnit().getWriteCapacityUnit();
		} finally {
		}
	}
}
