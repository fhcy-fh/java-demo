package top.fhcy.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RedisUtilsTest {

    @Resource
    private RedisUtils redisUtils;

    @Test
    public void test() {
        String key = "key1";
        String value = "value1";

        redisUtils.setValue(key, value);
        String value1 = redisUtils.getValue(key);
        assert StringUtils.equals(value, value1);

    }

}