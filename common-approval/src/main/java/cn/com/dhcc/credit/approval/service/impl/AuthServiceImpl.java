package cn.com.dhcc.credit.approval.service.impl;

import cn.com.dhcc.credit.approval.bean.entity.SuperviseAuthority;
import cn.com.dhcc.credit.approval.bean.param.AuthGradeParam;
import cn.com.dhcc.credit.approval.bean.param.AuthParam;
import cn.com.dhcc.credit.approval.bean.vo.Tab;
import cn.com.dhcc.credit.approval.constants.AuthType;
import cn.com.dhcc.credit.approval.constants.Constants;
import cn.com.dhcc.credit.approval.exception.IllegalAuthTypeException;
import cn.com.dhcc.credit.approval.exception.ParamIsNullException;
import cn.com.dhcc.credit.approval.service.AuthGradeService;
import cn.com.dhcc.credit.approval.service.AuthService;
import cn.com.dhcc.credit.approval.service.SuperviseAuthorityService;
import cn.com.dhcc.credit.approval.service.base.BaseService;
import cn.com.dhcc.credit.approval.utils.DateUtils;
import cn.com.dhcc.credit.approval.utils.NumberConvertToChinese;
import cn.com.dhcc.credit.common.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 权限操作service
 *
 * @author wanghaowei
 * @date 2020年6月28日
 */
@Service
public class AuthServiceImpl extends BaseService implements AuthService {


    @Autowired
    private SuperviseAuthorityService superviseAuthorityService;
    @Autowired
    private AuthGradeService authGradeService;

    private static final String SEPARATOR = ":";
    private static final String PARENT_NODE = "parent";
    private static final int ARRPOVAL_AUTH_MAX_LEVEL = 5;
    @Override
    public List<Tab> obtainAuth(AuthParam authParam) {
        checkParam(authParam);

        List<Tab> tabs = new ArrayList<>();

        if (Constants.OBTAIN_ALL_AUTH.equals(authParam.getObtainMark())) {
            obtainAllAuth(tabs, authParam);
        } else {
            obtainRoleAuth(tabs, authParam);
        }
        return tabs;
    }

    private void obtainRoleAuth(List<Tab> tabs, AuthParam authParam) {
        String roleId = authParam.getRoleId();
        if (StringUtils.isEmpty(roleId)) {
            throw new ParamIsNullException("角色Id不能为空");
        }
        String[] authTypes = AuthType.typeOf(authParam.getMark());
        Map<String, Tab> tabMap = new HashMap<>();
        for (String authType : authTypes) {
            List<SuperviseAuthority> superviseAuthorities = superviseAuthorityService.find(Arrays.asList(roleId), authType);
            //获取权限类型的最高等级
            String authGrade = authGradeService.obtainAuthGrade(AuthGradeParam.builder().mark(authParam.getMark()).authType(authType).build());

            for (SuperviseAuthority superviseAuthority : superviseAuthorities) {
                String authLevel = superviseAuthority.getAuthLevel();
                if (authGrade.compareTo(authLevel) < 0) {
                    continue;
                }

                Tab tab = assembleChildTab(authType, authLevel);
                tab.setChecked(Constants.BOOLEAN_TRUE_STR);
                Tab parentTab = tabMap.get(authType);
                if (parentTab == null) {
                    //组装父节点
                    parentTab = assembleParentTab(authType, authLevel);
                    parentTab.setChecked(Constants.BOOLEAN_TRUE_STR);
                    tabs.add(parentTab);
                    tabMap.put(authType, parentTab);
                }
                parentTab.getChildren().add(tab);
            }
        }
    }

    private void obtainAllAuth(List<Tab> tabs, AuthParam authParam) {
        String[] authTypes = AuthType.typeOf(authParam.getMark());
        for (String authType : authTypes) {
            //获取当前类型的所有等级权限
            int authGradeInt = obtainApprovalAuthGrade(authParam,authType);
            Tab parentTab = assembleParentTab(authType, String.valueOf(authGradeInt));
            parentTab.setChecked(Constants.BOOLEAN_FALSE_STR);
            for (int i = 1; i <= authGradeInt; i++) {
                Tab tab = assembleChildTab(authType, String.valueOf(i));
                tab.setChecked(Constants.BOOLEAN_FALSE_STR);
                parentTab.getChildren().add(tab);
            }
            tabs.add(parentTab);
        }
    }

