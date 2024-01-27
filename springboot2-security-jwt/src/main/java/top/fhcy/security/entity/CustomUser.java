package top.fhcy.security.entity;

import lombok.Data;

@Data
public class CustomUser {

    private String code;

    private String username;

    private String password;

    private Boolean enable;
}
