package com.antont.switterkeycloak.db.repository

import com.antont.switterkeycloak.db.entity.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface UsersRepository extends MongoRepository<User, String> {
    Optional<User> findByKeycloakId(String keycloakId)
    Optional<User> findByUsername(String username)
}
