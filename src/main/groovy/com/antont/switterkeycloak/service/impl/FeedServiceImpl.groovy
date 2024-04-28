package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.db.entity.Post
import com.antont.switterkeycloak.db.repository.PostsRepository
import com.antont.switterkeycloak.db.repository.UsersRepository
import com.antont.switterkeycloak.service.FeedService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FeedServiceImpl implements FeedService {

    private Logger LOGGER = LoggerFactory.getLogger(FeedService.class)

    private final PostsRepository postsRepository
    private final UsersRepository usersRepository

    FeedServiceImpl(PostsRepository postsRepository, UsersRepository usersRepository) {
        this.postsRepository = postsRepository
        this.usersRepository = usersRepository
    }

    @Override
    List<Post> getUsersFeedByKeycloakId(String userId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }

            postsRepository.findAllByPostOwner(user.id)
        } catch (Exception e) {
            LOGGER.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Override
    List<Post> getUsersFeedByUserId(String userId) {
        try {
            postsRepository.findAllByPostOwner(userId)
        } catch (Exception e) {
            def message = "Failed to get feed for user with id: ${userId}"
            LOGGER.error(message)
            throw new RuntimeException(message, e)
        }
    }
}
