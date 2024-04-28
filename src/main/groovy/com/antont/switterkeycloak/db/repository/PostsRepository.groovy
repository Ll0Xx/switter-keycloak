package com.antont.switterkeycloak.db.repository

import com.antont.switterkeycloak.db.entity.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostsRepository extends MongoRepository<Post, String> {
    Optional<Post> findByIdAndPostOwner(String postId, String user)
    List<Post> findAllByPostOwner(String user)
}
