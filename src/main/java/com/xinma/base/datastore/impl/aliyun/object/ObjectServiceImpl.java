package com.xinma.base.datastore.impl.aliyun.object;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.xinma.base.datastore.error.ObjectError;
import com.xinma.base.datastore.exceptions.ObjectException;
import com.xinma.base.datastore.ext.object.ObjectService;

/**
 * 对象存储相关操作接口实现类
 * 
 * @author Alauda
 *
 * @date Aug 2, 2016
 *
 */
public class ObjectServiceImpl implements ObjectService {

	private static Logger logger = LoggerFactory.getLogger(ObjectServiceImpl.class);

	private OSSClient client;

	private String bucketName;

	public ObjectServiceImpl(OSSClient client, String bucketName) {
		super();
		this.client = client;
		this.bucketName = bucketName;
	}

	@Override
	public void mkdir(String folderName) throws ObjectException {
		/*
		 * 这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,
		 * dataStream仍然可以有数据
		 * 照样可以上传下载,只是控制台会对以"/"结尾的Object以文件夹的方式展示,用户可以利用这种方式来实现
		 * 文件夹模拟功能,创建形式上的文件夹
		 */
		byte[] buffer = new byte[0];
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);
		try {
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(0);
			client.putObject(bucketName, folderName, in, objectMeta);
		} catch (Throwable ex) {
			throw new ObjectException(ex, ObjectError.CreateForderErr);
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
				throw new ObjectException(ex, ObjectError.CreateForderErr);
			}
		}
	}

	/**
	 * 列出Bucket中的Object
	 * 
	 * @param client
	 *            OSSClient对象
	 * @param bucketName
	 *            bucket名称
	 * @param delimiter
	 *            用于对Object名字进行分组的字符。
	 *            所有名字包含指定的前缀且第一次出现Delimiter字符之间的object作为一组元素: CommonPrefixes。
	 * @param marker
	 *            设定结果从Marker之后按字母排序的第一个开始返回
	 * @param maxKeys
	 *            限定此次返回object的最大数，如果不设定，默认为100，MaxKeys取值不能大于1000。
	 * @param prefix
	 *            限定返回的object key必须以Prefix作为前缀。注意使用prefix查询时，返回的key中仍会包含Prefix。
	 * @return OSS文件路径列表
	 */
	private List<String> listObject(final OSSClient client, final String bucketName, final String delimiter,
			final String marker, final int maxKeys, final String prefix) {

		String innerMarker = marker;
		// 是否循环的标识
		boolean hasNext = false;
		List<String> filePathList = new ArrayList<String>();
		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

		listObjectsRequest.setDelimiter(delimiter);

		listObjectsRequest.setMaxKeys(maxKeys);

		listObjectsRequest.setPrefix(prefix);

		do {
			listObjectsRequest.setMarker(innerMarker);
			ObjectListing sublisting = client.listObjects(listObjectsRequest);
			if (sublisting.isTruncated()) {
				hasNext = true;
				innerMarker = sublisting.getNextMarker();
			} else {
				hasNext = false;
			}
			for (OSSObjectSummary objectSummary : sublisting.getObjectSummaries()) {
				filePathList.add(objectSummary.getKey());
			}
		} while (hasNext);

		return filePathList;
	}

	@Override
	public List<String> listObjects(String folderName) throws ObjectException {
		return listObject(client, bucketName, null, "", 50, folderName);
	}

	private String getContentType(String objectName) {
		String contentType = "";
		String fileName = objectName.toLowerCase();
		if (fileName.contains("index.html")) {
			contentType = "text/html";
		} else if (fileName.endsWith(".css")) {
			contentType = "text/css";
		} else if (fileName.endsWith(".js")) {
			contentType = "application/x-javascript";
		} else if (fileName.endsWith(".png")) {
			contentType = "image/png";
		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			contentType = "image/jpeg";
		} else if (fileName.endsWith(".gif")) {
			contentType = "image/gif";
		} else if (fileName.endsWith(".bmp")) {
			contentType = "application/x-bmp";
		} else if (fileName.endsWith(".json")) {
			contentType = "application/json";
		} else if (fileName.endsWith(".txt")) {
			contentType = "text/plain";
		} else if (fileName.endsWith(".zip")) {
			contentType = "application/x-zip-compressed";
		} else if (fileName.endsWith(".rar")) {
			contentType = "application/octet-stream";
		}

		return contentType;
	}

	@Override
	public void uploadObject(String objectName, String localFileName) throws ObjectException {
		File file = new File(localFileName);

		InputStream input = null;
		try {
			input = new FileInputStream(file);
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(file.length());

			String contentType = getContentType(objectName);
			if (StringUtils.isBlank(contentType)) {
				logger.warn("unknow content-type of object name {}", objectName);
			} else {
				objectMeta.setHeader("Content-Type", contentType);
			}

			client.putObject(bucketName, objectName, input, objectMeta);
		} catch (IOException e) {
			throw new ObjectException(e, ObjectError.IOExceptionErr);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				throw new ObjectException(e, ObjectError.IOExceptionErr);
			}
		}
	}

	@Override
	public void uploadObject(String objectName, InputStream inputStream) throws ObjectException {
		try {
			ObjectMetadata objectMeta = new ObjectMetadata();
			// maybe blocked by Internet
			objectMeta.setContentLength(inputStream.available());

			String contentType = getContentType(objectName);
			if (StringUtils.isBlank(contentType)) {
				logger.warn("unknow content-type of object name {}", objectName);
			} else {
				objectMeta.setHeader("Content-Type", contentType);
			}

			client.putObject(bucketName, objectName, inputStream, objectMeta);
		} catch (IOException e) {
			throw new ObjectException(e, ObjectError.IOExceptionErr);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new ObjectException(e, ObjectError.IOExceptionErr);
			}
		}

	}

	@Override
	public void deleteObject(String objectName) throws ObjectException {
		while (true) {
			String prefix = objectName.endsWith("/") ? objectName : objectName + "/";
			List<String> children = listObject(client, bucketName, null, "", 1000, prefix);
			if (children.isEmpty()) {
				break;
			} else {
				for (String child : children) {
					client.deleteObject(bucketName, child);
				}
				client.deleteObject(bucketName, objectName);
				if (!client.doesObjectExist(bucketName, objectName)) {
					break;
				}
			}
		}

	}

}
