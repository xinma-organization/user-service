package com.xinma.base.datastore.impl.aliyun.table;

import com.aliyun.openservices.ots.OTSClient;

public class DataStoreBaseTest {
	private String endpoint = "http://touyun-dev.cn-hangzhou.ots.aliyuncs.com";
	private String accessId = "jFGmMqmS8LZABfRB";
	private String accessKey = "PNnhx6TpPRdVbBE94xLGqX0zTrZrMM";
	private String instanceName = "tm-dev";
	
	protected OTSClient otsClient = new OTSClient(endpoint, accessId, accessKey, instanceName);
}
