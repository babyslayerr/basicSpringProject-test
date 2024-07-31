package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    //  자동 id 증가를 위한 유틸객체
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    // queryString으로 파라미터 받음
    public Map greeting(@RequestParam(value="name", defaultValue = "World")String name){
        
        // 객체를 만드는 대신 Map으로 대체
        Map<String , String> result = new HashMap<>();
        result.put("id", String.valueOf(counter.incrementAndGet()));
        result.put("content",String.format("Hello %s!", name));
        return result;
    }

}
