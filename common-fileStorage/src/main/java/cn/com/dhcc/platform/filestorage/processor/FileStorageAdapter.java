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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.dhcc.platform.filestorage.info.FileReadRequest;
import cn.com.dhcc.platform.filestorage.info.FileReadResponse;
import cn.com.dhcc.platform.filestorage.info.StorageRequest;
import cn.com.dhcc.platform.filestorage.info.StorageResponse;
import cn.com.dhcc.platform.filestorage.processor.FileStorageProcessor;
import cn.com.dhcc.platform.filestorage.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 文件存储适配器
 * @author Jerry.chen
 * @date 2019年2月25日
 */
@Slf4j
public class FileStorageAdapter {

	private static Map<String, FileStorageProcessor> fileStorageProcessors = new ConcurrentHashMap<String, FileStorageProcessor>();
	

	/**
	 * 注册处理器
	 * 
	 * @param processType
	 * @param porc
	 * @author Jerry.chen
	 * @date 2019年2月25日
	 */
	public static void registerProcessor(String processType, FileStorageProcessor porc) {
		fileStorageProcessors.put(processType, porc);
	}
	
	public static StorageResponse writeTask(StorageRequest request) {
		log.info("writeTask method begin,param requests = {}", request);
		StorageResponse response = null;
		String type = request.getStorageType();
		FileStorageProcessor fileStorageProcessor = fileStorageProcessors.get(type);
		if (null == fileStorageProcessor) {
			response = new StorageResponse();
			//20190909 TASK #411 查询流程增加流水号 start
			response.setSerialNumber(request.getSerialNumber());
			//20190909 TASK #411 查询流程增加流水号 end
			response.setErrorCode(FileStorageService.ERR_CODE_QCFQ11);
			response.setErrorMsg(FileStorageService.ERR_MSG_QCFQ11);
			log.error("writeTask method error,param requests = {},the fileStorageProcessor=null",request);
			return response;
		}
		response = fileStorageProcessor.saveFile(request);
		log.info("writeTask method begin,param requests = {},response = {}", request, response);
		return response;
	}

	public static List<StorageResponse> writeTask(List<StorageRequest> requests) {
		log.debug("writeTask(List<StorageRequest> requests) method begin,requests={}",requests);
		if(null != requests && requests.size()>0) {
			List<StorageResponse> resList = new ArrayList<StorageResponse>();
			for(StorageRequest request:requests){
				StorageResponse response = writeTask(request);
				resList.add(response);
			}
			return resList;
		}
		log.info("writeTask(List<StorageRequest> requests) method end,the requests = null");
		return null;
	}
	/**
	 * 
	 * @param request
	 * @return
	 * @author Jerry.chen
	 * @date 2019年3月11日
	 */
	public static FileReadResponse readTask(FileReadRequest request) {
		log.info("readTask method begin,param request = ", request);
		FileReadResponse response = new FileReadResponse();
		String storageType = request.getStorageType();
		FileStorageProcessor fileStorageProcessor = fileStorageProcessors.get(storageType);
		if (null == fileStorageProcessor) {
			response.setErrorCode(FileStorageService.ERR_CODE_QCFQ11);
			response.setErrorMsg(FileStorageService.ERR_MSG_QCFQ11);
			return response;
		}
		try {
			response = fileStorageProcessor.readFile(request);
		} catch (Exception e) {
			log.error("readTask method processing error! Request = {},The exception ={}", request, e);

		}
		
		return response;
	}

	public static FileReadResponse readTask(String url, String storageType) {
		log.info("readTask method begin,param url = {},storageType={}", url, storageType);
		FileReadResponse response = new FileReadResponse();
		FileStorageProcessor fileStorageProcessor = fileStorageProcessors.get(storageType);
		if (null == fileStorageProcessor) {
			response.setErrorCode(FileStorageService.ERR_CODE_QCFQ11);
			response.setErrorMsg(FileStorageService.ERR_MSG_QCFQ11);
			return response;
		}
		String fileContent;
		try {
			fileContent = fileStorageProcessor.readFile(url);
			response.setContent(fileContent);
		} catch (Exception e) {
			log.error("readTask method processing ,param url = {},storageType={},The URL error,can't read the file ! e={}", url, storageType,e);
		}
		
		return response;
	}

	public static List<FileReadResponse> readTask(List<String> url, String storageType) {
		return null;
	}

	public static boolean deleteTask(String url, String storageType) {
		log.info("deleteTask method begin,param url = {},storageType={}", url, storageType);
		boolean flag = false;
		FileStorageProcessor fileStorageProcessor = fileStorageProcessors.get(storageType);
		if (null == fileStorageProcessor) {
			log.error("deleteTask method process error,can't find the right FileStorageProcessor class!!! The storageType={}",storageType);
			return flag;
		}
		flag = fileStorageProcessor.deleteFile(url);
		log.info("deleteTask method end,param url = {},storageType={}, isDeleteSucess={}",flag);
		return flag;
	}

}
