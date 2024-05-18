package cn.com.dhcc.credit.approval.service;

import cn.com.dhcc.credit.approval.bean.entity.SuperviseAuthority;

import java.util.List;

public interface SuperviseAuthorityService {


    List<SuperviseAuthority> find(List<String> roleIds,String authType);

    SuperviseAuthority save(SuperviseAuthority superviseAuthority);

    Iterable<SuperviseAuthority> save(Iterable<SuperviseAuthority> superviseAuthorities);

    void delete(String roleId,String authType);
}
