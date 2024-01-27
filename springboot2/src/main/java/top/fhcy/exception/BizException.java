package top.fhcy.exception;

import top.fhcy.enums.ResultCodeEnum;

public class BizException extends RuntimeException {

    private Integer code;

    /**
     * @param message 异常消息
     */
    public BizException(String message) {
        this(ResultCodeEnum.FAILED.getCode(), message);
    }

    /**
     * @param code    异常代码
     * @param message 异常消息
     */
    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
