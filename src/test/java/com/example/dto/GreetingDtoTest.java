package com.example.dto;

import com.example.customInterface.ObjectToJsonConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreetingDtoTest {

    @Test
    public void greetingDtoLombokTest(){

        // given
        String id = "1";
        String name = "Greeting";

        // when
        GreetingDto dto = new GreetingDto(id,name); // GreetingDto를 import하지 않는 이유는 IDE가 인식하고 자동으로 추가하기 때문

        // then jUnit에서 제공하는 검증클래스메소드 Assert(주장하다)의 메소드
        // 정적메소드로만 표기시 import 구문에 클래스.정적메소드 와 같은 형식으로 클래스와 함께 정적메소드까지 같이 작성
        assertEquals(dto.getId(), id);
        assertEquals(dto.getName(), name);
    }

    /**
     * customAnnotation Test
     * simple object to json
     * @throws IllegalAccessException
     */
    @Test
    public void customAnnotationDtoTest() throws IllegalAccessException {
        // given
        GreetingDto dto = new GreetingDto("5", "fender");

        // when
        String jsonString = ObjectToJsonConverter.convertToJson(dto); // Runtime에서 활용가능

        // then
        assertEquals("{\"id\":\"5\",\"name\":\"fender\"}" ,jsonString);
    }

}
