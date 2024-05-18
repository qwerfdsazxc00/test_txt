/**
 *  Copyright (c)  2018-2028 DHCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of DHCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with DHCC.
 */
package cn.com.dhcc.platform.filestorage.info;

import java.util.Arrays;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 文件读取响应类
 * 
 * @author Jerry.chen
 * @date 2019年2月25日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileReadResponse {
	 /**
     * 流水号
     */
    private String serialNumber;
    
    /**
     * 预留的扩展参数字段
     */
    private Map<String,Object> extendParams;
	/**
	 * 文件内容
	 */
	private String content;
	/**
	 * 文件内容
	 */
	private byte[] contentBytes;
	/**
	 * 文件内容的MD5 摘要值
	 */
	private String md5;
	/**
	 * 返回处理码
	 */
	private String errorCode;
	/**
	 * 返回错误消息
	 */
	private String errorMsg;
	/**
	 * 读取文件成功默认返回码
	 */
	private final static String SUCESS_CODE="000000";
	/**
	 * 读取文件成功默认返回消息
	 */
	private final static String SUCESS_MSG = "文件读取成功";
	
	final static String ERROR_CODE="P";
	
	/**
	 * 默认返回成功响应对象
	 * @return
	 * @author Jerry.chen
	 * @date 2019年2月27日
	 */
	public static FileReadResponse getSuccessReadResponse(){
		FileReadResponse response =  new FileReadResponse();
		response.setErrorCode(SUCESS_CODE);
		response.setErrorMsg(SUCESS_MSG);;
		return response;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileReadResponse [serialNumber=" + serialNumber + ", extendParams=" + extendParams + ", content="
				+ (null == content?null:content.length()) + ", contentBytes's length =" + (null == contentBytes?0:contentBytes.length) + ", md5=" + md5 + ", errorCode="
				+ errorCode + ", errorMsg=" + errorMsg + "]";
	}
}
