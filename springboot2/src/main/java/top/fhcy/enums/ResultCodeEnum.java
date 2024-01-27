package top.fhcy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum ResultCodeEnum {

    /**
     * 返回结果编码
     */
    SUCCESS(1000, "操作成功"),
    FAILED(1001, "操作失败"),
    ERROR(1002, "系统异常，请联系管理员"),
    INVALID_URL(1003, "无效请求地址"),
    PARAM_ERROR(1004, "请求参数格式异常"),
    INVALID_TOKEN(1005, "无效登录凭证"),
    ACCESS_DENIED(1006, "权限不足");

    private Integer code;

    private String msg;
}
