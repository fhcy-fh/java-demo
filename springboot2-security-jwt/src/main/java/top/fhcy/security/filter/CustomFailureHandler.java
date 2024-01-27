package top.fhcy.security.filter;

import com.alibaba.fastjson2.JSON;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
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
@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        Result<String> result = Result.failed(ResultCodeEnum.INVALID_TOKEN.getCode(), "账号或密码错误", null);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(JSON.toJSONString(result));
        printWriter.flush();
        printWriter.close();
//        response.sendRedirect("/loginError");
    }
}
