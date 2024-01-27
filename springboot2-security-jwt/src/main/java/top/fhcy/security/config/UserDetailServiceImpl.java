package top.fhcy.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import top.fhcy.entity.BaseUser;
import top.fhcy.security.entity.CustomUser;
import top.fhcy.security.entity.CustomUserDetails;
import top.fhcy.service.BaseUserService;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private BaseUserService baseUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser user = baseUserService.getByUsername(username);
        log.info("查询用户");
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        CustomUser customUser = new CustomUser();
        customUser.setCode(user.getCode());
        customUser.setUsername(user.getUsername());
        customUser.setPassword(user.getPassword());
        customUser.setEnable(user.getEnable());

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setCustomUser(customUser);
//        customUserDetails.setAuthorityList();
        return customUserDetails;
    }
}
