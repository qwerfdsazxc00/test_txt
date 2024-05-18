package cn.com.dhcc.credit.approval.exception;

public class ParamIsNullException extends RuntimeException {

    public ParamIsNullException() {
        super();
    }

    public ParamIsNullException(String message) {
        super(message);
    }
}
