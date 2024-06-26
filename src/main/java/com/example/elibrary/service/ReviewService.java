package com.example.elibrary.service;

import com.example.elibrary.model.Book;
import com.example.elibrary.model.Review;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.model.dto.ReviewDto;
import com.example.elibrary.repository.BookRepository;
import com.example.elibrary.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByBookId(Long bookId){
        return reviewRepository.findAllByBookId(bookId);
    }

    public Review addReview(ReviewDto reviewDto, UserEntity user) {
        Review review = modelMapper.map(reviewDto, Review.class);
        review.setBookId(reviewDto.getBookId());
        review.setUser(user);

        return reviewRepository.save(review);

    }

    public void deleteReviewById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
