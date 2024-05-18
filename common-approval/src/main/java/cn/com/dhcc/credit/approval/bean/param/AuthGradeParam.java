package cn.com.dhcc.credit.approval.bean.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by 王豪伟
 * @Description 获取权限等级，传递参数的bean
 * @Date 2020/7/3 16:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthGradeParam {
    /**
     * 区分个人企业
     */
    private String mark;
    /**
     * 权限类型
     */
    private String authType;
    /**
     * 角色所有Id,使用 , 分割
     */
    private String roleIdArray;
}
