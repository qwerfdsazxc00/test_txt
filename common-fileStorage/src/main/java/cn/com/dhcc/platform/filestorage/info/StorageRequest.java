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

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件存储请求类
 * 
 * @author Jerry.chen
 * @date 2019年2月28日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2204196947027443283L;

	/**
	 * 流水号
	 */
	private String serialNumber;

	/**
	 * 预留的扩展参数字段
	 */
	private Map<String, Object> extendParams;

	/**
	 * 需要存储的信息
	 */
	private String content;
	/**
	 * 需要存储的文件字节数组。
	 * 该字段为文件流方式存储使用，如果直接是需要存储文件，建议使用该字段。
	 */
	private byte[] contentBytes;
	
	/**
	 * 字符集: UTF-8   GBK  默认为UTF-8
	 */
	private String charset= "UTF-8";

	/**
	 * 是否加密: 默认为 true
	 */
	private boolean encrypt = true;

	/**
	 * 加解密方式，默认 国密算法 SM4
	 */
	private String encryptAlgorithm = "SM4";

	/**
	 * 是否加压,默认加压
	 */
	private boolean compression = true;

	/**
	 * 需要存储的文件类型，为文件的后缀名。
	 * 比如：    HTML/XML/PDF/JPG
	 */
	private String fileType;
	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 存储文件的根路径
	 * 必填--业务调用方需要获取系统的配置，传入该参数值
	 * Linux 默认为：/fileData
	 * Windows 例如：D:/fileData  ;
	 * 注意：该参数如果不传值，会直接报错。 
	 */
	private String rootUri = "/fileData";

	/**
	 * 文件所属业务功能模块的英文名称 
	 * 查询业务线业务功能模块： 
	 * 下游接口服务 qa ;
	 * 授权管理服务 am ;
	 * 统计服务 qs ;
	 * 配置服务 cg ;
	 * 业务监控服务 bm ;
	 * 审批服务 af ;
	 * 查询控制服务 cl ;
	 * 征信报告展示服务 rv ;
	 * 查询流程管理服务 fm ;
	 * 人行转发组件 qp ;
	 * 征信报告存储组件 rs ;
	 * 计数器组件
	 * qc ;征信用户路由组件 ur ;未知默认： XX
	 */
	private String bussModelEN = "XX";

	/**
	 * 系统来源: 
	 * QP 个人查询; 
	 * QE 企业查询 ;
	 * RP 个人报数 ;
	 * RE 企业报数 ;
	 * P 平台;
	 * BP 个人百行 ;
	 * BE 企业百行 ;
	 * SS 未知系统--默认 必填;
	 */
	private String sourceSystem;

	/**
	 * 存储方式类型  0- 本地磁盘/NAS 共享存储; 1- FTP 共享存储; 2- 云盘 共享存储
	 */
	private String storageType = "0";

	/**
	 * 源业务系统所属业务功能模块的业务主键，只用于日志记录，以及后期排查
	 */
	private String bussId;

	/**
	 * 完整的URL
	 */
	private String url;

	public static void validate(StorageRequest request) {
		org.springframework.util.Assert.notNull(request, "request-文件存储请求对象不能不为空");
		org.springframework.util.Assert.notNull(request.getFileType(), "fileType-文件类型字段不能为空");
		org.springframework.util.Assert.notNull(request.getSourceSystem(), "sourceSystem-系统来源字段不能为空");
	}

	@Override
	public String toString() { 
		return "StorageRequest [serialNumber=" + serialNumber + ", extendParams=" + extendParams + ", content's length ="
				+ (content==null?null:content.length()) + ", contentBytes's length =" + (contentBytes == null?null:contentBytes.length) + ", charset=" + charset + ", encrypt="
				+ encrypt + ", encryptAlgorithm=" + encryptAlgorithm + ", compression=" + compression + ", fileType="
				+ fileType + ", rootUri=" + rootUri + ", bussModelEN=" + bussModelEN + ", sourceSystem=" + sourceSystem
				+ ", storageType=" + storageType + ", bussId=" + bussId + ", url=" + url + "]";  
	}

	
}
