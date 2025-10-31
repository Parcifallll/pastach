package com.example.Pastach.dao.impl;

import com.example.Pastach.dao.PostDao;
import com.example.Pastach.dao.UserDao;
import com.example.Pastach.model.Post;
import com.example.Pastach.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class PostDaoImplTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("user1", "User1", "user1@test.com", LocalDate.of(2000, 1, 1));
        userDao.create(testUser);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("DELETE FROM users");
    }

    @Test
    @DisplayName("findPostsByUser: when user exists, return list of posts")
    void findPostsByUser_UserExists() {
        userDao.create(new User("user2", "User1", "user2@test.com", LocalDate.of(2000, 1, 1)));
        postDao.create(new Post("user1", "Post 1", "url1"));
        postDao.create(new Post("user1", "Post 2", "url1"));
        postDao.create(new Post("user2", "Post 3", "url2"));

        Collection<Post> posts = postDao.findPostsByUser("user1");
        List<Map<String, Object>> expectedPosts = jdbcTemplate.queryForList( // Map<column, value> (e.x. {id: 1})
                "SELECT id, author_id, text, photo_url, creation_date FROM posts WHERE author_id = 'user1'");

        assertThat(posts)
                .hasSize(2)
                .allSatisfy(post -> {
                    Map<String, Object> expected = expectedPosts
                            .stream()
                            .filter(p -> p.get("id").equals(post.getId()))
                            .findFirst()
                            .orElseThrow();

                    assertThat(post.getAuthor()).isEqualTo(expected.get("author_id"));
                    assertThat(post.getText()).isEqualTo(expected.get("text"));
                    assertThat(post.getPhotoUrl()).isEqualTo(expected.get("photo_url"));
                    assertThat(post.getCreationDate()).isEqualTo(((Timestamp) expected.get("creation_date")).toLocalDateTime());
                });
    }

    @Test
    @DisplayName("findPostsByUser: when user does not exist, return empty")
    void findPostsByUser_UserDoesNotExist() {
        Collection<Post> posts = postDao.findPostsByUser("unknown user");
        assertThat(posts).isEmpty();
    }

    @Test
    @DisplayName("findPostsByUser: when user does not have any posts, return empty list of posts")
    void findPostsByUser_UserHasNoPosts() {
        Collection<Post> posts = postDao.findPostsByUser("user1");
        assertThat(posts).isEmpty();

    }


    @Test
    @DisplayName("findAll: when posts exists, return posts")
    void findAll_PostsExists() {
        userDao.create(new User("user2", "User1", "user2@test.com", LocalDate.of(2000, 1, 1)));
        postDao.create(new Post("user1", "Post 1", "url2"));
        postDao.create(new Post("user2", "Post 2", "url2"));

        List<Map<String, Object>> expectedPosts = jdbcTemplate.queryForList(
                "SELECT id, author_id, text, photo_url, creation_date FROM posts");
        Collection<Post> posts = postDao.findAll();

        assertThat(posts).hasSize(2);
        assertThat(posts)
                .allSatisfy(post -> {
                    Map<String, Object> expected = expectedPosts.stream()
                            .filter(p -> p.get("id").equals(post.getId()))
                            .findFirst()
                            .orElseThrow();

                    assertThat(post.getAuthor()).isEqualTo(expected.get("author_id"));
                    assertThat(post.getText()).isEqualTo(expected.get("text"));
                    assertThat(post.getPhotoUrl()).isEqualTo(expected.get("photo_url"));
                    assertThat(post.getCreationDate()).isEqualTo(((Timestamp) expected.get("creation_date")).toLocalDateTime());
                });
    }

    @Test
    @DisplayName("findAll: when no posts")
    void findAll_NoPosts() {
        Collection<Post> posts = postDao.findAll();

        assertThat(posts).isEmpty();
    }

    @Test
    @DisplayName("create: should insert post and return post with generated id and creation date")
    void create_ShouldInsertPost() {
        Post createdPost = postDao.create(new Post("user1", "New post", "url"));

        // for dbPost and creation_date check
        Optional<Post> foundPost = postDao.findPostsByUser("user1").stream().filter(p -> p.getId() == createdPost.getId()).findFirst();
        assertThat(foundPost).isPresent();
        Post result = foundPost.get();

        Map<String, Object> dbPost = jdbcTemplate.queryForMap(
                "SELECT id, author_id, text, photo_url, creation_date FROM posts WHERE id = ?",
                result.getId()
        );

        assertThat(dbPost.get("author_id")).isEqualTo("user1");
        assertThat(dbPost.get("text")).isEqualTo("New post");
        assertThat(dbPost.get("photo_url")).isEqualTo("url");
        assertThat(((Timestamp) dbPost.get("creation_date")).toLocalDateTime()).isEqualTo(result.getCreationDate());
    }

    @Test
    @DisplayName("deleteById: should delete existing post and return deleted post")
    void deleteById_PostExists() {
        Post post = postDao.create(new Post("user1", "Post to delete", "url1"));
        Optional<Post> result = postDao.deleteById(post.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(post.getId());
        assertThat(result.get().getAuthor()).isEqualTo("user1");
        assertThat(result.get().getText()).isEqualTo("Post to delete");
        assertThat(result.get().getPhotoUrl()).isEqualTo("url1");

        Integer dbCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM posts WHERE id = ?", Integer.class, post.getId());
        assertThat(dbCount).isZero();
    }

    @Test
    @DisplayName("deleteById: when post not exists, should return empty")
    void deleteById_WhenPostNotExists() {
        Optional<Post> result = postDao.deleteById(999);

        assertThat(result).isEmpty();

        Integer dbCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM posts WHERE id = ?", Integer.class, 999);
        assertThat(dbCount).isZero();
    }

    @Test
    @DisplayName("updateById: when post found, should update")
    void updateById_PostFound() {
        Post post = postDao.create(new Post("user1", "Post to delete", "url1"));

        post.setText("");
        post.setPhotoUrl("new");
        post.setCreationDate(LocalDateTime.now());
        postDao.updateById(post, post.getId());

        Optional<Post> dbPost = postDao.findPostsByUser(post.getAuthor())
                .stream()
                .filter(p -> p.getId() == post.getId())
                .findFirst();
        assertThat(dbPost).isPresent();

        assertThat(dbPost.get().getText()).isEqualTo(post.getText());
        assertThat(dbPost.get().getPhotoUrl()).isEqualTo(post.getPhotoUrl());
    }
}
