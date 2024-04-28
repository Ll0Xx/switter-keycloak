package com.antont.switterkeycloak.db.repository

import com.antont.switterkeycloak.db.entity.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface PostsRepository extends MongoRepository<Post, String> {
    Optional<Post> findByIdAndPostOwner(String postId, String user)
    List<Post> findAllByPostOwner(String user)
}
