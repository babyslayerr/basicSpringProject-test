package com.example.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
     */
    @PostMapping("/api/post")
    public void postPost(@ModelAttribute Post post) { // model과 mapping시키는 어노테이션
        postService.savePost(post);
    }
}
