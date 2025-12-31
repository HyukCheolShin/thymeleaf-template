package com.example.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisCookieTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testCookieSerializerIsConfigured() throws NoSuchFieldException, IllegalAccessException {
        CookieSerializer cookieSerializer = applicationContext.getBean(CookieSerializer.class);
        assertThat(cookieSerializer).isNotNull();
        assertThat(cookieSerializer).isInstanceOf(DefaultCookieSerializer.class);

        DefaultCookieSerializer defaultCookieSerializer = (DefaultCookieSerializer) cookieSerializer;
        
        Field cookiePathField = DefaultCookieSerializer.class.getDeclaredField("cookiePath");
        cookiePathField.setAccessible(true);
        String cookiePath = (String) cookiePathField.get(defaultCookieSerializer);
        
        assertThat(cookiePath).isEqualTo("/");
    }
}
