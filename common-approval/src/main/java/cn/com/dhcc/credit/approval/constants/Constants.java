package cn.com.dhcc.credit.approval.constants;

/**
 * 常量类
 *
 * @author wanghaowei
 * @date 2020年6月29日
 */
public interface Constants {
    /**
     * 企业审批权限类型
     */
    String E_APPROVAL_STR = "e_approval";
    /**
     * 个人审批权限类型
     */
    String P_APPROVAL_STR = "p_approval";
    /**
     * 拼装几级审批使用的字符串数组
     */
//    final String num[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
//    final String unit[] = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
    String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    /**
     * 获取全部权限标志
     */
    String OBTAIN_ALL_AUTH = "0";
    /**
     * 获取角色权限标志
     */
    String OBTAIN_ROLE_AUTH = "1";
    /**
     * 企业权限标志
     */
    String ENT_MARK = "0";
    /**
     * 个人权限标志
     */
    String PERSON_MARK = "1";
    /**
     * 审批状态：待审批
     */
    String APPR_STATE_WAIT = "1";
    /**
     * 审批状态：审核中
     */
    String APPR_STATE_PENDING= "2";
    /**
     * 审批状态：审批拒绝
     */
    String APPR_STATE_REFUSE = "3";
    /**
     * 审批状态：审批通过
     */
    String APPR_STATE_PASS = "4";
    /**
     * 审批状态：无需审批
     */
    String APPR_STATE_NOAUDIT = "0";
    /**
     * 审批等级：一级
     */
    String APPR_LEVEL_ONE = "1";
    /**
     * 升序排序
     */
    String ORDER_ASC = "asc";
    /**
     * 倒序排序
     */
    String ORDER_DESC = "desc";
    /**
     * 企业需要从基础段中取出的值
     */
    String[] ENT_FIELDS={"entCertNum","entCertType","entName","deptCode"};
    /**
     * 个人需要从基础段中取出的值
     */
    String[] PERSON_FIELDS={"idNum","idType","name","deptCode"};
    /**
     * 企业标识
     */
    String ENT_FLAG="E";
    
    /**
     * 个人标识
     */
    String PERSON_FLAG="P";

    /**
     * boolean 值
     */
    String BOOLEAN_TRUE_STR = "true";
    String BOOLEAN_FALSE_STR = "false";
    /**
     *审批等级
     */
    String APPR_LEVEL_STR = "approvalLevel";
    /**
     *审批开关
     */
    String APPR_SWITCH_STR = "approvalSwitch";
    /**
     *审批细分参数
     */
    String APPR_DETAIL_STR = "approvalDetails";
    
    /**
     * 审批细分参数--修改操作
     */
    String APPR_DETAIL_UPDATE="U";
    /**
     * 审批细分参数--新增操作
     */
    String APPR_DETAIL_CREATE="C";
    /**
     * 审批细分参数--删除操作
     */
    String APPR_DETAIL_DELETE="D";
    /**
     * 审批开关-关闭
     */
    String APPR_SWITCH_NO="N";
    /**
     * 审批开关-开启
     */
    String APPR_SWITCH_YES="Y";
    /**
     * id
     */
    String ID_STR = "id";
    /**
     * 审批记录表数据标志：有效
     */
    String DATA_FLAG_VALID="0";
    /**
     * 审批记录表数据标志：无效
     */
    String DATA_FLAG_INVALID="1";

}
