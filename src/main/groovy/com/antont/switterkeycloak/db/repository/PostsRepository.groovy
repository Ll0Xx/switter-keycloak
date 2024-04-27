package com.antont.switterkeycloak.db.repository

import com.antont.switterkeycloak.db.entity.Post
import com.antont.switterkeycloak.db.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface PostsRepository extends MongoRepository<Post, String> {
    Optional<Post> findByIdAndPostOwner(String postId, User user)
}
