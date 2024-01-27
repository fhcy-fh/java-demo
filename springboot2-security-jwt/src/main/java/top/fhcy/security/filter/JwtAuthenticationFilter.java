package top.fhcy.security.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.fhcy.enums.ResultCodeEnum;
import top.fhcy.exception.BizException;
import top.fhcy.security.consts.CustomConst;
import top.fhcy.security.entity.CustomUser;
import top.fhcy.security.utils.AuthUtils;
import top.fhcy.security.utils.JwtUtils;
import top.fhcy.security.utils.RsaUtils;
import top.fhcy.utils.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RsaUtils rsaUtil;

    @Resource
    private AuthUtils authUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String token = request.getHeader(CustomConst.TOKEN);
        try {
            if (!StringUtils.isEmpty(token)) {
                CustomUser customUser = jwtUtils.getUserVoFromToken(token, rsaUtil.getPublicKey());
                if (Objects.isNull(customUser)) {
                    throw new BizException("登录失效，请重新登录");
                }
                String customVoJson = redisUtils.getValue(CustomConst.LOGIN_KEY + customUser.getCode());
                if (StringUtils.isBlank(customVoJson)) {
                    throw new BizException("登录失效，请重新登录");
                }
                customUser = JSON.parseObject(customVoJson, CustomUser.class);
                if (StringUtils.isBlank(customUser.getCode())) {
                    throw new BizException("登录失效，请重新登录");
                }
                authUtils.setUser(customUser);

                // 如果可以正确的从JWT中提取用户信息，并且该用户未被授权
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(customUser.getUsername());
                    if (jwtUtils.validateToken(token, userDetails, rsaUtil.getPublicKey())) {
                        // 给使用该JWT令牌的用户进行授权
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        // 交给spring security管理,在之后的过滤器中不会再被拦截进行二次授权了
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
                redisUtils.setValue(CustomConst.LOGIN_KEY + customUser.getCode(), JSON.toJSONString(customUser), CustomConst.LOGIN_KEY_REDIS_TIMEOUT * 60 * 1000);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            BizException bizException = new BizException(ResultCodeEnum.LOGIN_OVERDUE.getCode(), "登录失效，请重新登录");
            response.getWriter().write(new ObjectMapper().writeValueAsString(bizException));
        }
    }
}
