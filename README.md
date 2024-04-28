# Switter - API for an analog of Twitter
Switter is built using SpringBoot framework written in groovy, uses mongodb as local database, uses keycloak as authentication server.
## 1. How to run the project locally
  **1.1 Download the project and open it with Intellij Idea**
  
  **1.2 Start all necessary services to run the following command in IDE terminal:**
```
> cd .\docker-compose\ 
> docker-compose up
```
Make sure all services are running correctly

**1.3 Run SpringBoot application**
**1.4 Done! You can use postman or other similar application for testing**
## 2. How to run the tests locally
  **2.1 Start all necessary services to run the following command in IDE terminal:**
```
> cd .\src\test\resources\ 
> docker-compose up
```
Make sure all services are running correctly

**2.2 Run test**
## 3. Endpoints mapping
**3.1 Create user**
```
POST http://localhost:8080/user
```
with body 
```
{
    "username":"<USERNAME>",
    "password": "<PASSWORD>",
    "passwordConfirmation":"<PASSWORD>"
}
```
**3.2 Login**
```
POST http://localhost:8080/auth/login
```
with body 
```
{
    "username":"<USERNAME>",
    "password": "<PASSWORD>"    
}
```
You will get a json with an access_token that can be used to gain access to secure endpoints

**3.3 Update user**
```
PATCH http://localhost:8080/user
```
with body and authentification using access_token
```
{
    "password": "<NEW_PASSWORD>",
    "passwordConfirmation":"<NEW_PASSWORD>"
}
```
**3.4 Delete user**
```
DELETE http://localhost:8080/user
```
with authentification using access_token

**3.5 Create post**
```
POST http://localhost:8080/posts
```
with body and authentification using access_token
```
{
    "title": "<POST_TITLE>",
    "content":"<POST_CONTENT>"
}
```
**3.6 Edit post**
```
POST http://localhost:8080/posts
```
with body and authentification using access_token
```
{
    "title": "<POST_TITLE>",
    "content":"<POST_CONTENT>"
}
 ```
**3.7 Delete post**
```
DELETE http://localhost:8080/posts/<POST_ID>
```
with authentification using access_token

**3.8 Leave post favorite**
```
POST http://localhost:8080/posts/<POST_ID>/add-favorite
```
with authentification using access_token

**3.9 Delete post favorite**
```
POST http://localhost:8080/posts/<POST_ID>/remove-favorite
```
with authentification using access_token

**3.10 Subscription to a user**
```
POST http://localhost:8080/subscribe?to=<USERNAME_YOU_WANT_SUBSCRIBE_TO>
```
with authentification using access_token

**3.11 Unsubscription from a user**
```
POST http://localhost:8080/unsubscribe?from=<USERNAME_YOU_WANT_UNSUBSCRIBE_FROM>
```
with authentification using access_token

**3.12  Commenting on a post**
```
POST http://localhost:8080/posts/<POST_ID>/add-comment
```
with body and authentification using access_token
```
{
    "comment": "<COMMENT_TEXT>"
}
 ```
**3.13  Get a user's feed**
```
GET http://localhost:8080/feed
```
with authentification using access_token

**3.14  Get another user's feed**
```
GET http://localhost:8080/feed/<USER_NAME>
```
with authentification using access_token

**3.15  Get post comments**
```
GET http://localhost:8080/posts/<POST_ID>/get-comment
```
with authentification using access_token


