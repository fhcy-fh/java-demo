package top.fhcy.mapper;

import org.springframework.stereotype.Component;
import top.fhcy.entity.BaseUser;
import top.fhcy.utils.CodeUtils;

/**
 * @author fh
 * @date 2024/1/19
 */
@Component
public class BaseUserMapper {

    public BaseUser getByUsername(String username) {

        BaseUser baseUser = new BaseUser();
        baseUser.setId(1L);
        baseUser.setCode(CodeUtils.getUuid());
        baseUser.setUsername("test");
        baseUser.setPassword("$2a$10$ECpuRjAYAI9ihspY/QwU1uf73S2GjpaydwgnNq/r9BcPI0jSO./iq");
        baseUser.setEnable(true);
        return baseUser;
    }
}
