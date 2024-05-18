package cn.com.dhcc.credit.approval.exception;

/**
 * 审批功能：业务主键为空异常
 * @author wanghaowei
 * @date 2020年6月22日
 */
public class PrimaryKeyIsNullException extends RuntimeException{

    public PrimaryKeyIsNullException(String message) {
        super(message);
    }
}
