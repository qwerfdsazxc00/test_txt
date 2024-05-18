package cn.com.dhcc.credit.approval.service.impl;

import cn.com.dhcc.credit.approval.bean.entity.ApprRecord;
import cn.com.dhcc.credit.approval.bean.param.ApprTaskParam;
import cn.com.dhcc.credit.approval.constants.Constants;
import cn.com.dhcc.credit.approval.dao.ApprRecordDao;
import cn.com.dhcc.credit.approval.service.ApprRecordService;
import cn.com.dhcc.credit.approval.service.base.BaseService;
import cn.com.dhcc.credit.approval.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprRecordServiceImpl extends BaseService implements ApprRecordService {

    @Autowired
    private ApprRecordDao apprRecordDao;

    @Override
    public List<ApprRecord> obtainApprRecord(ApprTaskParam apprTaskParam) {
        String apptTaskId = apprTaskParam.getApprTaskId();
        return apprRecordDao.findByTaskId(apptTaskId,Constants.DATA_FLAG_VALID);
    }

    @Override
    public void record(ApprTaskParam apprTaskParam) {
        ApprRecord apprRecord = new ApprRecord();
        apprRecord.setApproveDate(DateUtils.now());
        apprRecord.setApprover(apprTaskParam.getUserName());
        apprRecord.setApproveState(apprTaskParam.getApprovalState());
        apprRecord.setTaskId(apprTaskParam.getApprTaskId());
        apprRecord.setApproverLevel(apprTaskParam.getApprovalLevel());
        apprRecord.setRemark(apprTaskParam.getRemark());
        apprRecord.setDataFlag(Constants.DATA_FLAG_VALID);
        apprRecordDao.save(apprRecord);
    }
}
