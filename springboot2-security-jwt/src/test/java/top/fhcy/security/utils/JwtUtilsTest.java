package top.fhcy.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.fhcy.security.entity.CustomUser;
import top.fhcy.utils.CodeUtils;

import javax.annotation.Resource;

@SpringBootTest
class JwtUtilsTest {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RsaUtils rsaUtils;

    @Test
    public void test() {
        CustomUser customUser = new CustomUser();
        customUser.setCode(CodeUtils.getUuid());
        customUser.setUsername("username123");
        String token = jwtUtils.generateToken(customUser, rsaUtils.getPrivateKey());
        CustomUser userVoFromToken = jwtUtils.getUserVoFromToken(token, rsaUtils.getPublicKey());
        assert StringUtils.equals(customUser.getCode(), userVoFromToken.getCode());
        assert StringUtils.equals(customUser.getUsername(), userVoFromToken.getUsername());
        String usernameFromToken = jwtUtils.getUsernameFromToken(token, rsaUtils.getPublicKey());
        assert StringUtils.equals(customUser.getUsername(), usernameFromToken);

        Boolean tokenExpired = jwtUtils.isTokenExpired(token, rsaUtils.getPublicKey());
        assert !tokenExpired;

        String newToken = jwtUtils.refreshToken(token, rsaUtils.getPublicKey(), rsaUtils.getPrivateKey());
        CustomUser newTokenUser = jwtUtils.getUserVoFromToken(newToken, rsaUtils.getPublicKey());
        assert StringUtils.equals(customUser.getCode(), newTokenUser.getCode());
        assert StringUtils.equals(customUser.getUsername(), newTokenUser.getUsername());
    }

}