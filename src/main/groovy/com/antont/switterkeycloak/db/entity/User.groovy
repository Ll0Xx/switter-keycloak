package com.antont.switterkeycloak.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User {
    @Id
    @Indexed(unique = true)
    String id
    @Indexed(unique = true)
    String keycloakId
    @Indexed(unique = true)
    String username
}