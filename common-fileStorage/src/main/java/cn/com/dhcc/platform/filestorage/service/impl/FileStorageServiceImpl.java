/**
 *  Copyright (c)  2018-2028 DHCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of DHCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with DHCC.
 */
package cn.com.dhcc.platform.filestorage.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.com.dhcc.platform.filestorage.exception.FileIdAndUrlAtTheSameTimeException;
import cn.com.dhcc.platform.filestorage.info.FileReadRequest;
import cn.com.dhcc.platform.filestorage.info.FileReadResponse;
import cn.com.dhcc.platform.filestorage.info.StorageRequest;
import cn.com.dhcc.platform.filestorage.info.StorageResponse;
import cn.com.dhcc.platform.filestorage.processor.FileStorageAdapter;
import cn.com.dhcc.platform.filestorage.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

	@Override
	public StorageResponse saveFile(StorageRequest request) {
		if(null != request) {
			//未直接打印整个request对象，是因为文件内容过大，打印在log中查看不理想
			log.info("saveFile method begin ,param request ={}",request);
			StorageResponse res = FileStorageAdapter.writeTask(request);
			log.info("saveFile method end, param StorageRequest = {}, return = {}", request,res);
			return res;
		} else {
			log.error("saveFile error!!! the request = null");
			StorageResponse errorRes =new StorageResponse();
			errorRes.setErrorCode(ERR_CODE_QCFQ06);
			errorRes.setErrorMsg(ERR_CODE_QCFQ06);
			return errorRes;
		}
	}

	@Override
	public List<StorageResponse> saveFiles(List<StorageRequest> requests) {
		log.info("saveFiles method begin ,param requests = {}", requests);
		List<StorageResponse>  list =  FileStorageAdapter.writeTask(requests);
		log.info("saveFiles method end ,param requests = {},res={}", requests,list);
		return list;
	}

	@Override
	public FileReadResponse readFileContent(FileReadRequest readRequest) {
		log.info("readFileContent request begin, readRequest={}",readRequest);
		FileReadResponse readResponse = null;
		String fileId = readRequest.getFileId();
		String uri = readRequest.getUri();
		try {
			if (null != fileId) {
				//TODO 需要开发通过根据fileId获取url
			} else if (null != uri) {
				try {
					readResponse = FileStorageAdapter.readTask(readRequest);
				} catch (Exception e) {
					readResponse = FileReadResponse.getSuccessReadResponse();
					readResponse.setErrorCode(ERR_CODE_QCFQ01);
					readResponse.setErrorMsg(QCFQ01);
					log.error("readFileContent Failure,error: ",e);
				}
			} else {
				throw new FileIdAndUrlAtTheSameTimeException("FileId 与 URL 不能同时为空！");
			}
		} catch (FileIdAndUrlAtTheSameTimeException e) {
			log.error("FileId 与 URL 不能同时为空！error：",e);
		}
		String content = readResponse.getContent();
		String plaintext = null;
		if (readRequest.isDecrypt()) {
//			plaintext = EncryptAndDecryptAdapter.decrypt(content, readRequest.getEncryptAlgorithm());
			log.debug("readFileContent  decrypt plaintext:",plaintext);
		}
		if(readRequest.isZipFlag()){
//			plaintext = CommonsGZIPCompress.gunzip(plaintext);
			log.debug("readFileContent CommonsGZIPCompress plaintext:",plaintext);
		}
		if(StringUtils.isBlank(plaintext)) {
			plaintext = content;
		}
		readResponse.setContent(plaintext);
		log.info("readFileContent response end, readResponse={}",readResponse);
		return readResponse;
	}

	@Override
	public List<FileReadResponse> readFilesContent(List<FileReadRequest> readRequest) {
		//TODO
		return null;
	}

	@Override
	public boolean deleteFilesByURL(List<String> urls, String storageType) {
		//TODO
		return false;
	}

	@Override
	public List<StorageResponse> find(String bussId, String sourceSystem, String bussEN) {
		//TODO
		return null;
	}

	@Override
	public boolean deleteFiles(List<String> fileIds, String storageType) {
		//TODO
		return false;
	}

}
