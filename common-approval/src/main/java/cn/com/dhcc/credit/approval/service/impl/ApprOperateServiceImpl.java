package cn.com.dhcc.credit.approval.service.impl;

import cn.com.dhcc.credit.approval.bean.entity.ApprOperate;
import cn.com.dhcc.credit.approval.bean.param.ApprTaskParam;
import cn.com.dhcc.credit.approval.dao.ApprOperateDao;
import cn.com.dhcc.credit.approval.service.ApprOperateService;
import cn.com.dhcc.credit.approval.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprOperateServiceImpl extends BaseService implements ApprOperateService {

    @Autowired
    private ApprOperateDao apprOperateDao;

    @Override
    public List<ApprOperate> obtainApprOperate(ApprTaskParam apprTaskParam) {
        String apprTaskId = apprTaskParam.getApprTaskId();
        return apprOperateDao.findByTaskId(apprTaskId);
    }
}
