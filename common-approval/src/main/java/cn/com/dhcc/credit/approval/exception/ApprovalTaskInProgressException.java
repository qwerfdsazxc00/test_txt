package cn.com.dhcc.credit.approval.exception;

/**
 * @author by 王豪伟
 * @Description 审批任务正在审批中
 * @Date 2020/7/8 14:47
 */
public class ApprovalTaskInProgressException extends RuntimeException{

    public ApprovalTaskInProgressException(String message) {
        super(message);
    }
}
