/**
 *  Copyright (c)  2018-2028 DHCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of DHCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with DHCC.
 */
package cn.com.dhcc.platform.filestorage.processor;

import cn.com.dhcc.platform.filestorage.info.FileReadRequest;
import cn.com.dhcc.platform.filestorage.info.FileReadResponse;
import cn.com.dhcc.platform.filestorage.info.StorageRequest;
import cn.com.dhcc.platform.filestorage.info.StorageResponse;

/**
 * 文件存储处理器
 * 每个子实现类都需要在无参构造方法时，调用一下register 方法
 *  具体例子可参照NasStorageProcessor 
 * @author Tianyu.Li
 * @date   2019.02.24
 */
public abstract class FileStorageProcessor {

	/**
	 * 存储介质类型 -- NAS /LOCAL DISK 对应的值为 ： "0"
	 */
	public static final String STORAGE_MEDIUM_TYPE_NAS = "0";
	
	/**
	 * 存储介质类型 -- FTP  对应的值为 ："1"
	 */
	public static final String STORAGE_MEDIUM_TYPE_FTP = "1";
	
	/**
	 * 存储介质类型 -- CLOUND 对应的值为 ："2"
	 */
	public static final String STORAGE_MEDIUM_TYPE_CLOUND = "2";
	/**
	 * 文件保存类型 1-String
	 */
	public static final String FILE_RETURN_TYPE_STRING = "1";
	/**
	 * 文件保存类型 0-byte[]
	 */
	public static final String FILE_RETURN_TYPE_BYTES = "0";
	/**
	 * 1-关闭  加密加压，0-开启 加密加压
	 */
	public static final String COMPRESS_ENCRYPT_FLAG_KEY = "compress.encrypt.flag";
	/**
	 * 1-关闭  加密加压，0-开启 加密加压
	 */
	public static final String COMPRESS_ENCRYPT_FLAG_CLOSE = "1";
	 
	public void register(){
		FileStorageAdapter.registerProcessor(this.getType(),this);
	}

	/**
	 * 保存文件
	 * @param request
	 * @return 
	 */
	public abstract StorageResponse saveFile(StorageRequest request);
	
	/**
	 * 获取存储介质类型
	 * @return 存储介质类型:目前暂时支持  NAS |FTP |CLOUND ;
	 * 如果业务方如果需要扩展，只需在子类中自行定义
	 * @author Jerry.chen
	 * @date 2019年2月25日
	 */
	public abstract String getType();
	
	/**
	 * 读取指定的字符集和路径，按操作文本文件的方式读取文件内容
	 * @param url 文件的完整路径
	 * @param charset 字符集
	 * @return 文件内容
	 * @author Jerry.chen
	 * @date 2019年2月25日
	 */
	
	public abstract String readFile(String url,String charset) throws Exception;
	
	/**
	 * 读取路径，按默认UTF-8字符集,操作文本文件的方式读取文件内容
	 * @param url
	 * @param storage
	 * @return 
	 * @author Jerry.chen
	 * @date 2019年2月25日
	 */
	public abstract String readFile(String url) throws Exception;
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author Jerry.chen
	 * @date 2019年3月9日
	 */
	public abstract FileReadResponse readFile(FileReadRequest request) throws Exception;
	
	
	/**
	 * 读取路径，按默认UTF-8字符集,操作文本文件的方式读取文件内容
	 * @param uri 文件的完整路径。
	 * @return  byte[] 
	 * @author Jerry.chen
	 * @date 2019年2月25日
	 */
	public abstract byte[] readFileBytes(String uri) throws Exception;
	/**
	 * 
	 * @param url
	 * @param storage
	 * @return
	 * @author Jerry.chen
	 * @date 2019年2月25日
	 */
	public abstract boolean deleteFile(String url);
}
