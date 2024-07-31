package com.example.customInterface;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class ObjectToJsonConverter  {

    public static String convertToJson(Object object) throws IllegalAccessException {

        //annotation Type 체크
        if(!object.getClass().isAnnotationPresent(ObjectToJson.class)){
            throw new RuntimeException("@ObjectToJson 어노테이션을 찾을 수 없습니다.");
        }

        // reflection 사용
        Class<?> c = object.getClass();
        Map<String, String> jsonElementMap = new LinkedHashMap<>(); // Test용 반환순서를 예측하기 위해 LinkedHashMap사용

        for(Field f : c.getDeclaredFields()){
            f.setAccessible(true); // private 접근제어자에 접근하기 위한 설정
            jsonElementMap.put(f.getName(), (String) f.get(object));
        }

        String jsonString = jsonElementMap.entrySet() // 값이 Map<Key, Value>인 순서가 없고 중복허용이 안되는 entrySet으로 변경
                .stream() // 스트림으로 변환
                .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"") // 해당 기능 적용 후 결과 반환
                .collect(Collectors.joining(",")); // 구분자가 쉼표로 결합
        return "{" + jsonString + "}";
    }

}
