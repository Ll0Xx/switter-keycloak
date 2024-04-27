package com.antont.switterkeycloak.service

import com.antont.switterkeycloak.db.entity.Post
import com.antont.switterkeycloak.web.dto.PostDto

interface PostsService {
    Post getPost(String userId)
    Post createPost(PostDto dto, String userId)
    Post editPost(PostDto dto, String postId, String userId)
    void deletePost(String postId, String userId)
}