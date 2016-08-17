package com.xinma.base.datastore.impl.aliyun.table;

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

public class OtsTableUtils {

	/**
	 * 建表
	 * 
	 * @param client
	 *            OTSClient
	 * @param tableName
	 *            表名
	 * @param primaryKeyList
	 *            主键设置
	 * @param capacityUnit
	 *            吞吐量设置
	 */
	public static void createTable(final OTSClient client, final String tableName,
			final List<KeyValue<String, PrimaryKeyType>> primaryKeyList, final CapacityUnit capacityUnit) {

		if (primaryKeyList == null || primaryKeyList.isEmpty()) {
			throw new RuntimeException("OTS表不能没有主键");
		}

		TableMeta tableMeta = new TableMeta(tableName);

		for (KeyValue<String, PrimaryKeyType> keyValue : primaryKeyList) {
			tableMeta.addPrimaryKeyColumn(keyValue.getKey(), keyValue.getValue());
		}

		CreateTableRequest request = new CreateTableRequest();
		request.setTableMeta(tableMeta);
		request.setReservedThroughput(capacityUnit);
		client.createTable(request);
	}

	/**
	 * 删除表
	 * 
	 * @param client
	 *            OTSClient
	 * @param tableName
	 *            OTS表名
	 * @return 删除OTS表格操作结果
	 */
	public static DeleteTableResult deleteTable(final OTSClient client, final String tableName) {
		DeleteTableRequest request = new DeleteTableRequest(tableName);
		return client.deleteTable(request);
	}

	/**
	 * 修改表读写吞吐量
	 * 
	 * @param client
	 *            OTSClient
	 * @param tableName
	 *            OTS表名
	 * @param readCapacityUnit
	 *            OTS读吞吐量
	 * @param writeCapacityUnit
	 *            OTS写吞吐量
	 * @return OTS表吞吐量修改结果
	 */
	public static UpdateTableResult updateTable(final OTSClient client, final String tableName,
			final int readCapacityUnit, final int writeCapacityUnit) {
		if (readCapacityUnit < 0 || writeCapacityUnit < 0) {
			throw new RuntimeException("读吞吐量或写吞吐量不能小于0");
		}

		ReservedThroughputChange capacityChange = new ReservedThroughputChange();
		capacityChange.setReadCapacityUnit(readCapacityUnit);
		capacityChange.setWriteCapacityUnit(writeCapacityUnit);
		UpdateTableRequest request = new UpdateTableRequest(tableName, capacityChange);
		return client.updateTable(request);
	}

	/**
	 * 获取表写吞吐量
	 * 
	 * @param client
	 *            OTSClient
	 * @param tableName
	 *            OTS表名
	 * @return OTS指定表的写吞吐量
	 */
	public static int getTableWriteCapacity(final OTSClient client, final String tableName) {

		DescribeTableRequest request = new DescribeTableRequest(tableName);
		DescribeTableResult result = client.describeTable(request);
		return result.getReservedThroughputDetails().getCapacityUnit().getWriteCapacityUnit();

	}
}
