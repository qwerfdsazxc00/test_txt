package cn.com.dhcc.credit.approval.service.impl;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import cn.com.dhcc.credit.approval.constants.Constants;
import cn.com.dhcc.credit.approval.service.QueryAndUpdateBasicService;
import cn.com.dhcc.credit.platform.util.Reflections;
import cn.com.dhcc.credit.platform.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueryAndUpdateBasicServiceImpl implements QueryAndUpdateBasicService {
	
	@Autowired(required = false)
	@Qualifier("entityManagerFactory")
	private LocalContainerEntityManagerFactoryBean ens;

	@Override
	public Object queryBasicData(String className,String value,String methodName) throws Exception {
		 //获取含有查找方法的实体类,调用方法执行
		Object bean = SpringContextUtil.getBean(className);
		Class<?> clazz = bean.getClass();
		
		Method method = clazz.getMethod(methodName, String.class);
		
		Object obj = method.invoke(bean, value);
		return obj;
	}

	@Override
	public void updateAuditstate(String fieldName,String fieldValue,String tableName,String auditState)throws Exception {
	
		log.info("QueryAndUpdateBasicServiceImpl---updateAuditstate---修改基础段审批状态开始");
		StringBuffer sql=new StringBuffer();
		sql.append(" update ").append(tableName).append(" set AUDITING_STATE='").append(auditState).append("' where ")
		.append("id ").append("='").append(fieldValue).append("'");
		EntityManager entityManager =ens.getNativeEntityManagerFactory().createEntityManager();
		Session s = (Session)entityManager.getDelegate();
		SessionFactoryImpl factory = (SessionFactoryImpl)s.getSessionFactory();
		Connection conn = null;
		try {
			conn=factory.getConnectionProvider().getConnection();
			conn.setAutoCommit(false);
			 PreparedStatement ps = conn.prepareStatement(sql.toString());
			 ps.execute();
			 conn.commit();
			
			log.info("QueryAndUpdateBasicServiceImpl---updateAuditstate---修改基础段审批状态结束");
		} catch (Exception e) {
			log.error("修改基础段审核状态时异常------------------error{}",e);
			throw e;
		}

	}
	
	
	@Override
	public void updateAuditstate(Object basicData)throws Exception {
	
		log.info("QueryAndUpdateBasicServiceImpl---updateAuditstate---修改基础段审批状态开始");
		EntityManager entityManager =ens.getNativeEntityManagerFactory().createEntityManager();
		Session s = (Session)entityManager.getDelegate();
		try {
			 Reflections.setFieldValue(basicData, "auditingState", Constants.APPR_STATE_WAIT);
			 s.saveOrUpdate(basicData);
//			 s.flush();
			log.info("QueryAndUpdateBasicServiceImpl---updateAuditstate---修改基础段审批状态结束");
		} catch (Exception e) {
			log.error("修改基础段审核状态时异常------------------error{}",e);
			throw e;
		}

	}
	

}
