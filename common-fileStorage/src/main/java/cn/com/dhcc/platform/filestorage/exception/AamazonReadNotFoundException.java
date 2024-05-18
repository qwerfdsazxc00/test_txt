package cn.com.dhcc.platform.filestorage.exception;

public class AamazonReadNotFoundException extends RuntimeException{

    /**
     *
     */
    public AamazonReadNotFoundException(String code, String msg) {
        super(code+":"+msg);
    }
    /**
     *
     */
    public AamazonReadNotFoundException(String msg) {
        super(msg);
    }


}
