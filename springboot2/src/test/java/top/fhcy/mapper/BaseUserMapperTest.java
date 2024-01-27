package top.fhcy.mapper;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.fhcy.entity.BaseUser;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class BaseUserMapperTest {

    @Resource
    private BaseUserMapper baseUserMapper;

    @Test
    public void test() {
        List<BaseUser> baseUsers = baseUserMapper.selectList(null);
        System.out.println(JSON.toJSONString(baseUsers));
    }

}