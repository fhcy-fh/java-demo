package top.fhcy.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.fhcy.enums.ResultCodeEnum;
import top.fhcy.result.Result;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.springboot.demo")
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public Result<String> handleException(Exception ex) {
        customHandleException(ex);
        writeLog(ex);
        return Result.failed(ResultCodeEnum.ERROR.getCode(), ex.getMessage(), null);
    }

    @ExceptionHandler({BizException.class})
    public Result<String> handleBizException(BizException ex) {
        customHandleException(ex);
        writeLog(ex);
        return Result.failed(ex.getCode(), ex.getMessage(), null);
    }


    @ExceptionHandler(SQLException.class)
    public Result<String> handleSqlException(SQLException ex) {
        customHandleException(ex);
        writeLog(ex);
        return Result.failed(ResultCodeEnum.ERROR.getCode(), "数据库异常", null);
    }


    @ExceptionHandler(RequestParamException.class)
    public Result<Map<String, String>> handleRequestParameterException(RequestParamException ex) {
        customHandleException(ex);
        writeLog(ex);
        Map<String, String> map = new HashMap<>(1);
        if (StringUtils.isNotBlank(ex.getFieldName())) {
            map.put(ex.getFieldName(), ex.getMessage());
        }
        return Result.failed(ex.getCode(), ex.getMessage(), map);
    }


    @ExceptionHandler(BindException.class)
    public Result<Map<String, String>> handleBindException(BindException ex) {
        customHandleException(ex);
        writeLog(ex);
        List<FieldError> errors = ex.getFieldErrors();
        errors = errors.stream().filter(Objects::nonNull).collect(Collectors.toList());
        Map<String, String> map = new LinkedHashMap<>(errors.size());
        String msg = errors.stream().findFirst().get().getDefaultMessage();
        for (FieldError error : errors) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        ResultCodeEnum pe = ResultCodeEnum.PARAM_ERROR;
        return Result.failed(pe.getCode(), msg, map);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        customHandleException(ex);
        writeLog(ex);
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Map<String, String> map = new LinkedHashMap<>(constraintViolations.size());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String property = propertyPath.substring(propertyPath.indexOf(".") + 1);
            map.put(property, constraintViolation.getMessage());
        }
        ResultCodeEnum pe = ResultCodeEnum.PARAM_ERROR;
        return Result.failed(pe.getCode(), pe.getMsg(), map);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        customHandleException(ex);
        writeLog(ex);
        String message = ex.getMessage() + ", Request method '" +
                this.arrayToCommaDelimitedString(ex.getSupportedMethods()) + "' supported";
        return Result.failed(ResultCodeEnum.ERROR.getCode(), message, null);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        customHandleException(ex);
        writeLog(ex);
        ResultCodeEnum invalidUrl = ResultCodeEnum.INVALID_URL;
        return Result.failed(invalidUrl.getCode(), invalidUrl.getMsg(), null);
    }

    private void writeLog(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    protected void customHandleException(Exception ex) {
        // do nothing default.
    }

    private String arrayToCommaDelimitedString(Object[] ary) {
        return arrayToDelimitedString(ary, ",");
    }

    private String arrayToDelimitedString(Object[] ary, String delimiter) {
        if (ObjectUtils.isEmpty(ary)) {
            return "";
        }
        if (ary.length == 1) {
            return ObjectUtils.nullSafeToString(ary[0]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ary.length; i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            sb.append(ary[i]);
        }
        return sb.toString();
    }
}
