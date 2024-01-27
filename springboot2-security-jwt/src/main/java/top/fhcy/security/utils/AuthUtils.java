package top.fhcy.security.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import top.fhcy.security.entity.CustomUser;

import java.util.Objects;

@Getter
@Component
public class AuthUtils {

    private String code;

    private String username;

    private CustomUser customUser;

    public void setUser(CustomUser customUser) {
        if (Objects.isNull(customUser)) {
            return;
        }
        this.code = customUser.getCode();
        this.username = customUser.getUsername();
        this.customUser = customUser;
    }
}
