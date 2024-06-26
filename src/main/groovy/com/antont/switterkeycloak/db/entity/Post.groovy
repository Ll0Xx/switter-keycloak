package com.antont.switterkeycloak.db.entity

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "posts")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Post {
    @Id
    @Indexed(unique = true)
    String id
    String title
    String content
    String postOwner
    Set<String> favorites = []
    List<Comment> comments = []
}
