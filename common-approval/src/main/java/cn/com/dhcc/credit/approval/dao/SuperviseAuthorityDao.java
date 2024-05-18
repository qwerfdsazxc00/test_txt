package cn.com.dhcc.credit.approval.dao;

import cn.com.dhcc.credit.approval.bean.entity.SuperviseAuthority;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SuperviseAuthorityDao extends PagingAndSortingRepository<SuperviseAuthority, String>, JpaSpecificationExecutor<SuperviseAuthority> {


    @Query("from SuperviseAuthority sa where sa.roleId in (?1) and sa.authType = ?2")
    List<SuperviseAuthority> find(List<String> roleIds,String authType);

    @Modifying
    @Query("delete from SuperviseAuthority sa where sa.roleId = ?1 and sa.authType = ?2")
    void delete(String roleId,String authType);
}
