package com.example.posts;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
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

        person.put("subject", "제목");
        person.put("content", "내용");


        // when
        ResultActions postResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/post").contentType("application/json")
                .content("{\"subject\" : \"" + person.get("subject") + "\" , \"content\" : \"" + person.get("content") +"\"}"));

        // then (return값은 없기 때문에 http상태코드만)
        postResult.andExpect(MockMvcResultMatchers.status().isOk());

        // 확인
        ResultActions getResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/post/1"));
        getResult.andExpect(MockMvcResultMatchers.jsonPath("id").value("1"));
    }

    @Test
    public void getPostList() throws Exception {
        // given
        // 가져올 List 객체들 생성
        Post post1 = Post.builder()
                .subject("글 제목1")
                .content("글 내용1")
                .build();
        Post post2 = Post.builder()
                .subject("글 제목2")
                .content("글 내용2")
                .build();

        // when
        // 저장
        postRepository.save(post1);
        postRepository.save(post2);

        // api 테스트 실행 및 결과 가져올 객체
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/getPostList")
                .param("page","0") // 가져올 page(쪽)
        );


        // then
        result.andExpect(
                // 가져온 리스트 수가 2가 되야함
                MockMvcResultMatchers.jsonPath("$.totalElements").value(2)
        );
    }

    @Test
    public void deletePostTest() throws Exception {
        // given
        // 삭제될 객체
        Post post = Post.builder()
                .subject("글 내용")
                .content("글 제목")
                .build();

        // when
        // 저장
        Post savedPost = postRepository.save(post);

        // 결과값 가져오기
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/post")
                .param("id", String.valueOf(savedPost.getId())));

        // then
        // 상태코드 200
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        // ID로 찾은 값이 null
        Assertions.assertNull(postRepository.findById(1L).orElse(null));
    }
}
