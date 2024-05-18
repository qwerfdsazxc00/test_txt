/**
 *  Copyright (c)  2018-2028 DHCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of DHCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with DHCC.
 */
package cn.com.dhcc.platform.filestorage.service;

import java.util.List;

import cn.com.dhcc.platform.filestorage.info.FileReadRequest;
import cn.com.dhcc.platform.filestorage.info.FileReadResponse;
import cn.com.dhcc.platform.filestorage.info.StorageRequest;
import cn.com.dhcc.platform.filestorage.info.StorageResponse;

/**
 * 文件存储服务
 * 
 * @author Tianyu.Li
 * @date 2019.02.24
 */
public interface FileStorageService {

	/**
	 * 存储单一文件方法
	 * 
	 * @param storageRequest {@link StorageRequest }
	 * @return StorageResponse
	 * @author Tianyu.Li
	 * @date 2019.02.24
	 */
	StorageResponse saveFile(StorageRequest storageRequest);

	/**
	 * 批量存储文件方法
	 * 
	 * @param storageRequestList {@link List<StorageRequest> }
	 * @return StorageResponse
	 * @author Tianyu.Li
	 * @date 2019.02.24
	 */
	List<StorageResponse> saveFiles(List<StorageRequest> storageRequestList);

	/**
	 * 读取文件方法
	 * 
	 * @param fileId
	 * @param storageType
	 * @return 字符串文件
	 */
	FileReadResponse readFileContent(FileReadRequest readRequest);

	/**
	 * 批量读取文件方法
	 * 
	 * @param fileIds
	 * @param storageType
	 * @return map key-fileId value-与fileId匹配的文件内容字符串
	 */
	List<FileReadResponse> readFilesContent(List<FileReadRequest> readRequest);

	/**
	 * 删除文件方法
	 * 
	 * @param fileIds
	 * @param storageType
	 */
	boolean deleteFiles(List<String> fileIds, String storageType);

	/**
	 * 根据文件URI删除文件方法
	 * 
	 * @param fileIds
	 * @param storageType
	 */
	boolean deleteFilesByURL(List<String> uris, String storageType);

	List<StorageResponse> find(String bussId, String sourceSystem, String bussEN);
	public final static String ERR_CODE_QCFQ01 = "QCFQ01";
	final static String QCFQ01 = "文件不存在！文件的唯一标识为[{}],文件的全路径为[{}]。异常信息={}";
	final static String QCFQ02 = "流关闭异常！异常信息={}";
	final static String QCFQ03 = "读取文件异常！文件的全路径为[{}]。异常信息={}";
	
	final static String ERR_CODE_QCFQ04 = "QCFQ04";
	final static String ERR_MSG_QCFQ04 = "保存文件异常! 文件的全路径为[{}]。异常信息={}";
	final static String QCFQ05 = "删除文件异常! 文件的全路径为[{}]。异常信息={}";
	final static String ERR_CODE_QCFQ06 = "QCFQ06";
	final static String ERR_MSG_QCFQ06 = "请求文件内容为空";
	
	final static String ERR_CODE_QCFQ07 = "QCFQ07";
	/**
	 * 文件路径参数为空
	 */
	final static String ERR_MSG_QCFQ07 = "文件路径参数为空";
	
	final static String ERR_CODE_QCFQ08 = "QCFQ08";
	final static String ERR_MSG_QCFQ08 = "文件读取请求中的返回类型错误，非String 和Byte[] 这两种。";
	final static String QCFQ09 = "";
	final static String QCFQ10 = "";
	final static String ERR_CODE_QCFQ11 = "QCFQ11";
	final static String ERR_MSG_QCFQ11 = "存储方式类型为空!";
	final static String ERR_CODE_QCFQ12 = "QCFQ12";
	final static String ERR_MSG_QCFQ12 = "读取文件时，该文件尚未写入，请稍后重试!";
	final static String QCFQ12 = "";
	final static String QCFQ13 = "";
	final static String QCFQ14 = "";
	final static String QCFQ15 = "";
	final static String QCFQ16 = "";
	final static String QCFQ17 = "";
	final static String QCFQ18 = "";
	final static String QCFQ19 = "";
	final static String QCFQ20 = "";
}
