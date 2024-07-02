package com.example.elibrary.service;

import com.example.elibrary.model.Review;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.model.dto.ReviewDto;
import com.example.elibrary.repository.ReviewRepository;
import com.example.elibrary.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import javax.management.modelmbean.ModelMBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReviews() {
        Review review1 = new Review();
        Review review2 = new Review();
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review1, review2));

        List<Review> reviews = reviewService.getAllReviews();

        assertEquals(2, reviews.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testGetReviewsByBookId() {
        Review review1 = new Review();
        Review review2 = new Review();
        when(reviewRepository.findAllByBookId(1L)).thenReturn(Arrays.asList(review1, review2));

        List<Review> reviews = reviewService.getReviewsByBookId(1L);

        assertEquals(2, reviews.size());
        verify(reviewRepository, times(1)).findAllByBookId(1L);
    }

    @Test
    void testAddReview() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setBookId(1L);
        reviewDto.setContent("Great book!");

        Review review = new Review();
        review.setBookId(1L);
        review.setContent("Great book!");

        UserEntity user = new UserEntity();
        user.setId(1L);

        when(modelMapper.map(reviewDto, Review.class)).thenReturn(review);
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Review savedReview = reviewService.addReview(reviewDto, user);

        assertNotNull(savedReview);
        assertEquals("Great book!", savedReview.getContent());
        assertEquals(1L, savedReview.getBookId());
        assertEquals(user, savedReview.getUser());

        verify(modelMapper, times(1)).map(reviewDto, Review.class);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testDeleteReviewById() {
        doNothing().when(reviewRepository).deleteById(1L);

        reviewService.deleteReviewById(1L);

        verify(reviewRepository, times(1)).deleteById(1L);
    }

}
