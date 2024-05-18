package cn.com.dhcc.credit.approval.service.impl;

import cn.com.dhcc.credit.approval.bean.entity.ApprTask;
import cn.com.dhcc.credit.approval.bean.entity.SuperviseAuthority;
import cn.com.dhcc.credit.approval.bean.param.ApprTaskParam;
import cn.com.dhcc.credit.approval.bean.param.AuthGradeParam;
import cn.com.dhcc.credit.approval.bean.vo.ApprHistoryDTO;
import cn.com.dhcc.credit.approval.constants.Constants;
import cn.com.dhcc.credit.approval.dao.ApprTaskDao;
import cn.com.dhcc.credit.approval.exception.ApprovalTaskInProgressException;
import cn.com.dhcc.credit.approval.exception.BussOperationException;
import cn.com.dhcc.credit.approval.service.ApprRecordService;
import cn.com.dhcc.credit.approval.service.ApprTaskService;
import cn.com.dhcc.credit.approval.service.AuthGradeService;
import cn.com.dhcc.credit.approval.service.SuperviseAuthorityService;
import cn.com.dhcc.credit.approval.service.base.BaseService;
import cn.com.dhcc.credit.approval.utils.DateUtils;
import cn.com.dhcc.credit.approval.utils.NumberConvertToChinese;
import cn.com.dhcc.credit.common.utils.UserUtils;
import cn.com.dhcc.credit.platform.util.PageBean;
import cn.com.dhcc.credit.platform.util.Servlets;
import cn.com.dhcc.credit.platform.util.redis.RedissonUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author wanghaowei
 * @date 2020年7月3日
 */
@Service
public class ApprTaskServiceImpl extends BaseService implements ApprTaskService {

    @Autowired
    private ApprTaskDao apprTaskDao;
    @Autowired
    private AuthGradeService authGradeService;
    @Autowired
    private SuperviseAuthorityService authorityService;
    @Autowired
    private ApprRecordService apprRecordService;
    @PersistenceContext
    private EntityManager entityManager;

    private RedissonClient redisson = RedissonUtil.getLocalRedisson();

    private static final String ORDER_BY_OPERATEDATE = "operateDate";
    private static final String ORDER_BY_APPROVEDATE = "approveDate";
    private static final String QUERY_PARMA_PREFIX = "search_";

