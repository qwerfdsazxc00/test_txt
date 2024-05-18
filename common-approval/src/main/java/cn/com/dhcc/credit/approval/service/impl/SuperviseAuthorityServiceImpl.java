package cn.com.dhcc.credit.approval.service.impl;

import cn.com.dhcc.credit.approval.bean.entity.SuperviseAuthority;
import cn.com.dhcc.credit.approval.dao.SuperviseAuthorityDao;
import cn.com.dhcc.credit.approval.service.SuperviseAuthorityService;
import cn.com.dhcc.credit.approval.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SuperviseAuthorityServiceImpl extends BaseService implements SuperviseAuthorityService {

    @Autowired
    private SuperviseAuthorityDao superviseAuthorityDao;

    @Override
    public List<SuperviseAuthority> find(List<String> roleId,String authType) {
        return superviseAuthorityDao.find(roleId,authType);
    }

    @Override
    public SuperviseAuthority save(SuperviseAuthority superviseAuthority){
        return superviseAuthorityDao.save(superviseAuthority);
    }

    @Override
    public Iterable<SuperviseAuthority> save(Iterable<SuperviseAuthority> superviseAuthorities){
        return superviseAuthorityDao.save(superviseAuthorities);
    }

    @Override
    public void delete(String roleId, String authType) {
        superviseAuthorityDao.delete(roleId,authType);
    }
}
