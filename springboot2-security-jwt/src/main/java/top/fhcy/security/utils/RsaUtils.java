package top.fhcy.security.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import top.fhcy.exception.BizException;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class RsaUtils {

    @Value("${rsa.publicKey}")
    private String publicKeyFile;

    @Value("${rsa.privateKey}")
    private String privateKeyFile;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     * 加密算法
     */
    public static final String ENCRYPT_ALGORITHM = "RSA";

    private static final int MAX_ENCRYPT_BLOCK = 2048;
    private static final int MAX_DECRYPT_BLOCK = 2048;

    /**
     * 密钥长度
     */
    private static final int DEFAULT_KEY_SIZE = 2048;

    private static final String KEY_PATH = "./key/";
    private static final String PUBLIC_KEY_NAME = "publicKey.pub";
    private static final String PRIVATE_KEY_NAME = "privateKey.key";

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @return 加密字串
     */
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
            int inputLen = data.getBytes().length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offset > 0) {
                if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
            // 加密后的字符串
            return Base64.encodeBase64String(encryptedData);
        } catch (Exception e) {
            System.out.println("异常处理");
        }
        return null;
    }

    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @return 解密字串
     */
    public String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            byte[] dataBytes = Base64.decodeBase64(data);
            int inputLen = dataBytes.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offset > 0) {
                if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_DECRYPT_BLOCK;
            }
            out.close();
            // 解密后的内容
            return out.toString("UTF-8");
        } catch (Exception e) {
            throw new BizException("密码错误");
        }
    }

    @PostConstruct
    private void createRsaKey() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(publicKeyFile);
        this.privateKey = RsaUtils.getPrivateKey(privateKeyFile);
    }


    // 从文件读取公钥
    private static PublicKey getPublicKey(String fileName) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(fileName));
        keyBytes = Base64.decodeBase64(keyBytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_ALGORITHM);
        return keyFactory.generatePublic(spec);
    }

    // 从文件读取私钥
    private static PrivateKey getPrivateKey(String fileName) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(fileName));
        keyBytes = Base64.decodeBase64(keyBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_ALGORITHM);
        return keyFactory.generatePrivate(spec);
    }

    /**
     * 生成密钥对
     * @throws Exception exception
     */
    private void generate() throws Exception {
        // 生成密钥对
        // 生成RSA密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ENCRYPT_ALGORITHM);
        keyPairGenerator.initialize(DEFAULT_KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 保存公钥和私钥到文件
        saveKeyToFile(PUBLIC_KEY_NAME, publicKey.getEncoded());
        saveKeyToFile(PRIVATE_KEY_NAME, privateKey.getEncoded());
    }

    // 将密钥保存到文件
    private static void saveKeyToFile(String fileName, byte[] keyBytes) throws Exception {
        File dir = new File(KEY_PATH);
        if (!dir.exists()) {
            boolean mkdir = dir.mkdir();
            if (mkdir) {
                System.out.println("创建文件夹成功");
            }
        }
        String path = KEY_PATH + fileName;
        keyBytes = Base64.encodeBase64(keyBytes);
        Files.write(Paths.get(path), keyBytes);
    }

}
