package com.example.template.config;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "jasypt.encryptor.password=test"
})
class JasyptConfigTest {

    @Autowired
    @Qualifier("jasyptStringEncryptor")
    private StringEncryptor jasyptStringEncryptor;

    @Test
    void stringEncryptorShouldBeLoaded() {
        assertThat(jasyptStringEncryptor).isNotNull();
    }

    @Test
    void encryptAndDecryptTest() {
        String plainText = "1234";

        // 암호화
        String encryptedContext = jasyptStringEncryptor.encrypt(plainText);

        // 복호화
        String decryptedContext = jasyptStringEncryptor.decrypt(encryptedContext);

        System.out.println("Plain Text: " + plainText);
        System.out.println("Encrypted Context: " + encryptedContext);
        System.out.println("Decrypted Context: " + decryptedContext);

        assertThat(decryptedContext).isEqualTo(plainText);
    }
}
