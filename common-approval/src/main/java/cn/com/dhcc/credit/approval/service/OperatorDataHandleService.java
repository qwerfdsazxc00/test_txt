package cn.com.dhcc.credit.approval.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import cn.com.dhcc.credit.approval.bean.entity.ApprOperate;
import cn.com.dhcc.credit.approval.bean.entity.ApprTask;
import cn.com.dhcc.platform.middleware.operationshistory.container.ActionLog;
import cn.com.dhcc.platform.middleware.operationshistory.container.HistoryLog;
import cn.com.dhcc.platform.middleware.operationshistory.container.TargetId;
/**
 * 审批功能相关操作记录数据处理及入库操作
 * 
 * @author gsh
 * @date 2020年7月29日
 */
public interface OperatorDataHandleService {
	/**
	 * 修改时拼装操作记录,需要详细的操作字段及修改前后的值
	 * @param historyLogs
	 * @param actionLog
	 * @param subjectName
	 * @param operateNameMark
	 * @param infoSegment
	 * @param appList
	 * @param fieldList
	 * @param userName
	 * @throws ParseException
	 * @author gsh
	 * @date 2020年7月29日
	 * @return void
	 * 注：返回值为Boolean,写明true,false含义
	 */
	void assemblingOperatorData(List<HistoryLog> historyLogs, ActionLog actionLog, String subjectName,
			String operateNameMark, String infoSegment, List<ApprOperate> appList, List<String> fieldList,
			String userName) throws ParseException;
	/**
	 * 删除操作时,拼装操作记录
	 * @param actionLog
	 * @param subjectName
	 * @param operateNameMark
	 * @param infoSegment
	 * @param appList
	 * @param userName
	 * @param targetIds
	 * @throws ParseException
	 * @author gsh
	 * @date 2020年7月29日
	 * @return void
	 * 注：返回值为Boolean,写明true,false含义
	 */
	void assemblingOperatorDataForDelete(ActionLog actionLog, String subjectName, String operateNameMark,
			String infoSegment, List<ApprOperate> appList, String userName, List<TargetId> targetIds)
			throws ParseException;
	
	/**
	 *  新增操作时,拼装操作记录数据
	 * @param actionLog
	 * @param subjectName
	 * @param operateNameMark
	 * @param infoSegment
	 * @param appList
	 * @param userName
	 * @throws ParseException
	 * @author gsh
	 * @date 2020年7月29日
	 * @return void
	 * 注：返回值为Boolean,写明true,false含义
	 */
	 void assemblingOperatorDataForCreate(ActionLog actionLog, String subjectName, String operateNameMark,
				String infoSegment, List<ApprOperate> appList, String userName) throws ParseException;
	
	 /**
	  * 根据taskId将审批记录表逻辑删除
	  * @param taskId
	  * @author gsh
	  * @date 2020年7月29日
	  * @return void
	  * 注：返回值为Boolean,写明true,false含义
	  */
	 void updateApprRecord(String taskId);
	 
	 /**
	  * 操作记录入库
	  * @param appList
	  * @param taskId
	  * @param dataId
	  * @author gsh
	  * @date 2020年7月29日
	  * @return void
	  * 注：返回值为Boolean,写明true,false含义
	 * @throws Exception 
	  */
	 void saveOperateData(List<ApprOperate> appList, String taskId, String dataId) throws Exception;
	 /**
	  * 根据id查询本条数据
	  * @param value
	  * @param subjectClass
	  * @return
	  * @throws Exception
	  * @author gsh
	  * @date 2020年7月29日
	  * @return Object
	  * 注：返回值为Boolean,写明true,false含义
	  */
	 Object findSelfData(String value, Class<?> subjectClass) throws Exception;
	 /**
	  * 审批任务表入库
	  * @param subjectName
	  * @param dataId
	  * @param basicData
	  * @param taskId
	  * @param userName
	  * @throws Exception
	  * @author gsh
	  * @date 2020年7月29日
	  * @return void
	  * 注：返回值为Boolean,写明true,false含义
	  */
	 void initApprovalTasK(String subjectName, String dataId, Object basicData, String taskId, String userName)
				throws Exception;
	 /**
	  * 当前记录正在审批中,有新的操作记录需要增加时,更新任务表,逻辑删除审批记录表
	  * @param basicData
	  * @param oldTask
	  * @param fieldList
	  * @param isBasic
	  * @param userName
	  * @author gsh
	  * @date 2020年7月29日
	  * @return void
	  * 注：返回值为Boolean,写明true,false含义
	  */
	 void updateApprTask(Object basicData, ApprTask oldTask, List<String> fieldList, boolean isBasic,
				String userName);
	 
	 /**
	  * 根据基础段id与审核状态查询审批任务
	  * @param dataId
	  * @param auditState
	  * @return
	  * @author gsh
	  * @date 2020年7月29日
	  * @return List<ApprTask>
	  * 注：返回值为Boolean,写明true,false含义
	  */
	 List<ApprTask> findTaskByDataIdAndState(String dataId,String auditState);
	 /**
	  * 转换业务发生日期
	  * @param bussDate
	  * @return
	  * @throws ParseException
	  * @author gsh
	  * @date 2020年7月29日
	  * @return Date
	  * 注：返回值为Boolean,写明true,false含义
	  */
	 Date bussDateFormat(Date bussDate) throws ParseException;
	
}
