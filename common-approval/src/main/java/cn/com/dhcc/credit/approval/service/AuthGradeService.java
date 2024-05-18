package cn.com.dhcc.credit.approval.service;

import cn.com.dhcc.credit.approval.bean.param.AuthGradeParam;

import java.util.List;

/**
 * 获取权限等级
 *
 * @author wanghaowei
 * @date 2020年6月29日
 */
public interface AuthGradeService {

    /**
     * 获取权限类型的最高等级
     *
     * @param authGradeParam
     * @return 等级
     */
    String obtainAuthGrade(AuthGradeParam authGradeParam);
    /**
     * 获取用户所有角色Id
     * @param authGradeParam
     * @return
     */
    List<String> obtainUserRoleIds(AuthGradeParam authGradeParam);
}
