package com.xinma.base.datastore.aliyun.ots.model;

import java.io.Serializable;

public class OtsRequestParam implements Serializable {

	private static final long serialVersionUID = -2337075707992524113L;

	/**
	 * OTS服务的Endpoint
	 */
	private String endpoint;

	/**
	 * STS提供的临时访问ID
	 */
	private String accessKeyId;

	/**
	 * STS提供的访问密钥
	 */
	private String accessKeySecret;

	/**
	 * OTS数据库实例名
	 */
	private String instanceName;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public OtsRequestParam() {
		super();
	}

	public OtsRequestParam(String endpoint, String accessKeyId, String accessKeySecret, String instanceName) {
		super();
		this.endpoint = endpoint;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.instanceName = instanceName;
	}
}
