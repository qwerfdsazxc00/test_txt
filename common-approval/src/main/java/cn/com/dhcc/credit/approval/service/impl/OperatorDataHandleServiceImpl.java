package cn.com.dhcc.credit.approval.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.dhcc.credit.approval.ann.OperationsHandle;
import cn.com.dhcc.credit.approval.bean.entity.ApprOperate;
import cn.com.dhcc.credit.approval.bean.entity.ApprTask;
import cn.com.dhcc.credit.approval.constants.Constants;
import cn.com.dhcc.credit.approval.dao.ApprOperateDao;
import cn.com.dhcc.credit.approval.dao.ApprRecordDao;
import cn.com.dhcc.credit.approval.dao.ApprTaskDao;
import cn.com.dhcc.credit.approval.service.OperatorDataHandleService;
import cn.com.dhcc.credit.platform.util.Reflections;
import cn.com.dhcc.credit.platform.util.SpringContextUtil;
import cn.com.dhcc.credit.platform.util.SysDate;
import cn.com.dhcc.platform.middleware.operationshistory.container.ActionLog;
import cn.com.dhcc.platform.middleware.operationshistory.container.HistoryLog;
import cn.com.dhcc.platform.middleware.operationshistory.container.TargetId;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
/**
 *  审批功能相关操作记录数据处理及入库操作实现类
 * 
 * @author gsh
 * @date 2020年7月29日
 */
public class OperatorDataHandleServiceImpl implements OperatorDataHandleService {
	
	public static final String BUSS_DATE = "bussDate";
	public static final String BUSSNUM = "bussNum";
	public static final String CUSTID = "custId";
	public static final String PBSB_TABLE_NAME = "PBSB_SPCEVTDSCINF";
	public static final String PBSB_USE_NAME = "特殊事件";
	@Autowired
	private ApprRecordDao recordDao;
	@Autowired
	private ApprOperateDao operatreDao;
	@Autowired
	private ApprTaskDao taskDao;
	
	@Override
	public void assemblingOperatorData(List<HistoryLog> historyLogs, ActionLog actionLog, String subjectName,
			String operateNameMark, String infoSegment, List<ApprOperate> appList, List<String> fieldList,
			String userName) throws ParseException {
		Object newObj = actionLog.getNewObj();
		Date bussDate = (Date) Reflections.getFieldValue(newObj, BUSS_DATE);
		bussDate = bussDateFormat(bussDate);

		for (HistoryLog history : historyLogs) {
			// 修改前的字段值
			String oldValueForConvert = history.getOldValueForConvert();
			// 修改后的字段值,为防止日期型字段转换报错,使用转换类,转换字段值为字符串型
			String newValueForConvert = history.getNewValueForConvert();
			if(StringUtils.isEmpty(oldValueForConvert) && StringUtils.isEmpty(newValueForConvert)){
				continue;
			}
			ApprOperate app = new ApprOperate();
			app.setOperator(userName);
			app.setOperatorMode(operateNameMark);
			// 操作字段名,入库存字段中文名
			String fieldName = history.getFieldName();
			String fieldNameCN = history.getFieldNameCN();
			app.setOperateField(fieldNameCN);
			// 存入字段集合,方便后面操作审批任务表时使用
			fieldList.add(fieldName);

			app.setFieldOldvalue(oldValueForConvert);

			app.setFieldNewvalue(newValueForConvert);
			// 操作时间
			app.setOperateDate(SysDate.getSysDate());

			// 当前记录的业务发生日期
			app.setBussDate(bussDate);
			// 业务类型
			app.setBussType(subjectName);
			// 操作的具体信息段
			app.setInfoSegment(infoSegment);

			appList.add(app);
		}

	}

	@Override
	public void assemblingOperatorDataForDelete(ActionLog actionLog, String subjectName, String operateNameMark,
			String infoSegment, List<ApprOperate> appList, String userName, List<TargetId> targetIds)
			throws ParseException {
		int size = targetIds.size();
		for (int i = 0; i < size; i++) {
			ApprOperate app = new ApprOperate();
			app.setOperator(userName);
			app.setOperatorMode(operateNameMark);
			// 操作时间
			app.setOperateDate(SysDate.getSysDate());
			// 业务类型
			app.setBussType(subjectName);
			// 操作的具体信息段
			app.setInfoSegment(infoSegment);
			// 保持格式统一,方便入库时统一处理
			appList.add(app);
		}
	}

