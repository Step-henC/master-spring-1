package com.exam.rest.webservices.restfulwebservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exam.rest.webservices.restfulwebservice.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{


    @Query(value = "SELECT * FROM Post p WHERE p.USER_ID = :userId AND p.ID = :postId", nativeQuery = true )
    Post findPostByUserId(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
