package cn.com.dhcc.credit.approval.service;

import cn.com.dhcc.credit.approval.bean.entity.ApprOperate;
import cn.com.dhcc.credit.approval.bean.param.ApprTaskParam;

import java.util.List;

public interface ApprOperateService {


    List<ApprOperate> obtainApprOperate(ApprTaskParam apprTaskParam);
}
