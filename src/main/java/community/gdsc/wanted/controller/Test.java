package community.gdsc.wanted.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class Test {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/redis")
    public ResponseEntity<Object> testRedis() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        int randomValue = (int)(Math.random() * 300);
        valueOperations.set("test", Integer.toString(randomValue));

        String result = (String)valueOperations.get("test");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
