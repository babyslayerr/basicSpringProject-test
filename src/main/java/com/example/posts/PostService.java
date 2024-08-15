package com.example.posts;

import com.example.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional // 업무 저장단위 지정, 업무 별로 이상시 롤백
    public Post savePost(Post post){
        return postRepository.save(post);
    }

    /***
     * Post 다중 조회
     * 10건씩 검색
     * @Param page 페이지
     */
    public Page<Post> findPostList(int page){
        // 검색 객체를 만드는 PageRequest는 pageable을 구현한 구현체이다.
        // page는 0-based-index이다(0부터 시작)
        Pageable pageable = PageRequest.of(page, 10);
        return postRepository.findAll(pageable);
    }

    public void removePost(Long id){
        // Id 값을 받아와 없으면 찾음 없으면 에러 던짐
        postRepository.delete(postRepository.findById(id).orElseThrow());
    }
}
