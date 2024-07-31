package com.example.posts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class) // Spring연결을위한 JUnit(SpringRunner.class)와 함께사용
@SpringBootTest // 스프링 컴포넌트 스캔및 테스트객체를 사용하기 위한 설정
@AutoConfigureMockMvc // MockMvc객체를 구현받기위한 설정
public class PostControllerTest {

    // 컨트롤러를 테스트하기위한 목객체
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository; // 검색전 데이터를 저장하기 위함


    @Test
    public void getPostTest() throws Exception {
        //given

        // post 저장
        Post post = Post.builder()
                .id(1L)
                .subject("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        // Mock Request 객체
        MockHttpServletRequestBuilder mock = MockMvcRequestBuilders.get("/api/post/1");

        //when
        // 실행 후 result 객체 생성
        ResultActions result = mockMvc.perform(mock);

        //then
        // status code가 200이고 json으로 된 body에 key가 subject, content 의 내용 검증
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("subject").value("제목"))
                .andExpect(MockMvcResultMatchers.jsonPath("content").value("내용"))
                ;
    }

    // post 저장 테스트
    @Test
    public void postPostTest() throws Exception {
        // given
        Map<String,String> person = new HashMap<>();
        person.put("id", "1");
        person.put("subject", "제목");
        person.put("content", "내용");


        // when
        ResultActions postResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                .content(person.toString()));

        // then (return값은 없기 때문에 http상태코드만)
        postResult.andExpect(MockMvcResultMatchers.status().isOk());

        // 확인
        ResultActions getResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/post/1"));
        getResult.andExpect(MockMvcResultMatchers.jsonPath("id").value("1"));
    }
}
