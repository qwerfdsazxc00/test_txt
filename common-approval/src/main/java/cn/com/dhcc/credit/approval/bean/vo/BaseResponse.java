package cn.com.dhcc.credit.approval.bean.vo;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {


    private static final String OK_STATUS = "00000000";
    private static final String OK_MSG = "操作成功";
    private static final String ERROR_STATUS = "00000001";
    private static final String ERROR_MSG = "操作失败";


    private String status;


    private String message;


    private String devMessage;


    private T data;

    public BaseResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public static <T> BaseResponse<T> ok(String message, T data) {
        return new BaseResponse<>(OK_STATUS, message, data);
    }


    public static <T> BaseResponse<T> ok() {
        return ok(null);
    }


    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<T>(OK_STATUS, OK_MSG, data);
    }

    public static <T> BaseResponse<T> error() {
        return new BaseResponse<T>(ERROR_STATUS, ERROR_MSG, null);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<T>(ERROR_STATUS, message, null);
    }
}
