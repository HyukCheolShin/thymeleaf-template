package com.example.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Key는 String으로 직렬화
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Value는 기본적으로 JdkSerializationRedisSerializer 사용 (필요에 따라 변경 가능)
        // 여기서는 예시로 StringSerializer를 사용하거나 Jackson2JsonRedisSerializer 등을 고려할 수 있음
        // 일단 기본 설정을 유지하되 키만 명시적으로 설정

        return template;
    }

    @Bean
    public org.springframework.session.web.http.CookieSerializer cookieSerializer() {
        org.springframework.session.web.http.DefaultCookieSerializer serializer = new org.springframework.session.web.http.DefaultCookieSerializer();
        serializer.setCookieName("SESSION");
        serializer.setCookiePath("/");
        return serializer;
    }
}
