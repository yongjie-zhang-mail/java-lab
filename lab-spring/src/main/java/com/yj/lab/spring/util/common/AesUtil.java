package com.yj.lab.spring.util.common;

import com.yj.lab.spring.config.exception.BusinessException;
import com.yj.lab.spring.model.enums.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;

/**
 * AES加密解密工具类
 */
@Slf4j
public class AesUtil {

    private static final String ENCRYPT_ALG = "AES";
    private static final String AES_ECB_PKCS7_PADDING = "AES/ECB/PKCS7Padding";

    /**
     * 密钥key
     */
    public static class Keys {
        /**
         * 用户会员信息查询 加密key
         */
        public static final String USER_KEY = "aes%user";
    }

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * 加密
     *
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String key) throws Exception {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ENCRYPT_ALG);
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS7_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ReturnCode.FAIL.getCode(),
                    "AES加密失败：content=" + content + " key=" + key);
        }
    }

    /**
     * 解密
     */
    public static String decrypt(String content, String key) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ENCRYPT_ALG);
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS7_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // 先用base64解密
            byte[] encrypted = new Base64().decode(content);
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("=========AES解密失败============={}", e.getMessage());
            throw new BusinessException(ReturnCode.FAIL.getCode(),
                    "解密失败" + e.fillInStackTrace());
        }
    }

    private AesUtil() {
        throw new IllegalStateException("Utility class");
    }



}
