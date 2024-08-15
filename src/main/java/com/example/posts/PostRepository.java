package com.example.posts;

import com.example.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Jpa <- Hibernate가 구현하고, springDataJpa도 Jpa를 상속받아 인터페이스를 정의했고 이 SpringDataJpa가 후에 구현체를 제고할 때 내부적으로 hibernate를 사용한다
public interface PostRepository extends JpaRepository<Post, Long> {

    Post save(Post post);

    Optional<Post> findById(Long id);

    // Paging되어있는 List를 가져온다.(Page인터페이스 구현체 PageImpl은 SpringDataJpa가 제공한다)
    // Pageable은 검색 조건 모델, Page는 return 모델이다
    Page<Post> findAll(Pageable pageable);

    // 삭제
    void delete(Post entity);
}
