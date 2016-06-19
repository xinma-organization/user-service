package com.xinma.base.datastore.aliyun.oss.model;

import com.aliyun.oss.ClientConfiguration;

public class OssAccessRequest {
	
	/**
	 *  OSS服务的Endpoint
	 */
	private String endpoint;
	
	/**
	 * STS提供的临时访问ID
	 */
	private String accessKeyId;
	
	/**
	 * STS提供的访问密钥
	 */
	private String secretAccessKey;
	
	/**
	 * STS提供的安全令牌
	 */
	private String securityToken;
	
	/**
	 * 客户端配置 ClientConfiguration。 如果为null则会使用默认配置
	 */
	private ClientConfiguration config;

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

	public String getSecretAccessKey() {
		return secretAccessKey;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

	public ClientConfiguration getConfig() {
		return config;
	}

	public void setConfig(ClientConfiguration config) {
		this.config = config;
	}

	public OssAccessRequest() {
		super();
	}
	
	public OssAccessRequest(String endpoint, String accessKeyId, String secretAccessKey) {
		super();
		this.endpoint = endpoint;
		this.accessKeyId = accessKeyId;
		this.secretAccessKey = secretAccessKey;
	}
}
