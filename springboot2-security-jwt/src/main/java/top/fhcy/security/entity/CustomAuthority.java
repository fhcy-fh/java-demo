package top.fhcy.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class CustomAuthority implements GrantedAuthority {

    private CustomRole customRole;

    @Override
    public String getAuthority() {
        return customRole.getCode();
    }
}
