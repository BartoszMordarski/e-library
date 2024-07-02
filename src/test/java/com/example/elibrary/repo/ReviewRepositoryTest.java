package com.example.elibrary.repo;

import com.example.elibrary.model.Book;
import com.example.elibrary.model.Review;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ReviewRepository reviewRepository;


    @Test
    public void testFindAllByBookId() {
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");
        user1.setPassword("password1");

        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");
        user2.setPassword("password2");

        entityManager.persistAndFlush(user1);
        entityManager.persistAndFlush(user2);

        Book book1 = new Book();
        book1.setTitle("Test Book 1");
        book1.setAuthor("Test Author 1");
        book1.setIsbn("1234567890");

        Book book2 = new Book();
        book2.setTitle("Test Book 2");
        book2.setAuthor("Test Author 2");
        book2.setIsbn("0987654321");

        entityManager.persistAndFlush(book1);
        entityManager.persistAndFlush(book2);

        Review review1 = new Review();
        review1.setUser(user1);
        review1.setBookId(book1.getId());
        review1.setRating(5);
        review1.setContent("That was an amazing book");

        Review review2 = new Review();
        review2.setUser(user2);
        review2.setBookId(book1.getId());
        review2.setRating(2);
        review2.setContent("Didn't like it");

        Review review3 = new Review();
        review3.setUser(user2);
        review3.setBookId(book2.getId());
        review3.setRating(3);
        review3.setContent("It was ok");

        entityManager.persistAndFlush(review1);
        entityManager.persistAndFlush(review2);
        entityManager.persistAndFlush(review3);

        List<Review> reviewsOfBook1 = reviewRepository.findAllByBookId(book1.getId());
        List<Review> reviewsOfBook2 = reviewRepository.findAllByBookId(book2.getId());

        assertThat(reviewsOfBook1).hasSize(2);
        assertThat(reviewsOfBook2).hasSize(1);
    }
}
