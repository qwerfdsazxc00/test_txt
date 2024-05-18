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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * 文件读取请求信息类
 * @author Jerry.chen
 * @date 2019年2月25日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileReadRequest {

	 /**
     * 流水号
     */
    private String serialNumber;
    
    /**
     * 预留的扩展参数字段
     */
    private Map<String,Object> extendParams;
	/**
	 * 文件被分配的唯一标识，该参数和uri 参数只传入一个即可，如果两个都传入，以URI为准 。
	 */
	private String fileId;
	/**
	 * 需要读取的完整路径（包含文件名），该参数和uri 参数只传入一个即可，如果两个都传入，以URI为准 。
	 */
	private String uri;
	/**
	 * 返回的类型 ：
	 *          "0" 代表 byte[];
	 *          "1" 代表String;
	 */
	private String resultType = "1";
	/**
	 * 存储方式类型
	 * 0- 本地磁盘/NAS 共享存储
	 * 1- FTP  共享存储
	 * 2- 云盘   共享存储
	 */
	private String storageType;
	/**
	 * 是否解密
	 */
	private boolean decrypt = true;

	/**
	 * 解密方式
	 */
	private String  encryptAlgorithm = "SM4";

	/**
	 * 是否解压
	 */
	private boolean zipFlag = true;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileReadRequest [serialNumber=" + serialNumber + ", extendParams=" + extendParams + ", fileId=" + fileId
				+ ", uri=" + uri + ", resultType=" + resultType + ", storageType=" + storageType + ", decrypt="
				+ decrypt + ", encryptAlgorithm=" + encryptAlgorithm + ", zipFlag=" + zipFlag + "]";
	}  
}
