/**
 *  Copyright (c)  2018-2028 DHCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of DHCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with DHCC.
 */
package cn.com.dhcc.platform.util;

import lombok.Data;

/**
 * 
 * @author lhf
 * @date 2019年9月25日
 */
@Data
public class CommonKeyConfig {
	private String angoukaX;
	private String angoukaY;
	private String angoukaRandom;
	private String crcAccountPwdLength;
	private String crcLoginCookie;
	private String isMicroService;
	private String isWebService;
	private String compressEncryptFlag;
	private long lastAccessTime;
	/**
	 * 是否启用多法人
	 */
	private String multiLegalFlag;

}
