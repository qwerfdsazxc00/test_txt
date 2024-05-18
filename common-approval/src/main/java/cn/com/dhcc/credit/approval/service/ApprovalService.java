package cn.com.dhcc.credit.approval.service;

import cn.com.dhcc.credit.approval.bean.entity.ApprOperate;
import cn.com.dhcc.credit.approval.bean.entity.ApprRecord;
import cn.com.dhcc.credit.approval.bean.entity.ApprTask;
import cn.com.dhcc.credit.approval.bean.param.ApprTaskParam;
import cn.com.dhcc.credit.platform.util.PageBean;

import java.util.List;

/**
 * 审批
 *
 * @author wanghaowei
 * @date 2020年7月3日
 */
public interface ApprovalService {

    /**
     * 审批
     *
     * @param apprTaskParam
     * @return
     */
    ApprTask approval(ApprTaskParam apprTaskParam);

    /**
     * 获取审批历史
     *
     * @param apprTaskParam
     * @return
     */
    PageBean apprHistory(ApprTaskParam apprTaskParam, PageBean page);

    /**
     * 审批任务列表页
     *
     * @param apprTaskParam
     * @param page
     * @return
     */
    PageBean approvalTask(ApprTaskParam apprTaskParam, PageBean page);

    /**
     * 获取审批记录
     *
     * @param apprTaskParam
     * @return
     */
    List<ApprRecord> obtainApprRecord(ApprTaskParam apprTaskParam);

    /**
     * 获取操作记录
     *
     * @param apprTaskParam
     * @return
     */
    List<ApprOperate> obtainApprOperate(ApprTaskParam apprTaskParam);
}
