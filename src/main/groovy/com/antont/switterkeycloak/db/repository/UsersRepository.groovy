package com.antont.switterkeycloak.db.repository

import com.antont.switterkeycloak.db.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UsersRepository extends MongoRepository<User, String>{
    Optional<User> findByKeycloakId(String keycloakId)
}