	@Override
	public void assemblingOperatorDataForCreate(ActionLog actionLog, String subjectName, String operateNameMark,
			String infoSegment, List<ApprOperate> appList, String userName) throws ParseException {
		ApprOperate app = new ApprOperate();
		Object newObj = actionLog.getNewObj();
		// 获取当前操作记录的业务发生日期 
		Date bussDate = (Date) Reflections.getFieldValue(newObj, BUSS_DATE);
		bussDate = bussDateFormat(bussDate);
		app.setBussDate(bussDate);

		app.setOperator(userName);
		app.setOperatorMode(operateNameMark);
		// 操作时间
		app.setOperateDate(SysDate.getSysDate());
		// 业务类型
		app.setBussType(subjectName);
		// 操作的具体信息段
		app.setInfoSegment(infoSegment);
		// 保持格式统一,方便入库时统一处理
		appList.add(app);
	}

	@Override
	public void updateApprRecord(String taskId) {
		try {
			recordDao.updateFlagByTaskId(taskId);
		} catch (Exception e) {
			log.error("OperatorDataHandleServiceImpl---updateApprRecord---更新审批记录表有效标志时异常,taskId:{}",taskId);
			throw e;
		}
	}

	@Override
	public void saveOperateData(List<ApprOperate> appList, String taskId, String dataId) throws Exception {
		try {
			if (null != taskId) {
				for (ApprOperate operate : appList) {
					operate.setTaskId(taskId);
					operate.setDataId(dataId);
				}
			}
			operatreDao.save(appList);
		} catch (Exception e) {
			log.error("OperatorDataHandleServiceImpl---saveOperateData---taskId:{},dataId{},appList{},操作记录入库时异常",taskId,dataId,appList);
			throw e;
		}
	}
	
	
	@Override
	public Date bussDateFormat(Date bussDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (null != bussDate) {
			try {
				String formatDate = formatter.format(bussDate);
				bussDate = formatter.parse(formatDate);
			} catch (ParseException e) {
				log.error("OperatorDataHandleServiceImpl---bussDateFormat---bussDate:{},转换业务发生日期格式时异常", bussDate);
				throw e;
			}
		}
		return bussDate;
	}

	@Override
	public Object findSelfData(String value, Class<?> subjectClass) throws Exception {
		log.debug("OperatorDataHandleServiceImpl---findSelfData---根据主键查询当前数据,主键值为:{}",value);
		OperationsHandle handleAnn = subjectClass.getAnnotation(OperationsHandle.class);
		// 含有查找方法的类名
		String className = handleAnn.className();
		// 查找方法的方法名
		String methodName = handleAnn.methodName();

		// 获取含有查找方法的实体类,调用方法执行
		Object bean = SpringContextUtil.getBean(className);
		Class<?> clazz = bean.getClass();
		// 获取指定的查询方法
		Method method = clazz.getMethod(methodName, String.class);
		// 获取执行结果
		Object object = method.invoke(bean, value);
		return object;
	}

	@Override
	public void initApprovalTasK(String subjectName, String dataId, Object basicData, String taskId, String userName)
			throws Exception {
		ApprTask task = new ApprTask();

		task.setTaskId(taskId);
		// 审批时间
		task.setApproveDate(SysDate.getSysDate());
		// 审批员
		task.setApprover(userName);
		// 审批级别,初始化为1
		task.setApproverLevel(Constants.APPR_LEVEL_ONE);
		// 审批状态,初始化为待审批
		task.setApproveState(Constants.APPR_STATE_WAIT);
		// 业务类型
		task.setBussType(subjectName);
		// 个人/企业标志
		String bussCate = null;
		if (StringUtils.isNotBlank(subjectName)) {
			bussCate = subjectName.substring(0, 1);
			task.setBussCategory(bussCate);
		}
		// 获取字段名称
		String[] commonFields = getCommonFields(bussCate);
		// 业务编号
		Class<?> clazz = basicData.getClass();
		Field[] fields = clazz.getDeclaredFields();
		// 循环判断类中是否含有业务号,有业务号时注入业务号的值,无业务号时,说明主键为客户号,更新客户号字段的值
		boolean isBussNum = false;
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			if (BUSSNUM.equals(fieldName)) {
				String bussNum = (String) Reflections.getFieldValue(basicData, BUSSNUM);
				task.setBussNum(bussNum);
				isBussNum = true;
				break;
			}
		}

		if (!isBussNum) {
			String custId = (String) Reflections.getFieldValue(basicData, CUSTID);
			task.setCustId(custId);
		}
		// 基础段id
		task.setDataId(dataId);
		// 当前记录的所属机构
		String deptCode = (String) Reflections.getFieldValue(basicData, commonFields[3]);
		task.setDeptCode(deptCode);
		try {
			// 证件号码
			String idNum = (String) Reflections.getFieldValue(basicData, commonFields[0]);
			task.setIdNum(idNum);
			// 证件类型
			String idType = (String) Reflections.getFieldValue(basicData, commonFields[1]);
			task.setIdType(idType);
			// 姓名
			String name = (String) Reflections.getFieldValue(basicData, commonFields[2]);
			task.setOperator(name);
		} catch (Exception e) {
			if(PBSB_TABLE_NAME.equals(subjectName)){
				task.setOperator(PBSB_USE_NAME);
			}
		}
		// 入库时间
		task.setOperateDate(SysDate.getSysDate());

