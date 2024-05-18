package cn.com.dhcc.credit.approval.service;

import cn.com.dhcc.credit.approval.bean.entity.ApprRecord;
import cn.com.dhcc.credit.approval.bean.param.ApprTaskParam;

import java.util.List;

public interface ApprRecordService {

    List<ApprRecord> obtainApprRecord(ApprTaskParam apprTaskParam);

    void record(ApprTaskParam apprTaskParam);
}
