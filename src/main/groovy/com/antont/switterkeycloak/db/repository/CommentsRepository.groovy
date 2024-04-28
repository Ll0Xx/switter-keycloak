package com.antont.switterkeycloak.db.repository

import com.antont.switterkeycloak.db.entity.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentsRepository extends MongoRepository<Comment, String>{
    List<Comment> findAllByPostId(String postId)
}
