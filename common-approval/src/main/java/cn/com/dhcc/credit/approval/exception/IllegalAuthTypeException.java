package cn.com.dhcc.credit.approval.exception;

public class IllegalAuthTypeException extends RuntimeException {

    public IllegalAuthTypeException() {
        super();
    }

    public IllegalAuthTypeException(String message) {
        super(message);
    }
}
