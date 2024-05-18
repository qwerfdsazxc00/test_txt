package cn.com.dhcc.credit.approval.config;

import cn.com.dhcc.credit.approval.service.AuthGradeService;
import cn.com.dhcc.credit.approval.service.impl.AuthGradeServiceImpl;
import cn.com.dhcc.credit.approval.service.impl.DefaultOperationsHistoryWriter;
import cn.com.dhcc.credit.approval.service.impl.DefaultOperatorProvider;
import cn.com.dhcc.platform.middleware.operationshistory.operator.OperatorProvider;
import cn.com.dhcc.platform.middleware.operationshistory.writer.OperationsHistoryWriter;
import org.springframework.context.annotation.Bean;

/**
 * 配置类
 * @author wanghaowei
 * @date 2020年7月3日
 */
public class ApprovalConfigure {

  /*  @Bean
    public AuthGradeService authGradeService(){
        return new AuthGradeServiceImpl();
    }*/

  /*  @Bean
    public OperatorProvider operatorProvider(){
        return new DefaultOperatorProvider();
    }

    @Bean
    public OperationsHistoryWriter operationsHistoryWriter(){
        return new DefaultOperationsHistoryWriter();
    }*/
}
