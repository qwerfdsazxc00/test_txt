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

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * 
 * @author Jerry.chen
 * @date 2019年2月25日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageResponse {
	 /**
     * 流水号
     */
    private String serialNumber;
    
    /**
     * 预留的扩展参数字段
     */
    private Map<String,Object> extendParams;
	
	/**
	 * 文件被分配的唯一标识
	 */
	private String fileId;
	
	/**
	 * 存储文件后的文件全路径
	 */
	private String uri;
	
	/**
	 * 存储文件后的文件名称
	 */
	private String fileName;
	
	/**
	 * 存储方式类型
	 */
	private String storageType;
	
	/**
	 * 错误编码
	 */
	private String errorCode;
	
	/**
	 * 错误信息
	 */
	private String errorMsg;
	
	private final static String SUCESS_CODE="000000";
	private final static String SUCESS_MSG = "文件存储成功";
	
	public static StorageResponse sucessStorageResponse(){
		StorageResponse res = new StorageResponse();
		res.setErrorCode(SUCESS_CODE);
		res.setErrorMsg(SUCESS_MSG);
		return res;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}
