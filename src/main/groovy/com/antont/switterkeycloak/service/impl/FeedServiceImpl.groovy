package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.db.entity.Post
import com.antont.switterkeycloak.db.entity.User
import com.antont.switterkeycloak.db.repository.PostsRepository
import com.antont.switterkeycloak.db.repository.SubscriptionRepository
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
    private final SubscriptionRepository subscriptionRepository

    FeedServiceImpl(PostsRepository postsRepository, UsersRepository usersRepository, SubscriptionRepository subscriptionRepository) {
        this.postsRepository = postsRepository
        this.usersRepository = usersRepository
        this.subscriptionRepository = subscriptionRepository
    }

    @Override
    List<Post> getUsersFeedByKeycloakId(String userId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }

            getAllPostByUserSubscription(user)
        } catch (Exception e) {
            LOGGER.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Override
    List<Post> getUserFeed(String userId) {
        try {
            def user = usersRepository.findByUsername(userId).orElseGet {
                usersRepository.findById(userId).orElseThrow {
                    throw new RuntimeException("The user with the username or ID whose feed you want to view could not be found")
                }
            }

            getAllPostByUserSubscription(user)
        } catch (Exception e) {
            def message = "Failed to get feed for user with id: ${userId}"
            LOGGER.error(message)
            throw new RuntimeException(message, e)
        }
    }

    private List<Post> getAllPostByUserSubscription(User user) {
        def subscription = subscriptionRepository.findByUserId(user.id).orElseThrow {
            throw new RuntimeException("This user is not currently subscribed to anyone")
        }

        postsRepository.findAllByPostOwnerIn(subscription.subscribedTo.asList())
    }
}
