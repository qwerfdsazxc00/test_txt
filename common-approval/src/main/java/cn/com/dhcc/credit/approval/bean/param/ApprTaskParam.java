package cn.com.dhcc.credit.approval.bean.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Appr Task Param
 *
 * @author wanghaowei
 * @date 2020年6月29日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprTaskParam {

    /**
     * 标记是个人还是企业
     * 0:企业
     * 1:个人
     */
    private String mark;
    /**
     * 审批任务id
     */
    private String apprTaskId;
    /**
     * 用户
     */
    private String userName;
    /**
     * 审批备注
     */
    private String remark;
    /**
     * 审批状态
     */
    private String approvalState;
    /**
     * 审批等级
     */
    private String approvalLevel;
    /**
     * B段数据Id
     */
    private String dataId;
}
