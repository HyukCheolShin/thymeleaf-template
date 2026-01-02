package com.example.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.Socket;

@SpringBootApplication
public class ThymeleafTemplateApplication {

	public static void main(String[] args) {
		if (isRedisRunning()) {
			System.setProperty("spring.session.store-type", "redis");
			System.out.println("Redis(6379) 확인됨: Redis 세션을 사용합니다.");
		} else {
			System.setProperty("spring.session.store-type", "none");
			// Redis 자동 설정을 아예 제외시켜서 연결 시도를 원천 차단
			System.setProperty("spring.autoconfigure.exclude",
					"org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration," +
							"org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration");
			System.out.println("Redis 연결 실패: 기본(Tomcat) 세션을 사용하며, Redis 설정을 제외합니다.");
		}
		SpringApplication.run(ThymeleafTemplateApplication.class, args);
	}

	private static boolean isRedisRunning() {
		try (Socket socket = new Socket("localhost", 6379)) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
