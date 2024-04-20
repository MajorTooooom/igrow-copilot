package com.dororo.future.igrowcopilot.util;

import cn.hutool.core.util.HexUtil;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * 固定长度ID生成工具
 */
public class IdUtils {
    private final static String explicitSalt = "aaa1234567890bbb";

    /**
     * 将任意长度的字符串转换为固定长度的ID
     */
    public static String getFixedLengthId(String before) {
        String hexStr = null;
        try {
            // 秘钥派生函数
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            // [迭代次数=65536][密钥长度=256]
            PBEKeySpec spec = new PBEKeySpec(before.toCharArray(), explicitSalt.getBytes(), 65536, 256);
            SecretKey secretKey = factory.generateSecret(spec);
            // 生成固定长度ID
            byte[] secretKeyBytes = secretKey.getEncoded();
            hexStr = HexUtil.encodeHexStr(secretKeyBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error generating fixed length ID", e);
        }
        return hexStr;
    }
}