    @Override
    public PageBean findHistory(ApprTaskParam apprTaskParam, PageBean pageBean) {
        HttpServletRequest request = getCurRequest();
        String deptCode = UserUtils.getDeptCode(request);
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, QUERY_PARMA_PREFIX, pageBean, deptCode);
        String bussCategory = apprTaskParam.getMark().equalsIgnoreCase(Constants.ENT_MARK) ? Constants.ENT_FLAG : Constants.PERSON_FLAG;
        PageRequest pageRequest = buildPageRequest(pageBean, Constants.ORDER_ASC, ORDER_BY_APPROVEDATE);
        searchParams.put("EQ_bussCategory",bussCategory);
        searchParams.put("NE_approveState_OR",Constants.APPR_STATE_WAIT);
        searchParams.put("NE_approverLevel_OR",Constants.APPR_LEVEL_ONE);
        Specification<ApprTask> spec = buildSpecification(searchParams,ApprTask.class);
        Page<ApprTask> page = apprTaskDao.findAll(spec, pageRequest);
        Page<ApprHistoryDTO> apprHistoryDTOS = page.map(this::convertToVo);
        return convert(apprHistoryDTOS, pageBean);
    }

    /**
     * 分页方法支持审批员拥有多个审批等级，如果拥有多个等级 会查询出对应等级的所有任务
     *
     * @param apprTaskParam
     * @param pageBean
     * @return
     */
    @Override
    public PageBean approvalTask(ApprTaskParam apprTaskParam, PageBean pageBean) {
        HttpServletRequest request = getCurRequest();
        String deptCode = UserUtils.getDeptCode(request);

        List<String> roleIds = authGradeService.obtainUserRoleIds(AuthGradeParam.builder().build());
        String mark = apprTaskParam.getMark();
        String authType = Constants.ENT_MARK.equals(mark) ? Constants.E_APPROVAL_STR : Constants.P_APPROVAL_STR;
        String bussCategory = Constants.ENT_MARK.equals(mark) ? Constants.ENT_FLAG : Constants.PERSON_FLAG;
        List<SuperviseAuthority> superviseAuthorities = authorityService.find(roleIds, authType);
        if (CollectionUtils.isEmpty(superviseAuthorities)) {
            return pageBean;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < superviseAuthorities.size(); i++) {
            SuperviseAuthority superviseAuthority = superviseAuthorities.get(i);
            String authLevel = superviseAuthority.getAuthLevel();
            builder.append(authLevel);
            if (i != superviseAuthorities.size() - 1) {
                builder.append(",");
            }
        }
        String apprLevel = superviseAuthorities.get(0).getAuthLevel();
        String orderByStr = Constants.APPR_LEVEL_ONE.equals(apprLevel) ? ORDER_BY_OPERATEDATE : ORDER_BY_APPROVEDATE;

        PageRequest pageRequest = buildPageRequest(pageBean, Constants.ORDER_ASC, orderByStr);
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, QUERY_PARMA_PREFIX, pageBean, deptCode);
        searchParams.put("IN_approverLevel", builder.toString());
        searchParams.put("EQ_bussCategory", bussCategory);
        searchParams.put("EQ_approveState", Constants.APPR_STATE_WAIT);
        Specification<ApprTask> spec = buildSpecification(searchParams,ApprTask.class);
        Page<ApprTask> page = apprTaskDao.findAll(spec, pageRequest);
        return convert(page, pageBean);
    }

    private ApprHistoryDTO convertToVo(ApprTask apprTask) {
        ApprHistoryDTO apprHistoryDTO = new ApprHistoryDTO();
        org.springframework.beans.BeanUtils.copyProperties(apprTask, apprHistoryDTO);
        //如果审批状态未待审批，则前面需要加上等级
        String approveState = apprTask.getApproveState();
        String res = "";
        if (Constants.APPR_STATE_WAIT.equals(approveState)) {
            res = NumberConvertToChinese.numToChinese(Integer.parseInt(apprTask.getApproverLevel())) + "级待审批";
        } else if (Constants.APPR_STATE_REFUSE.equals(approveState)) {
            res = "审批拒绝";
        } else if (Constants.APPR_STATE_PASS.equals(approveState)) {
            res = "审批通过";
        }
        apprHistoryDTO.setApproveState(res);
        return apprHistoryDTO;
    }

    /**
     * 审批过程
     *
     * @param apprTaskParam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApprTask approval(ApprTaskParam apprTaskParam) {
        //添加redis锁，确保审批过程正确性
        String dataId = apprTaskParam.getDataId();
        RLock lock = redisson.getLock(dataId);
        if (!lock.tryLock()) {
            throw new ApprovalTaskInProgressException("approval task in progress！");
        }
        try {
            String apprTaskId = apprTaskParam.getApprTaskId();
            String curLevel = apprTaskParam.getApprovalLevel();
            ApprTask apprTask = apprTaskDao.findOne(apprTaskId);
            String originalLevel = apprTask.getApproverLevel();
            if (!originalLevel.equals(curLevel)) {
                throw new RuntimeException("task approvaled! ");
            }
            String bussType = apprTask.getBussType();
            if (Constants.APPR_LEVEL_ONE.equals(originalLevel)) {
                updateBussTableForBussType(bussType, Constants.APPR_STATE_PENDING, dataId);
            }
            String userName = UserUtils.getUserName(getCurRequest());
            apprTaskParam.setUserName(userName);
            apprRecordService.record(apprTaskParam);
            String mark = apprTaskParam.getMark();
            String authType = Constants.ENT_MARK.equals(mark) ? Constants.E_APPROVAL_STR : Constants.P_APPROVAL_STR;
            String approvalState = apprTaskParam.getApprovalState();
            String nextApprovalState = Constants.APPR_STATE_WAIT;
            String nextApprovalLevel = curLevel;
            if (Constants.APPR_STATE_PASS.equals(approvalState)) {
                String maxAuthLevel = authGradeService.obtainAuthGrade(AuthGradeParam.builder().mark(mark).authType(authType).build());
                //最高审批等级和 目前审批等级作比较
                if (curLevel.compareTo(maxAuthLevel) >= 0) {
                    nextApprovalState = Constants.APPR_STATE_PASS;
                } else {
                    nextApprovalLevel = String.valueOf(Integer.parseInt(curLevel) + 1);
                }
            } else if (Constants.APPR_STATE_REFUSE.equals(approvalState)) {
                nextApprovalState = Constants.APPR_STATE_REFUSE;
            } else {
                throw new RuntimeException("approval state exception !");
            }

            apprTask.setApproverLevel(nextApprovalLevel);
            apprTask.setApproveState(nextApprovalState);
            apprTask.setApprover(userName);
            apprTask.setApproveDate(DateUtils.now());
            ApprTask res = apprTaskDao.save(apprTask);

            if (!nextApprovalState.equals(Constants.APPR_STATE_WAIT)) {
                updateBussTableForBussType(bussType, nextApprovalState, dataId);
            }
            return res;
        } catch (Exception e) {
            throw new BussOperationException("update buss table exception！", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public ApprTask findOne(ApprTaskParam apprTaskParam) {
        return apprTaskDao.findOne(apprTaskParam.getApprTaskId());
    }


    private void updateBussTableForBussType(String bussType, String auditState, String dataId) {
        StringBuffer sql = new StringBuffer();
        sql.append("update ").append(bussType).append(" set auditing_state='").append(auditState).append("' where ")
                .append("id").append("='").append(dataId).append("'");
        entityManager.joinTransaction();
        entityManager.createNativeQuery(sql.toString()).executeUpdate();
    }

}
