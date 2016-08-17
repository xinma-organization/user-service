package com.xinma.base.datastore.impl.aliyun.table;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.KeyValue;
import org.testng.annotations.Test;

import com.aliyun.openservices.ots.model.CapacityUnit;
import com.aliyun.openservices.ots.model.PrimaryKeyType;

public class OtsTableUtilsTest extends DataStoreBaseTest {

	@Test(enabled=false)
	public void createTable() {

		List<KeyValue<String, PrimaryKeyType>> primaryKeyList = new ArrayList<KeyValue<String, PrimaryKeyType>>();
		primaryKeyList.add(KeyValue.with(OtsTagTableConstant.partitionKey, PrimaryKeyType.INTEGER));
		primaryKeyList.add(KeyValue.with(OtsTagTableConstant.tagIdKey, PrimaryKeyType.INTEGER));
		CapacityUnit capacityUnit = new CapacityUnit(0, 0);

		for (int i = 0; i < 16; i++) {
			String tableName = "tagbase" + i;
			OtsTableUtils.createTable(otsClient, tableName, primaryKeyList, capacityUnit);

			tableName = "tag" + i;
			OtsTableUtils.createTable(otsClient, tableName, primaryKeyList, capacityUnit);
		}
	}

	@Test
	public void deleteTable() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getTableWriteCapacity() {
		// throw new RuntimeException("Test not implemented");
	}

	@Test
	public void updateTable() {
		// throw new RuntimeException("Test not implemented");
	}
}
