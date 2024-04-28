package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.db.entity.Comment
import com.antont.switterkeycloak.db.entity.Post
import com.antont.switterkeycloak.db.repository.CommentsRepository
import com.antont.switterkeycloak.db.repository.PostsRepository
import com.antont.switterkeycloak.db.repository.UsersRepository
import com.antont.switterkeycloak.service.PostsService
import com.antont.switterkeycloak.web.dto.CommentDto
import com.antont.switterkeycloak.web.dto.PostDto
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service

@Service
class PostsServiceImpl implements PostsService {

    private Logger LOGGER = LoggerFactory.getLogger(PostsService.class)

    private final PostsRepository postsRepository
    private final UsersRepository usersRepository
    private final CommentsRepository commentsRepository
    private final MongoTemplate mongoTemplate

    PostsServiceImpl(PostsRepository postsRepository, UsersRepository usersRepository, CommentsRepository commentsRepository, MongoTemplate mongoTemplate) {
        this.postsRepository = postsRepository
        this.usersRepository = usersRepository
        this.commentsRepository = commentsRepository
        this.mongoTemplate = mongoTemplate
    }

    @Override
    Post getPost(String postId) {
        try {
            ObjectId postObjectId = new ObjectId(postId)
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("_id").is(postObjectId)),
                    Aggregation.lookup("comments", "postId", "_id", "comments")
            )
            AggregationResults<Post> results = mongoTemplate.aggregate(aggregation, "posts", Post.class)
            return results.getUniqueMappedResult()
        }catch (Exception e){
            String message = "Failed to get post data"
            LOGGER.error(message, e)
            throw new RuntimeException(message)
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
    String addComment(String postId, CommentDto commentDto, String userId) {
        try {
            def user = usersRepository.findByKeycloakId(userId).orElseThrow {
                throw new RuntimeException("User with id: ${userId} now found")
            }

            def post = postsRepository.findById(postId).orElseThrow {
                throw new RuntimeException("Post with id: ${postId} now found")
            }

            def comment = new Comment()
            comment.author = user.id
            comment.postId = post.id
            comment.comment = commentDto.comment

            commentsRepository.save(comment)
            "Your comment was sucessfully added to rhe post ${postId}"
        } catch (Exception e) {
            LOGGER.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Override
    List<Comment> getComments(String postId) {
        try {
            commentsRepository.findAllByPostId(postId)
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
