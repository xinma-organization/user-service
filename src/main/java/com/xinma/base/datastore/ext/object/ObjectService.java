package com.xinma.base.datastore.ext.object;

import java.io.InputStream;
import java.util.List;

import com.xinma.base.datastore.exceptions.ObjectException;

/**
 * 对象存储相关操作接口定义，主要用于image,js,css,html,json等资源的存储
 * 
 * @author Alauda
 *
 * @date Aug 1, 2016
 * 
 */
public interface ObjectService {

	/**
	 * 创建一个文件夹
	 * 
	 * @param folderName
	 *            文件夹全路径，可以创建多级目录,folderName必须以"/"为结尾，例如folder/a/
	 * @throws ObjectException
	 *             操作失败，抛出异常
	 */
	void mkdir(String folderName) throws ObjectException;

	/**
	 * 列出目录下所有文件名
	 * 
	 * @param folderName
	 *            目录名
	 * @return
	 * @throws ObjectException
	 *             操作失败，抛出异常
	 */
	List<String> listObjects(String folderName) throws ObjectException;

	/**
	 * 本地文件上传至对象服务器
	 * 
	 * @param objectName
	 *            对象被上传至远程服务的全路径名
	 * @param localFileName
	 *            本地要上传文件名称
	 * @throws ObjectException
	 *             操作失败，抛出异常
	 */
	void uploadObject(final String objectName, final String localFileName) throws ObjectException;

	/**
	 * 本地文件上传至对象服务器
	 * 
	 * @param objectName
	 *            对象被上传至远程服务的全路径名
	 * @param inputStream
	 *            要上传的输入流
	 * @throws ObjectException
	 *             操作失败，抛出异常
	 */
	void uploadObject(final String objectName, final InputStream inputStream) throws ObjectException;

	/**
	 * 删除指定Object及Object下children对象
	 * 
	 * @param objectName
	 *            对象名
	 * @throws ObjectException
	 *             操作失败，抛出异常
	 */
	void deleteObject(final String objectName) throws ObjectException;
}
