/**
 *  Copyright (c)  2018-2028 DHCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of DHCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with DHCC.
 */
package cn.com.dhcc.platform.filestorage.exception;

public class FileIdAndUrlAtTheSameTimeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5743682187346566571L;
	
	public FileIdAndUrlAtTheSameTimeException(String msg) {
		super(msg);
	}

}
