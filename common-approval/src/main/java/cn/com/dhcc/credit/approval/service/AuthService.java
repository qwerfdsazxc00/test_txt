package cn.com.dhcc.credit.approval.service;

import cn.com.dhcc.credit.approval.bean.param.AuthParam;
import cn.com.dhcc.credit.approval.bean.entity.SuperviseAuthority;
import cn.com.dhcc.credit.approval.bean.vo.Tab;

import java.util.List;

/**
 * 权限操作service
 *
 * @author wanghaowei
 * @date 2020年6月28日
 */

public interface AuthService {

    /**
     * 获取权限
     *
     * @param authParam
     * @return
     */
    List<Tab> obtainAuth(AuthParam authParam);

    /**
     * 更新权限
     *
     * @param authParam
     * @return
     */
    Iterable<SuperviseAuthority>  updateAuth(AuthParam authParam);


}
