package com.example.thymeleaf_template;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptUtilsTest {

    @Test
    void generateEncryptedPasswords() {
        encryptAndPrint("dev_secret_key", "template_pwd", "DEV");
        encryptAndPrint("prod_secret_key", "secure_password", "PROD");
    }

    private void encryptAndPrint(String key, String plainText, String env) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(key);
        String encrypted = encryptor.encrypt(plainText);
        System.out.println("==========================================");
        System.out.println("Environment: " + env);
        System.out.println("Key        : " + key);
        System.out.println("Plain      : " + plainText);
        System.out.println("Encrypted  : ENC(" + encrypted + ")");
        System.out.println("==========================================");
    }
}
