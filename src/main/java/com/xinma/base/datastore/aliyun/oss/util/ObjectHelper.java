package com.xinma.base.datastore.aliyun.oss.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 阿里云 OSS Object操作工具类
 * 
 * @author Hoctor
 *
 */
public class ObjectHelper {

	/**
	 * 创建模拟文件夹 (OSS服务是没有文件夹这个概念的，所有元素都是以Object来存储。)
	 * 
	 * @param client
	 *            OSSClient
	 * @param bucketName
	 *            指定object所在的bucket
	 * @param objectName
	 *            即模拟文件夹名(可以创建多级目录,objectName必须以"/"为结尾，例如folder/a/)
	 * @throws IOException
	 *             ByteArrayInputStream关闭时可能抛出IO异常
	 */
	public static void createFolder(final OSSClient client, final String bucketName, final String objectName)
			throws IOException {
		ObjectMetadata objectMeta = new ObjectMetadata();
		/*
		 * 这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,
		 * dataStream仍然可以有数据
		 * 照样可以上传下载,只是控制台会对以"/"结尾的Object以文件夹的方式展示,用户可以利用这种方式来实现
		 * 文件夹模拟功能,创建形式上的文件夹
		 */
		byte[] buffer = new byte[0];
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);
		objectMeta.setContentLength(0);
		try {
			client.putObject(bucketName, objectName, in, objectMeta);
		} finally {
			in.close();
		}
	}

	/**
	 * 上传文件至OSS
	 * 
	 * @param client
	 *            OSSClient
	 * @param bucketName
	 *            指定object所在的bucket
	 * @param objectName
	 *            即上传的文件在bucket中的名字
	 * @param fileName
	 *            本地要上传的文件路径
	 * @param httpHeaders
	 *            http头属性
	 * @return 操作成功时返回PutObjectResult对象；否则返回null
	 * @throws IOException
	 *             InputStream关闭时可能抛出的IO异常
	 */
	public static PutObjectResult simplePutObject(final OSSClient client, final String bucketName,
			final String objectName, final String fileName, Map<String, Object> httpHeaders) throws IOException {

		PutObjectResult result = null;
		File file = new File(fileName);

		InputStream input = new FileInputStream(file);
		try {
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(file.length());

			if (httpHeaders != null) {
				Set<Entry<String, Object>> entrys = httpHeaders.entrySet();
				for (Entry<String, Object> entry : entrys) {
					objectMeta.setHeader(entry.getKey(), entry.getValue());
				}
			}

			result = client.putObject(bucketName, objectName, input, objectMeta);
		} finally {
			input.close();
		}
		return result;
	}

	/**
	 * 上传文件至OSS
	 * 
	 * @param client
	 *            OSSClient
	 * @param bucketName
	 *            指定object所在的bucket
	 * @param objectName
	 *            即上传的文件在bucket中的名字
	 * @param inputStream
	 *            要上传的输入流
	 * @param httpHeaders
	 *            http头属性
	 * @return 操作成功时，返回PutObjectResult;操作失败时返回null
	 * @throws IOException
	 *             InputStream关闭时可能抛出的IO异常
	 */
	public static PutObjectResult simplePutObject(final OSSClient client, final String bucketName,
			final String objectName, final InputStream inputStream, Map<String, Object> httpHeaders)
					throws IOException {

		PutObjectResult result = null;

		try {
			ObjectMetadata objectMeta = new ObjectMetadata();
			// maybe blocked by Internet
			objectMeta.setContentLength(inputStream.available());

			if (httpHeaders != null) {
				Set<Entry<String, Object>> entrys = httpHeaders.entrySet();
				for (Entry<String, Object> entry : entrys) {
					objectMeta.setHeader(entry.getKey(), entry.getValue());
				}
			}

			result = client.putObject(bucketName, objectName, inputStream, objectMeta);
		} finally {
			inputStream.close();
		}
		return result;
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
	public static List<String> listObject(final OSSClient client, final String bucketName, final String delimiter,
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

	/**
	 * 获取Object网络地址
	 * 
	 * @param client
	 *            OSSClient
	 * @param bucketName
	 *            指定object所在的bucket
	 * @param objectName
	 *            即上传的文件在bucket中的名字
	 * @return 获取OSS中的Object网络地址(URL地址)
	 */
	public static String getObjectAddress(final OSSClient client, final String bucketName, final String objectName) {
		return "http://" + bucketName + "." + client.getEndpoint().getHost() + "/" + objectName;
	}

	/**
	 * 获取Bucket网络地址
	 * 
	 * @param client
	 *            OSSClient
	 * @param bucketName
	 *            指定object所在的bucket
	 * @return 返回Bucket网络地址（URL地址）
	 */
	public static String getBucketAddress(final OSSClient client, final String bucketName) {
		return "http://" + bucketName + "." + client.getEndpoint().getHost() + "/";
	}

	/**
	 * 获取Object的内容
	 * 
	 * @param client
	 *            OSSClient
	 * @param bucketName
	 *            指定object所在的bucket
	 * @param objectName
	 *            即上传的文件在bucket中的名字
	 * @return object的内容
	 * @throws IOException
	 *             InputStream关闭时可能抛出的IO异常
	 */
	public static String getObjectWithStringContent(final OSSClient client, final String bucketName,
			final String objectName) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		OSSObject ossObject = client.getObject(bucketName, objectName);
		InputStream inputStream = ossObject.getObjectContent();

		try {
			int readBytes = 0;
			byte[] buffer = new byte[8192];
			while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
				stringBuilder.append(Charset.forName("utf-8").decode(ByteBuffer.wrap(buffer, 0, readBytes)));
			}
		} finally {
			inputStream.close();
		}

		return stringBuilder.toString();
	}

	/**
	 * 
	 * 获取Object的内容
	 * 
	 * @param client
	 *            OSSClient
	 * @param bucketName
	 *            指定object所在的bucket
	 * @param objectName
	 *            即上传的文件在bucket中的名字
	 * @return object输入流(记得在调用的程序里关闭该流)
	 */

	public static InputStream simpleGetObject(final OSSClient client, final String bucketName,
			final String objectName) {
		OSSObject ossObject = client.getObject(bucketName, objectName);
		if (ossObject == null) {
			return null;
		}

		return ossObject.getObjectContent();
	}

	/**
	 * 删除Object及Object下children对象
	 * 
	 * @param client
	 *            OSSClient
	 * @param bucketName
	 *            指定object所在的bucket
	 * @param objectName
	 *            即上传的文件在bucket中的名字
	 */
	public static void deleteObject(final OSSClient client, final String bucketName, final String objectName) {

		while (true) {
			List<String> children = listObject(client, bucketName, null, "", 1000, objectName + "/");
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
