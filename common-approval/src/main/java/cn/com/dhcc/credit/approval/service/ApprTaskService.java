package cn.com.dhcc.credit.approval.service;

import cn.com.dhcc.credit.approval.bean.entity.ApprTask;
import cn.com.dhcc.credit.approval.bean.param.ApprTaskParam;
import cn.com.dhcc.credit.platform.util.PageBean;


public interface ApprTaskService {

    PageBean findHistory(ApprTaskParam apprTaskParam, PageBean pageBean);

    PageBean approvalTask(ApprTaskParam apprTaskParam, PageBean page);

    ApprTask approval(ApprTaskParam apprTaskParam);

    ApprTask findOne(ApprTaskParam apprTaskParam);
}
