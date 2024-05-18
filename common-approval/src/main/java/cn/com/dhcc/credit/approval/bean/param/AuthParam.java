package cn.com.dhcc.credit.approval.bean.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * auth param
 *
 * @author wanghaowei
 * @date 2020年6月29日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthParam {

    private String roleId;
    /**
     * 标记是个人还是企业
     */
    private String mark;
    /**
     * 0:获取所有
     * 1：获取角色对应的
     */
    private String obtainMark;

    private String authIds;
}
