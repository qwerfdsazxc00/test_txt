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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import cn.com.dhcc.credit.platform.util.RedissonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author lhf
 * @date 2019年9月25日
 */
@Slf4j
public class CommonProperties {
	private static CommonKeyConfig commonKeyConfig = new CommonKeyConfig();
	
	private static RedissonClient redis = RedissonUtil.getLocalRedisson();
	private static volatile Boolean flag = false;
	private static String isMicroService = KeyProperties.getProperty(CommonKeyConstants.IS_MICRO_SERVICE_CONFIG_KEY);
	private static String isWebService = KeyProperties.getProperty(CommonKeyConstants.IS_WEB_SERVICE_CONFIG_KEY);
	private static String validTime = KeyProperties.getProperty(CommonKeyConstants.MIRCO_CONFIG_VALID_TIME);
	private static Map<String, Boolean> map = new ConcurrentHashMap<String, Boolean>();
	private static String SYSTEM_TYPE = "system_type";

	public static boolean isMicroService() {
		if (!map.isEmpty()) {
			return map.get(SYSTEM_TYPE);
		}
		boolean isMicroServiceFlag = Boolean.parseBoolean(isMicroService);
		boolean isWebServiceFlag = Boolean.parseBoolean(isWebService);
		if (isMicroServiceFlag && !isWebServiceFlag) {
			map.put(SYSTEM_TYPE, true);
			return true;
		} else {
			map.put(SYSTEM_TYPE, false);
			return false;
		}
	}

	

	private static RMap<String, String> getCommonMapFromRedis() {
		RMap<String, String> map = redis.getMap(CommonKeyConstants.CQS_COMMON_CONFIG);
		if (map.isEmpty()) {
			try {
				Thread.sleep(1);
				map = redis.getMap(CommonKeyConstants.CQS_COMMON_CONFIG);
			} catch (InterruptedException e) {
				log.error("getCommonMapFromRedis error,e = {}", e);
				// 还原中断状态
				Thread.currentThread().interrupt();
			}
		}
		return map;
	}

	public static CommonKeyConfig getCommonConfig() {
		if (CommonProperties.isMicroService()) {
			if(flag){
				isMicroConfigValid();
			}
			if (!flag) {
				getMicroCommonProperties();
			}
		} else {
			if (!flag) {
				getDistCommonProperties();
			}
		}
		return commonKeyConfig;
	}

	private static void isMicroConfigValid() {
		long timeInterval = System.currentTimeMillis()-commonKeyConfig.getLastAccessTime();
		long validTimeL = Long.parseLong(validTime);
		if(timeInterval > validTimeL) {
			flag = false;
		}
	}

	/**
	 * 
	 * @author lhf
	 * @date 2019年10月13日
	 */
	private static void getMicroCommonProperties() {
		log.info("getMicroCommonProperties start ...");
		RMap<String, String> commonMapFromRedis = getCommonMapFromRedis();
		commonKeyConfig.setAngoukaRandom(getPropertiesFromMap(commonMapFromRedis, CommonKeyConstants.ANGOUKA_RTAND_KEY));
		commonKeyConfig.setAngoukaX(getPropertiesFromMap(commonMapFromRedis,CommonKeyConstants.ANGOUKA_X_KEY));
		commonKeyConfig.setAngoukaY(getPropertiesFromMap(commonMapFromRedis, CommonKeyConstants.ANGOUKA_Y_KEY));
		commonKeyConfig.setCompressEncryptFlag(getPropertiesFromMap(commonMapFromRedis, CommonKeyConstants.COMPRESS_ENCRYPT_FLAG_KEY));
		commonKeyConfig.setIsMicroService(getPropertiesFromMap(commonMapFromRedis,CommonKeyConstants.IS_MICRO_SERVICE_CONFIG_KEY));
		commonKeyConfig.setIsWebService(getPropertiesFromMap(commonMapFromRedis,CommonKeyConstants.IS_WEB_SERVICE_CONFIG_KEY));
		commonKeyConfig.setCrcAccountPwdLength(getPropertiesFromMap(commonMapFromRedis,CommonKeyConstants.CRC_ACCOUNT_PASSWORD_LENGTH));
		commonKeyConfig.setCrcLoginCookie(getPropertiesFromMap(commonMapFromRedis,CommonKeyConstants.COOKIE_KER));
		commonKeyConfig.setLastAccessTime(System.currentTimeMillis());
		commonKeyConfig.setMultiLegalFlag(getPropertiesFromMap(commonMapFromRedis,CommonKeyConstants.MULTI_LEGAL_FLAG));
		flag = true;
		log.info("getMicroCommonProperties end ...");
	}

	private static void getDistCommonProperties() {
		log.info("getDistCommonProperties start ...");
		String cookie = KeyProperties.getProperty(CommonKeyConstants.COOKIE_KER);
		String passwordLengh = KeyProperties.getProperty(CommonKeyConstants.CRC_ACCOUNT_PASSWORD_LENGTH);
		String random = KeyProperties.getProperty(CommonKeyConstants.ANGOUKA_RTAND_KEY,
				CommonKeyConstants.ANGOUKA_PROPERTIES_NAME);
		String angoukaX = KeyProperties.getProperty(CommonKeyConstants.ANGOUKA_X_KEY,
				CommonKeyConstants.ANGOUKA_PROPERTIES_NAME);
		String angoukaY = KeyProperties.getProperty(CommonKeyConstants.ANGOUKA_Y_KEY,
				CommonKeyConstants.ANGOUKA_PROPERTIES_NAME);
		commonKeyConfig.setAngoukaRandom(random);
		commonKeyConfig.setAngoukaX(angoukaX);
		commonKeyConfig.setAngoukaY(angoukaY);
		commonKeyConfig.setCrcAccountPwdLength(passwordLengh);
		commonKeyConfig.setCrcLoginCookie(cookie);
		commonKeyConfig.setIsMicroService(KeyProperties.getProperty(CommonKeyConstants.IS_MICRO_SERVICE_CONFIG_KEY));
		commonKeyConfig.setIsWebService(KeyProperties.getProperty(CommonKeyConstants.IS_WEB_SERVICE_CONFIG_KEY));
		commonKeyConfig.setCompressEncryptFlag(KeyProperties.getProperty(CommonKeyConstants.COMPRESS_ENCRYPT_FLAG_KEY));
		commonKeyConfig.setMultiLegalFlag(KeyProperties.getProperty(CommonKeyConstants.MULTI_LEGAL_FLAG));
		flag = true;
		log.info("getDistCommonProperties end ...");
	}

	private static String getPropertiesFromMap(RMap<String, String> map, String key) {
		String value = map.get(key);
		if (StringUtils.isBlank(value)) {
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				value = map.get(key);
			}
		}
		return value;
	}
}
