package top.fhcy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("base_user")
public class BaseUser {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String username;

    private String password;

    private Boolean enable;
}
