package cn.com.dhcc.credit.approval.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import cn.com.dhcc.credit.approval.ann.OperationsHandle;
import cn.com.dhcc.credit.platform.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 查询库中修改之前的原数据
 * 
 * @author gsh
 * @date 2020年7月3日
 */
@Component
@Slf4j
public class LastTimeDataQueryHandle {

	public Object findLastTimeData(Object o) throws Exception{
		log.info("findOldData---------------start");
		Class<?> class1 = o.getClass();
		//获取操作对象上注解
		OperationsHandle annotation = class1.getAnnotation(OperationsHandle.class);
		//含有查找方法的类名
		String className = annotation.className();
		//查找方法的方法名
		String methodName = annotation.methodName();
		//查找的条件
		String primaryKey = annotation.primaryKey();
		//获取主键字段值
		Field field = class1.getDeclaredField(primaryKey);
		field.setAccessible(true);
		String value=(String) ReflectionUtils.getField(field, o);
		
	    //获取含有查找方法的实体类,调用方法执行
		Object bean = SpringContextUtil.getBean(className);
		Class<?> clazz = bean.getClass();
		//获取指定的查询方法
		Method method = clazz.getMethod(methodName, String.class);
		//获取执行结果
		Object object = method.invoke(bean, value);
		
		log.info("findOldData---------------end");
		return object;
	}
	
	
}
