package cn.com.dhcc.credit.approval.dao;

import cn.com.dhcc.credit.approval.bean.entity.ApprTask;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ApprTaskDao extends PagingAndSortingRepository<ApprTask, String>, JpaSpecificationExecutor<ApprTask> {
    /**
     * 根据基础段id与审批状态查询审批任务
     * @param dataId
     * @param approveState
     * @return
     * @author gsh
     * @date 2020年7月7日
     * @return List<ApprTask>
     * 注：返回值为Boolean,写明true,false含义
     */
    @Query("from ApprTask at where at.dataId=?1  and at.approveState=?2")
    List<ApprTask> findByDataIdAndState(String dataId,String approveState);

}
