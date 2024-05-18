package cn.com.dhcc.credit.approval.exception;

/**
 * @author by 王豪伟
 * @Description 业务操作异常
 * @Date 2020/7/8 14:02
 */
public class BussOperationException  extends RuntimeException{

    public BussOperationException(String message) {
        super(message);
    }

    public BussOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
