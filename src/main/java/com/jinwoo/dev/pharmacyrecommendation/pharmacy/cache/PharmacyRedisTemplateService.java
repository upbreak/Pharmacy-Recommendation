package com.jinwoo.dev.pharmacyrecommendation.pharmacy.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRedisTemplateService {

    private static final String CACHE_KEY = "PHARMACY";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper mapper;

    private HashOperations<String , String, String> hashOperations;

    @PostConstruct
    public void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(PharmacyDto pharmacyDto){
        if(Objects.isNull(pharmacyDto) || Objects.isNull(pharmacyDto.id())){
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY, pharmacyDto.id().toString(), serializePharmacyDto(pharmacyDto));
            log.info("[PharmacyRedisTemplateService save success] id: ", pharmacyDto.id());
        } catch (JsonProcessingException e) {
            log.error("[PharmacyRedisTemplateService save error] {}", e.getMessage());
        }
    }

    public List<PharmacyDto> findAll(){
        try {
            List<PharmacyDto> list = new ArrayList<>();
            for(String value : hashOperations.entries(CACHE_KEY).values()){
                list.add(deserializePharmacyDto(value));
            }
            log.info("[PharmacyRedisTemplateService findAll success]");
            return list;
        }catch (JsonProcessingException e){
            log.error("[PharmacyRedisTemplateService findAll error] {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private String serializePharmacyDto(PharmacyDto pharmacyDto) throws JsonProcessingException {
        return mapper.writeValueAsString(pharmacyDto);
    }

    private PharmacyDto deserializePharmacyDto(String value) throws JsonProcessingException {
        return mapper.readValue(value, PharmacyDto.class);
    }
}
