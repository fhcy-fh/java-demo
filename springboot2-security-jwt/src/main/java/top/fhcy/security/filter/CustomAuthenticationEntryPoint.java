package top.fhcy.security.filter;

import com.alibaba.fastjson2.JSON;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.fhcy.enums.ResultCodeEnum;
import top.fhcy.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author fh
 * @date 2024/1/20
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        Result<String> result = Result.failed(ResultCodeEnum.INVALID_TOKEN.getCode(), "验证失败，请重新登录", null);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(JSON.toJSONString(result));
        printWriter.flush();
        printWriter.close();
    }
}
