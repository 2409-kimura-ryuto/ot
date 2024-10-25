package com.example.ot.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CipherUtil {
    // 暗号化キー（16バイトのランダムなキーを生成するか、事前に設定する）
    private static final String SECRET_KEY = "1234567890123456"; // 16文字のキー（例）

    // 暗号化メソッド
    public static String encrypt(String plainText) throws Exception {
        // AES暗号化アルゴリズムの設定
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        // 暗号化モードを設定
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // テキストを暗号化
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        // Base64エンコードして文字列として返す
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
