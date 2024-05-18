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

/**
 * 
 * @author lhf
 * @date 2019年9月25日
 */
public class CommonKeyConstants {
	/**
	 * 征信中心 cookie key
	 */
	public static String COOKIE_KER = "credit.login.key";

	public static final String CRC_ACCOUNT_PASSWORD_LENGTH = "PBOC-passwordLength";

	/**
	 * mq 配置文件
	 */
	public static final String PROPERTIES_FILENAME_RABBIT = "amqp.properties";
	/**
	 * kafka配置文件
	 */
	public static final String PROPERTIES_FILENAME_KAFKA = "kafka.properties";

	public static final String ANGOUKA_PROPERTIES_NAME = "angouka.properties";

	public static final String SYS_VERSION_FILE = "build.properties";

	/**
	 * 配置
	 */
	public static final String ANGOUKA_X_KEY = "ANGOUKA.X";
	public static final String ANGOUKA_Y_KEY = "ANGOUKA.Y";
	public static final String ANGOUKA_RTAND_KEY = "ANGOUKA.RAND";

	/**
	 * 是否为微服务版本
	 */
	public static final String IS_MICRO_SERVICE_CONFIG_KEY = "isMicroService";

	public static final String IS_WEB_SERVICE_CONFIG_KEY = "isWebService";
	/**
	 * 授权电子文件pdf未保存预览生成临时目录保存时间
	 */
	public static final String PDFARCHIVES_TEMP_SAVETIME = "pdfArchive.savetime";
	/**
	 * 针对多库多应用增加的法人机构代码，为初始化缓存
	 */
	public static final String LEGAL_ORGCODE_KEY = "legal.orgCode";

	/**
	 * api异步查询时的定时任务间隔周期（单位：毫秒），默认为5分钟
	 */
	public static final String ASYN_QUERY_INTEVALPERIOD = "asynchronous.query.timer.intevalPeriod";

	/**
	 * 1-关闭 加密加压，0-开启 加密加压
	 */
	public static final String COMPRESS_ENCRYPT_FLAG_KEY = "compress.encrypt.flag";

	/**
	 * rabbit相关配置参数
	 */
	public static final String RABBIT_DEPLOY_MODE = "amqp.deployMode";
	public static final String RABBIT_HOST = "amqp.host";
	public static final String RABBIT_PORT = "amqp.port";
	public static final String RABBIT_ADDRESSES = "amqp.addresses";
	public static final String RABBIT_USERNAME = "amqp.username";
	public static final String RABBIT_PASSWORD = "amqp.password";
	public static final String RABBIT_DESFLAG = "amqp.desFlag";
	public static final String RABBIT_URI = "amqp.uri";
	public static final String RABBIT_VIRTUAL_HOST = "amqp.virtualHost";

	public static final String KAFKA_ACKS = "kafka.acks";
	public static final String KAFKA_BOOTSTRAP_SERVERS = "kafka.bootstrapServers";
	public static final String KAFKA_BATCH_SIZE = "kafka.batchSize";
	public static final String KAFKA_BUFFER_MEMORY_CONFIG = "kafka.bufferMemoryConfig";
	public static final String KAFKA_LINGERMS_CONFIG = "kafka.lingerMsConfig";
	public static final String KAFKA_RETRIES = "kafka.retries";

	public static final String CQS_COMMON_CONFIG = "cqs_common_cloud_config";
	public static final String CQS_ENT_CONFIG = "cqs_ent_cloud_config";
	public static final String CQS_PERSON_CONFIG = "cqs_person_cloud_config";
	
	/**
	 * 	单位ms，缓存有效时间。
	 */
	public static final String MIRCO_CONFIG_VALID_TIME ="valid.time";
	
	/**
	 * 是否为多法人
	 */
	public static final String MULTI_LEGAL_FLAG = "org.enableVirtualRoot";

}
