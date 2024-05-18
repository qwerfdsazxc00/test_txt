package cn.com.dhcc.credit.approval.service.impl;

import cn.com.dhcc.credit.approval.bean.param.AuthGradeParam;
import cn.com.dhcc.credit.approval.constants.Constants;
import cn.com.dhcc.credit.approval.exception.IllegalAuthTypeException;
import cn.com.dhcc.credit.approval.service.AuthGradeService;
import cn.com.dhcc.credit.approval.service.base.BaseService;
import cn.com.dhcc.credit.common.utils.UserUtils;
import cn.com.dhcc.credit.platform.util.redis.RedissonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取权限等级
 *
 * @author wanghaowei
 * @date 2020年6月29日
 */
@Slf4j
@Component
public class AuthGradeServiceImpl extends BaseService implements AuthGradeService {

    private RedissonClient localRedisson = RedissonUtil.getLocalRedisson();
    private Map<String, String> markMap = new HashMap<>();

    private Map<String, String> authTypeAndAuthConfigNameMap = new HashMap<>();
    {
        markMap.put("0", "e_config_table");
        markMap.put("1", "config_table");

        authTypeAndAuthConfigNameMap.put(Constants.E_APPROVAL_STR,Constants.APPR_LEVEL_STR);
        authTypeAndAuthConfigNameMap.put(Constants.P_APPROVAL_STR,Constants.APPR_LEVEL_STR);
    }

    private static final String ROLE_STR = "role";
    private static final String ID_STR = "id";

    /**
     * 获取权限类型的最高等级
     *
     * @param authGradeParam
     * @return
     */
    @Override
    public String obtainAuthGrade(AuthGradeParam authGradeParam) {
        String mark = authGradeParam.getMark();
        String authType = authGradeParam.getAuthType();
        //根据权限类型获取 配置表中的参数名称
        String configName = authTypeAndAuthConfigNameMap.get(authType);
        String redisKey = markMap.get(mark);
        RMap<Object, Object> configMap = localRedisson.getMap(redisKey);
        if (configMap.get(configName) == null) {
            throw new IllegalAuthTypeException("找不到对应的权限类型！");
        }
        return configMap.get(configName).toString();
    }

    /**
     * @param authGradeParam
     * @return
     */
    @Override
    public List<String> obtainUserRoleIds(AuthGradeParam authGradeParam) {
        List<String> ids = new ArrayList<>();
        String roleArrayStr = "";
        if(authGradeParam == null || StringUtils.isEmpty(authGradeParam.getRoleIdArray())){
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            Map<String, String> userInfo = UserUtils.getUserInfo(request);
            roleArrayStr = userInfo.get(ROLE_STR);
        }
        JSONArray jsonArray = JSON.parseArray(roleArrayStr);

        for (int i = 0; i < jsonArray.size(); i++) {
            Object item = jsonArray.get(i);
            Map map = JSON.parseObject(item.toString(), Map.class);
            ids.add(map.get(ID_STR).toString());
        }
        return ids;
    }
}
