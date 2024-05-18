package cn.com.dhcc.credit.approval.ann;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基础段相关信息
 * 
 * @author gsh
 * @date 2020年7月5日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface BasicMessage {
   String className();
   String queryMethod();
}
