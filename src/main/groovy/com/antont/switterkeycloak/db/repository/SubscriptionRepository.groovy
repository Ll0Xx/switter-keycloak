package com.antont.switterkeycloak.db.repository

import com.antont.switterkeycloak.db.entity.Subscription
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface SubscriptionRepository extends MongoRepository<Subscription, String>{
    Optional<Subscription> findByUserId(String userid)
}
