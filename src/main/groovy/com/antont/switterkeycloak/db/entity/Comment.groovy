package com.antont.switterkeycloak.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "comments")
class Comment {
    @Id
    @Indexed(unique = true)
    String id
    String postId
    String author
    String comment
}
