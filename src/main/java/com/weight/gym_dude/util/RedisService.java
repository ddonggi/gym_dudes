package com.weight.gym_dude.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * PackageName : com.weight.gym_dude.util
 * FileName : RedisService
 * Author : dglee
 * Create : 2/25/24 12:53 AM
 * Description :
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String,Object> redisTemplate;

    // 데이터 저장
    public void setValues(String key, String data){
        ValueOperations<String,Object> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    // 데이터 저장 기간
    public void setValues(String key, String data, Duration duration){
        ValueOperations<String,Object> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    // 데이터 가져오기
    @Transactional(readOnly = true)
    public String getValues(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if(values.get(key)==null){
            return "false";
        }
        return (String) values.get(key);
    }

    // 데이터 삭제
    public void deleteValues(String key){
        redisTemplate.delete(key);
    }
    public void expireValues(String key, int timeout){
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public void setHashOps(String key, Map<String,String> data){
        HashOperations<String,Object,Object> values=redisTemplate.opsForHash();
        values.putAll(key,data);
    }
    @Transactional(readOnly = true)
    public String getHashOps(String key, String hashKey) {
        HashOperations<String,Object,Object> values=redisTemplate.opsForHash();
        return Boolean.TRUE.equals(values.hasKey(key, hashKey))?
                (String) redisTemplate.opsForHash().get(key, hashKey):"";
    }

    public void deleteHashOps(String key, String hashKey){
        HashOperations<String,Object,Object> values=redisTemplate.opsForHash();
        values.delete(key,hashKey);
    }
    public boolean checkExistsValue(String value){
        return !value.equals("false");
    }
}