    private Tab assembleChildTab(String authType, String authGrade) {
        Tab tab = new Tab();
        tab.setName(NumberConvertToChinese.numToChinese(Integer.parseInt(authGrade)) + childAuthNameCN(authType));
        tab.setParentId(PARENT_NODE + SEPARATOR + authType);
        tab.setId(authGrade + SEPARATOR + authType);
        return tab;
    }

    private Tab assembleParentTab(String authType, String authGrade) {
        Tab tab = new Tab();
        tab.setName(parentAuthNameCN(authType));
        tab.setChildren(new ArrayList<>());
        tab.setParentId("");
        tab.setId(PARENT_NODE + SEPARATOR + authType);
        return tab;
    }

    private String parentAuthNameCN(String authType) {
        switch (authType) {
            case Constants.E_APPROVAL_STR:
            case Constants.P_APPROVAL_STR:
                return "审批级别";
            default:
                throw new IllegalAuthTypeException("找不到对应的权限类型！");
        }
    }

    private String childAuthNameCN(String authType) {
        switch (authType) {
            case Constants.E_APPROVAL_STR:
            case Constants.P_APPROVAL_STR:
                return "级审批权限";
            default:
                throw new IllegalAuthTypeException("找不到对应的权限类型！");
        }
    }


    private void checkParam(AuthParam authParam) {
        if (authParam == null) {
            throw new ParamIsNullException("authParam is null!");
        }
    }

    private int obtainApprovalAuthGrade(AuthParam authParam,String authType){
        String authGrade = authGradeService.obtainAuthGrade(AuthGradeParam.builder().mark(authParam.getMark()).authType(authType).build());
        //获取当前类型的所有等级权限
        int authGradeInt = Integer.parseInt(authGrade);
        if(authGradeInt > ARRPOVAL_AUTH_MAX_LEVEL && (Constants.P_APPROVAL_STR.equals(authType) || Constants.E_APPROVAL_STR.equals(authType))){
            return ARRPOVAL_AUTH_MAX_LEVEL;
        }
        return authGradeInt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Iterable<SuperviseAuthority> updateAuth(AuthParam authParam) {
        checkParam(authParam);
        String authIds = authParam.getAuthIds();
        String roleId = authParam.getRoleId();
        String[] types = AuthType.typeOf(authParam.getMark());
        for (String authType : types) {
            superviseAuthorityService.delete(roleId, authType);
        }
        if (StringUtils.isEmpty(authIds)) {
            return null;
        }
        String[] authArray = authIds.split(",");

        String userName = UserUtils.getUserName(getCurRequest());
        List<SuperviseAuthority> superviseAuthorities = new ArrayList<>();
        //存储审批等级的最高级
        String approvalAuthLevel = "";
        String approvalAuthType = "";
        for (String authTypeAndGrade : authArray) {
            String authGrade = authTypeAndGrade.split(SEPARATOR)[0];
            if (PARENT_NODE.equals(authGrade)) {
                continue;
            }
            String authType = authTypeAndGrade.split(SEPARATOR)[1];
            if (Constants.E_APPROVAL_STR.equals(authType) || Constants.P_APPROVAL_STR.equals(authType)) {
                //如果是审批 ，审批等级只取最高等级
                approvalAuthLevel = authGrade.compareTo(approvalAuthLevel) > 0 ? authGrade : approvalAuthLevel;
                approvalAuthType = authType;
                continue;
            }
            saveAuth(authType, authGrade, superviseAuthorities, roleId, userName);
        }
        saveApprovalAuth(approvalAuthType, approvalAuthLevel, superviseAuthorities, roleId, userName);
        return superviseAuthorityService.save(superviseAuthorities);
    }

    private void saveApprovalAuth(String authType, String authLevel, List<SuperviseAuthority> superviseAuthorities, String roleId, String userName) {
        if (StringUtils.isNotEmpty(authLevel)) {
            saveAuth(authType, authLevel, superviseAuthorities, roleId, userName);
        }
    }


    private void saveAuth(String authType, String authLevel, List<SuperviseAuthority> superviseAuthorities, String roleId, String userName) {
        SuperviseAuthority superviseAuthority = new SuperviseAuthority();
        superviseAuthority.setAuthLevel(authLevel);
        superviseAuthority.setAuthType(authType);
        superviseAuthority.setRoleId(roleId);
        Date now = DateUtils.now();
        superviseAuthority.setCreateTime(now);
        superviseAuthority.setUpdateTime(now);
        superviseAuthority.setCreateUser(userName);
        superviseAuthorities.add(superviseAuthority);
    }
}
