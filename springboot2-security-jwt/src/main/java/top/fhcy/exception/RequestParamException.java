package top.fhcy.exception;

import top.fhcy.enums.ResultCodeEnum;

public class RequestParamException extends RuntimeException {

    private Integer code;
    private String fieldName;

    public RequestParamException(String msg) {
        this(ResultCodeEnum.PARAM_ERROR.getCode(), msg, null);
    }

    public RequestParamException(Integer code, String msg) {
        this(code, msg, null);
    }

    public RequestParamException(Integer code, String msg, String fieldName) {
        super(msg);
        this.code = code;
        this.fieldName = fieldName;
    }

    public Integer getCode() {
        return code;
    }

    public String getFieldName() {
        return fieldName;
    }
}
