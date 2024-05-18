package cn.com.dhcc.credit.approval.service.impl;

import cn.com.dhcc.credit.common.utils.CommonConstant;
import cn.com.dhcc.credit.common.utils.UserUtils;
import cn.com.dhcc.platform.middleware.operationshistory.operator.OperatorProvider;
import cn.com.dhcc.platform.middleware.operationshistory.operator.subject.Operator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 审批功能：获取用户信息的功能类
 * @author wanghaowei
 * @date 2020年6月19日
 */
@Component
public class DefaultOperatorProvider implements OperatorProvider {

    public static final String ORG_NO = "orgNo";

    /**
     * 拼装用户信息
     * @return 封装完成的用户信息
     */
    @Override
    public Operator findOperator() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Map<String, String> userInfo = UserUtils.getUserInfo(request);
        Operator operator = new Operator();
        String deptCode = userInfo.get(ORG_NO).trim();
        operator.setOrgEntity(deptCode);
        operator.setUserName(userInfo.get(CommonConstant.USERNAME).trim());
        return operator;
    }
}
