package cn.com.dhcc.credit.approval.dao;

import cn.com.dhcc.credit.approval.bean.entity.ApprOperate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ApprOperateDao extends PagingAndSortingRepository<ApprOperate, String>, JpaSpecificationExecutor<ApprOperate> {

    @Query("from ApprOperate  ao where ao.taskId = ?1 order by ao.operateDate asc")
    List<ApprOperate> findByTaskId(String apptTaskId);
}
