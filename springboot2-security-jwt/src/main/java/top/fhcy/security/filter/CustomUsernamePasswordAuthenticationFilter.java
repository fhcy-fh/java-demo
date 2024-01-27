package top.fhcy.security.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.fhcy.security.utils.RsaUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fh
 * @date 2024/1/20
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {

    @Resource
    private RsaUtils rsaUtils;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从请求中提取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = rsaUtils.decrypt(password);

        // 创建认证请求
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // 使用AuthenticationManager进行身份验证
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
