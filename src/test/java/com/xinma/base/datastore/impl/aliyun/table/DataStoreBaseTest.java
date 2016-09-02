package com.xinma.base.datastore.impl.aliyun.table;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.openservices.ots.OTSClient;

public class DataStoreBaseTest {
	private String otsEndpoint = "http://touyun-dev.cn-hangzhou.ots.aliyuncs.com";
	private String accessId = "jFGmMqmS8LZABfRB";
	private String accessKey = "PNnhx6TpPRdVbBE94xLGqX0zTrZrMM";
	private String instanceName = "tm-dev";

	private String mnsEndpoint = "http://1664047120873882.mns.cn-hangzhou.aliyuncs.com";

	protected OTSClient otsClient = new OTSClient(otsEndpoint, accessId, accessKey, instanceName);

	protected MNSClient mnsClient = new CloudAccount(accessId, accessKey, mnsEndpoint).getMNSClient();

}
