package cn.com.dhcc.credit.approval.service;


/**
 * 查找或修改基础段信息
 * 
 * @author gsh
 * @date 2020年7月5日
 */
public interface QueryAndUpdateBasicService {

	Object queryBasicData(String className,String value,String methodName)throws Exception;
	
	void updateAuditstate(String fieldName,String fieldValue,String tableName,String auditState) throws Exception;
	
	void updateAuditstate(Object basicData) throws Exception;
	
}
