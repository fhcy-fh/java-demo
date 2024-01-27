package top.fhcy.entity;

import lombok.Data;

@Data
public class BaseUser {

    private Long id;

    private String code;

    private String username;

    private String password;

    private Boolean enable;
}
