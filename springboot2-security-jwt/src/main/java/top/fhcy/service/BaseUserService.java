package top.fhcy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.fhcy.entity.BaseUser;
import top.fhcy.mapper.BaseUserMapper;

/**
 * @author fh
 * @date 2024/1/19
 */
@Service
public class BaseUserService {

    private final BaseUserMapper baseUserMapper;

    public BaseUserService(BaseUserMapper baseUserMapper) {
        this.baseUserMapper = baseUserMapper;
    }

    public BaseUser getByUsername(String username) {
        return baseUserMapper.getByUsername(username);
    }

}
