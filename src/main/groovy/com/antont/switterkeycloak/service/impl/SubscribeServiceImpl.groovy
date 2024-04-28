package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.db.entity.Subscription
import com.antont.switterkeycloak.db.repository.SubscriptionRepository
import com.antont.switterkeycloak.db.repository.UsersRepository
import com.antont.switterkeycloak.service.PostsService
import com.antont.switterkeycloak.service.SubscribeService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SubscribeServiceImpl implements SubscribeService {

    private Logger LOGGER = LoggerFactory.getLogger(PostsService.class)

    private final SubscriptionRepository subscriptionRepository
    private final UsersRepository usersRepository

    SubscribeServiceImpl(SubscriptionRepository subscriptionRepository, UsersRepository usersRepository) {
        this.subscriptionRepository = subscriptionRepository
        this.usersRepository = usersRepository
    }

    @Override
    List<String> subscribeTo(String userId, String subscribeToId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }

            def subscribeToUser = usersRepository.findByUsername(subscribeToId).orElseGet {
                usersRepository.findById(subscribeToId).orElseThrow {
                    throw new RuntimeException("Could not find the user with the username or ID you want to subscribe to")
                }
            }

            if (user.id == subscribeToUser.id) {
                throw new RuntimeException("You cannot subscribe to yourself")
            }

            def subscription = subscriptionRepository.findByUserId(user.id).orElse(new Subscription())
            subscription.userId = user.id
            subscription.subscribedTo.add(subscribeToUser.id)
            subscriptionRepository.save(subscription).subscribedTo.asList()
        } catch (Exception e) {
            LOGGER.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Override
    List<String> unsubscribeFrom(String userId, String subscribeToId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }

            def unsubscribeFromUser = usersRepository.findByUsername(subscribeToId).orElseGet {
                usersRepository.findById(subscribeToId).orElseThrow {
                    throw new RuntimeException("Could not find the user with the username or ID you want to unsubscribe from")
                }
            }

            def subscription = subscriptionRepository.findByUserId(user.id).orElse(new Subscription())
            subscription.userId = user.id
            subscription.subscribedTo.remove(unsubscribeFromUser.id)
            subscriptionRepository.save(subscription).subscribedTo.asList()
        } catch (Exception e) {
            LOGGER.error(e.message)
            throw new RuntimeException(e.message)
        }
    }
}
