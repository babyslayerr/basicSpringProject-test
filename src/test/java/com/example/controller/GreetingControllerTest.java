package com.example.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class) // Spring연결을 위한 JUnit클래스 SpringRunner(Spring실행)과 함께 사용된다
@SpringBootTest // SpringTest객체를 사용하기 위한 어노테이션 && 컴포넌트 스캔도 실행(컨택스트 로드)
@AutoConfigureMockMvc // MockMvc를 사용하기 위한 설정
public class GreetingControllerTest {

    //SpringTest에서 제공하는 테스트용 객체
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void greetingTest() throws Exception {
        //given
        String nameValue = "Test";

        //when
        //요청 객체
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/greeting")
                .param("name", nameValue));


        //then
        // SpringTest에서 제공하는 결과 매치 확인 정적메소드
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content").value("Hello " + nameValue + "!"));
    }

}
