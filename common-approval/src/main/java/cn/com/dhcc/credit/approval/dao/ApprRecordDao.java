package cn.com.dhcc.credit.approval.dao;

import cn.com.dhcc.credit.approval.bean.entity.ApprRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author
 * 审批记录表
 */
public interface ApprRecordDao  extends PagingAndSortingRepository<ApprRecord, String>, JpaSpecificationExecutor<ApprRecord> {

    @Query("from ApprRecord  ar where ar.taskId = ?1 and ar.dataFlag=?2 order by ar.approveDate asc")
    List<ApprRecord> findByTaskId(String taskId,String dataFlag);
    
    /**
     * 删除原来的审批记录
     * @param taskId
     * @author gsh
     * @date 2020年7月7日
     * @return void
     * 注：返回值为Boolean,写明true,false含义
     */
    @Query("update ApprRecord  ar set ar.dataFlag='1' where ar.taskId=?1")
    @Modifying
    void updateFlagByTaskId(String taskId);
}
