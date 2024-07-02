package com.example.elibrary.controller;


import com.example.elibrary.controller.ReviewController;
import com.example.elibrary.model.Review;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.model.dto.ReviewDto;
import com.example.elibrary.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testGetAllReviews() throws Exception {
        Review review1 = new Review();
        Review review2 = new Review();
        when(reviewService.getAllReviews()).thenReturn(Arrays.asList(review1, review2));

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    @WithMockUser
    void testGetReviewsByBookId() throws Exception {
        Review review1 = new Review();
        Review review2 = new Review();
        when(reviewService.getReviewsByBookId(1L)).thenReturn(Arrays.asList(review1, review2));

        mockMvc.perform(get("/reviews/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(reviewService, times(1)).getReviewsByBookId(1L);
    }


    @Test
    @WithMockUser
    void testDeleteReview() throws Exception {
        doNothing().when(reviewService).deleteReviewById(1L);

        mockMvc.perform(delete("/reviews/1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(reviewService, times(1)).deleteReviewById(1L);
    }
}
