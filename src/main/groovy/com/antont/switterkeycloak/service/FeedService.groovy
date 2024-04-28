package com.antont.switterkeycloak.service

import com.antont.switterkeycloak.db.entity.Post

interface FeedService {
    List<Post> getUsersFeedByKeycloakId(String userId)
    List<Post> getUsersFeedByUserId(String userId)
}
