package kr.co.aia.ipro.common.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

public class JasyptTest {

    @Test
    public void encryptTest() {
        String password = "test"; // Jasypt Key
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String[] plainTexts = {
            "jdbc:postgresql://localhost:5432/postgres", // Primary URL
            "postgres", // Username
            "1234", // Password
            "jdbc:postgresql://localhost:5433/postgres" // Secondary URL (Username/PW 동일)
        };

        for (String plainText : plainTexts) {
            String encryptedText = encryptor.encrypt(plainText);
            System.out.println("Plain: " + plainText + " -> ENC(" + encryptedText + ")");
        }
    }
}
