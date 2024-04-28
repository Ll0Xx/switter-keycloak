package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.db.entity.Post
import com.antont.switterkeycloak.db.repository.PostsRepository
import com.antont.switterkeycloak.db.repository.UsersRepository
import com.antont.switterkeycloak.service.PostsService
import com.antont.switterkeycloak.web.dto.PostDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PostsServiceImpl implements PostsService {

    private Logger LOGGER = LoggerFactory.getLogger(PostsService.class)

    private final PostsRepository postsRepository
    private final UsersRepository usersRepository

    PostsServiceImpl(PostsRepository postsRepository, UsersRepository usersRepository) {
        this.postsRepository = postsRepository
        this.usersRepository = usersRepository
    }

    @Override
    Post getPost(String userId) {
        postsRepository.findById(userId).orElseThrow {
            throw new RuntimeException("Post with id: ${userId} now found")
        }
    }

    @Override
    Post createPost(PostDto dto, String userId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }

            def post = new Post()
            post.title = dto.title
            post.content = dto.content
            post.postOwner = user.id

            postsRepository.save(post)
        } catch (Exception e) {
            String message = "Failed to create post"
            LOGGER.error(message, e)
            throw new RuntimeException(message)
        }
    }

    @Override
    Post editPost(PostDto dto, String postId, String userId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }

            def post = postsRepository.findByIdAndPostOwner(postId, user.id).orElseThrow {
                throw new RuntimeException("Post with id: ${postId} now found")
            }
            post.title = dto.title
            post.content = dto.content

            postsRepository.save(post)
        } catch (Exception e) {
            LOGGER.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Override
    Post addToFavorite(String postId, String userId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }

            def post = postsRepository.findById(postId).orElseThrow {
                throw new RuntimeException("Post with id: ${postId} now found")
            }

            post.favorites.add(user.id)
            postsRepository.save(post)
        } catch (Exception e) {
            LOGGER.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Override
    Post removeFromFavorite(String postId, String userId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }
            def post = postsRepository.findById(postId).orElseThrow {
                throw new RuntimeException("Post with id: ${postId} now found")
            }

            post.favorites.remove(user.id)
            postsRepository.save(post)
        } catch (Exception e) {
            LOGGER.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Override
    void deletePost(String postId, String userId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }

            def post = postsRepository.findByIdAndPostOwner(postId, user.id).orElseThrow {
                throw new RuntimeException("Post with id: ${postId} now found")
            }
            postsRepository.delete(post)
        } catch (Exception e) {
            LOGGER.error(e.message)
            throw new RuntimeException(e.message)
        }
    }
}
