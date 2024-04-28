package com.antont.switterkeycloak.service

import com.antont.switterkeycloak.db.entity.Comment
import com.antont.switterkeycloak.db.entity.Post
import com.antont.switterkeycloak.web.dto.CommentDto
import com.antont.switterkeycloak.web.dto.PostDto

interface PostsService {
    Post getPost(String userId)
    Post createPost(PostDto dto, String userId)
    Post editPost(PostDto dto, String postId, String userId)
    Post addToFavorite(String postId, String userId)
    Post removeFromFavorite(String postId, String userId)
    String addComment(String postId, CommentDto commentDto, String userId)
    List<Comment> getComments(String postId)
    void deletePost(String postId, String userId)
}