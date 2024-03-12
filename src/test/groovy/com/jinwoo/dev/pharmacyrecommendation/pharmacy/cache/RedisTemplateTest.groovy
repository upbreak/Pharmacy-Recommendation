package com.jinwoo.dev.pharmacyrecommendation.pharmacy.cache

import com.jinwoo.dev.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import org.junit.Ignore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SetOperations
import org.springframework.data.redis.core.ValueOperations

@Ignore
class RedisTemplateTest extends AbstractIntegrationContainerBaseTest{

    @Autowired
    private RedisTemplate redisTemplate

    def "RedisTemplate String operations"(){
        given:
        def valueOperations = redisTemplate.opsForValue()
        def key = "stringKey"
        def value = "hello"

        when:
        valueOperations.set(key, value)

        then:
        valueOperations.get(key) == value
    }

    def "RedisTemplate set operations"(){
        given:
        def set = redisTemplate.opsForSet()
        def setKey = "setKey"

        when:
        set.add(setKey, "h", "e", "l", "l", "o")

        then:
        set.size(setKey) == 4
    }

    def "RedisTemplate hash operations"(){
        given:
        def hash = redisTemplate.opsForHash()
        def hashKey = "hashKey"

        when:
        hash.put(hashKey, "subKey", "value")

        then:
        hash.get(hashKey, "subKey") == "value"

        def entries = hash.entries(hashKey)
        entries.keySet().contains("subKey")
        entries.values().contains("value")

        hash.size(hashKey) == entries.size()
    }
}
