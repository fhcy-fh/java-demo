package top.fhcy.security.filter;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.fhcy.enums.ResultCodeEnum;
import top.fhcy.result.Result;
import top.fhcy.security.consts.CustomConst;
import top.fhcy.security.entity.CustomUser;
import top.fhcy.security.entity.CustomUserDetails;
import top.fhcy.security.utils.JwtUtils;
import top.fhcy.security.utils.RsaUtils;
import top.fhcy.utils.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author fh
 * @date 2024/1/19
 */
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RsaUtils rsaUtils;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 自定义逻辑
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        CustomUser customUser = customUserDetails.getCustomUser();
        String token = jwtUtils.generateToken(customUser, rsaUtils.getPrivateKey());
        redisUtils.setValue(CustomConst.LOGIN_KEY + customUser.getCode(), JSON.toJSONString(customUser), CustomConst.LOGIN_KEY_REDIS_TIMEOUT * 60 * 1000);
        response.setHeader(CustomConst.TOKEN, token);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        Result<String> result = Result.failed(ResultCodeEnum.SUCCESS.getCode(), "登录成功", null);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(com.alibaba.fastjson2.JSON.toJSONString(result));
        printWriter.flush();
        printWriter.close();
        // 例如：重定向到主页
//        response.sendRedirect("/home");
    }
}
