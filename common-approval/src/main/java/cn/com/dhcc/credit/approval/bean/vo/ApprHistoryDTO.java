package cn.com.dhcc.credit.approval.bean.vo;

import lombok.Data;
import lombok.ToString;


/**
 * @author by 王豪伟
 * @Date 2020/7/29 16:20
 */
@Data
@ToString
public class ApprHistoryDTO {
    /**
     * ID
     */
    private String taskId;

    /**
     * 姓名
     */
    private String operator;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idNum;

    /**
     * 业务编号
     */
    private String bussNum;

    /**
     * 业务类型
     */
    private String bussType;

    /**
     * 所属机构
     */
    private String deptCode;

    /**
     * 审批员
     */
    private String approver;
    /**
     * 审批状态
     */
    private String approveState;
}