		// 保存任务表数据
		saveApprTask(task);
	}

	@Override
	public void updateApprTask(Object basicData, ApprTask oldTask, List<String> fieldList, boolean isBasic,
			String userName) {
		// 更新审批任务状态
		// 审批级别,初始化为1
		oldTask.setApproverLevel(Constants.APPR_LEVEL_ONE);
		// 更新操作员
		oldTask.setApprover(userName);
		// 更新操作时间
		oldTask.setApproveDate(SysDate.getSysDate());
		oldTask.setOperateDate(SysDate.getSysDate());
		// 如果通过标识变更的数据,再次修改需要更新任务表时,需要更新业务号字段
		if (fieldList.contains(BUSSNUM)) {
			// 修改的字段中包含业务号时,说明表内存在业务号字段
			String bussNum = (String) Reflections.getFieldValue(basicData, BUSSNUM);
			oldTask.setBussNum(bussNum);
		}

		if (fieldList.contains(CUSTID)) {
			// 修改的字段中包含业务号时,说明表内存在业务号字段
			String custId = (String) Reflections.getFieldValue(basicData, CUSTID);
			oldTask.setCustId(custId);
		}

		// 只有操作基础段时,需要判断是否更新三标与机构号
		if (isBasic) {
			updateThreeMarkAndDeptCode(basicData, fieldList, oldTask);
		}
		saveApprTask(oldTask);
	}
	
	/**
	 * 保存审批任务数据
	 * 
	 * @author gsh
	 * @date 2020年7月30日
	 * @return void
	 * 注：返回值为Boolean,写明true,false含义
	 */
	private void saveApprTask(ApprTask task){
		try {
			taskDao.save(task);
		} catch (Exception e) {
			log.error("OperatorDataHandleServiceImpl---saveApprTask---Task:{},保存审批任务表数据时异常",task);
			throw e;
		}
	}
	
	
	/**
	 * 获取需要从基础段中取值的字段
	 * 
	 * @param bussCate
	 * @return
	 * @author gsh
	 * @date 2020年7月7日
	 * @return String[] 注：返回值为Boolean,写明true,false含义
	 */
	private String[] getCommonFields(String bussCate) {
		String[] commonFields = null;
		if (Constants.ENT_FLAG.equals(bussCate)) {
			commonFields = Constants.ENT_FIELDS;
		} else {
			commonFields = Constants.PERSON_FIELDS;
		}
		return commonFields;
	}
	
	/**
	 * 更新审批任务表的三标与机构号
	 * 
	 * @param basicData
	 * @param fieldList
	 * @param oldTask
	 * @author gsh
	 * @date 2020年7月9日
	 * @return void 注：返回值为Boolean,写明true,false含义
	 */
	private void updateThreeMarkAndDeptCode(Object basicData, List<String> fieldList, ApprTask oldTask) {
		// 获取字段名称
		String[] commonFields = getCommonFields(oldTask.getBussCategory());
		// 所属机构
		if (fieldList.contains(commonFields[3])) {
			String deptCode = (String) Reflections.getFieldValue(basicData, commonFields[3]);
			oldTask.setDeptCode(deptCode);
		}
		// 证件号码
		if (fieldList.contains(commonFields[0])) {
			String idNum = (String) Reflections.getFieldValue(basicData, commonFields[0]);
			oldTask.setIdNum(idNum);
		}
		// 证件类型
		if (fieldList.contains(commonFields[1])) {
			String idType = (String) Reflections.getFieldValue(basicData, commonFields[1]);
			oldTask.setIdType(idType);
		}
		// 姓名
		if (fieldList.contains(commonFields[2])) {
			String name = (String) Reflections.getFieldValue(basicData, commonFields[2]);
			oldTask.setOperator(name);
		}

	}

	@Override
	public List<ApprTask> findTaskByDataIdAndState(String dataId, String auditState) {
		List<ApprTask> taskList =null;
		try {
			taskList = taskDao.findByDataIdAndState(dataId, Constants.APPR_STATE_WAIT);
		} catch (Exception e) {
			log.error("OperatorDataHandleServiceImpl---findTaskByDataIdAndState---dataId{},通过dataId查询审批任务表时异常",dataId);
			throw e;
		}
		return taskList;
	}

}
