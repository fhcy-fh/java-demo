package top.fhcy.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 添加值，设置过期时间
     *
     * @param key     key
     * @param value   value
     * @param timeout 毫秒
     */
    public void setValue(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 根据主键获取值
     *
     * @param key key
     * @return value
     */
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据主键删除
     *
     * @param key key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
