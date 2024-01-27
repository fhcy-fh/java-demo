package top.fhcy.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RsaUtilsTest {

    @Resource
    private RsaUtils rsaUtils;

    /**
     * 生成密钥对
     * @throws Exception Exception
     */
    @Test
    void generate() throws Exception {
//        rsaUtils.generate();

    }

    /**
     * 测试加密解密
     */
    @Test
    void test() {
        String str = "123";
        String kk = rsaUtils.encrypt(str);
        System.out.println(kk);
        String res = rsaUtils.decrypt(kk);
        System.out.println(res);
        assert StringUtils.equals(str, res);

    }
}