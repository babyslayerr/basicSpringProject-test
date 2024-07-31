package com.example.posts;

import com.example.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // 트랜잭션(한 업무단위)의 영역이 되는 서비스 영억
@RequiredArgsConstructor // 레파지토리 구현(의존성)을 주입받기 위함
public class PostService {

    private final PostRepository postRepository;

    /***
     * 아이디(PK)를 이용해서 포스트를 찾는다
     * @param id
     * @return com.example.posts.Post
     */
    public Post findPostById(Long id){
        // Optional 객체로 해당id에 해당하는 데이터가 없는 경우 에러를 반환
        return postRepository.findById(id).orElseThrow(()->new RuntimeException("객체를 찾을 수 없습니다."));
    }

    /***
     * 저장
     * @Param Post
     * @return
     */
    public Post savePost(Post post){
        return postRepository.save(post);
    }
}
