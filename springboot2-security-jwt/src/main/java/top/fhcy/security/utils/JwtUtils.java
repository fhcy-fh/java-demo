package top.fhcy.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import top.fhcy.security.entity.CustomUser;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Configuration
public class JwtUtils {

    /**
     * 过期时间
     */
    @Value("${jwt.expire}")
    public String expire;

    /**
     * 生成token令牌
     *
     * @param customUser 用户
     * @return token令牌
     */
    public String generateToken(CustomUser customUser, PrivateKey privateKey) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("userCode", customUser.getCode());
        claims.put("username", customUser.getUsername());
        return this.generateToken(claims, privateKey);
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token, PublicKey publicKey) {
        String username;
        try {
            Claims claims = this.getClaimsFromToken(token, publicKey);
            username = String.valueOf(claims.get("username"));
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public CustomUser getUserVoFromToken(String token, PublicKey publicKey) {
        CustomUser customUser = new CustomUser();
        try {
            Claims claims = this.getClaimsFromToken(token, publicKey);
            customUser.setCode(String.valueOf(claims.get("userCode")));
            customUser.setUsername(String.valueOf(claims.get("username")));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return customUser;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token, PublicKey publicKey) {
        try {
            Claims claims = this.getClaimsFromToken(token, publicKey);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token, PublicKey publicKey, PrivateKey privateKey) {
        String refreshedToken;
        try {
            Claims claims = this.getClaimsFromToken(token, publicKey);
            refreshedToken = this.generateToken(claims, privateKey);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails, PublicKey publicKey) {
        String username = this.getUsernameFromToken(token, publicKey);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, publicKey));
    }

//    下方私有方法区


    /**
     * 生成令牌
     *
     * @param claims 数据声明
     * @return token令牌
     */
    private String generateToken(Map<String, Object> claims, PrivateKey privateKey) {
        Date date = new Date(System.currentTimeMillis());
        return Jwts.builder().setClaims(claims)
                .setId(createJTI())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + Integer.parseInt(expire)))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token, PublicKey publicKey) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

}
