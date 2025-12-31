package com.example.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisConnection() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = "test:key";
        String value = "hello redis";

        ops.set(key, value);
        String retrievedValue = ops.get(key);

        assertThat(retrievedValue).isEqualTo(value);

        // Clean up
        stringRedisTemplate.delete(key);
    }
}
