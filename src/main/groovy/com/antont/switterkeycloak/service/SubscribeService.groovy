package com.antont.switterkeycloak.service

interface SubscribeService {
    List<String> subscribeTo(String userId, String subscribeToId)
    List<String> unsubscribeFrom(String userId, String subscribeToId)
}
