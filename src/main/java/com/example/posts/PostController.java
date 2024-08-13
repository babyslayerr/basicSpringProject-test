package com.example.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 단일 post에 관한 정보를 가져온다
     * @param id
     * @return
     */
    @GetMapping("/api/post/{id}") // 해당 url에 관한 api를 작성한다.
    public Post getPost(@PathVariable("id") Long id){ // @PathVariable은 path경로의 이름이 value인 값을 어노테이션이 붙어있는 필드에 매핑시킨다.(기본 어노테이션 value는 value이며 생략했다.)
        return postService.findPostById(id);
    }

    /**
     * 단일 post에 대해 저장한다.
     * @ModelAttribute 폼데이터(key=value)를 받아 객체로 매핑
     * @RequestBody 는 json으로 받아서 객체로 매핑(content-type -> application/json)
     * 기본 생성자 생성후 body는 리플렉션을 통한 접근 model은 setter를 통해 접근
     * 가끔 @ModelAttribute가 key:value형식일때도 받아질 때도있는데 content-type이 form-data면 받아진다(잘못된거임)
     */
    @PostMapping("/api/post")
    public void postPost(@RequestBody Post post) { // model과 mapping시키는 어노테이션
        postService.savePost(post);
    }
}
