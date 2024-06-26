package com.example.elibrary.controller;
import com.example.elibrary.model.Review;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.model.dto.ReviewDto;
import com.example.elibrary.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() { return reviewService.getAllReviews(); }

    @GetMapping("/book/{bookId}")
    public List<Review> getReviewsByBookId(@PathVariable Long bookId) {
        return reviewService.getReviewsByBookId(bookId);
    }

    @PostMapping
    public Review addReview(@RequestBody ReviewDto reviewDto, @AuthenticationPrincipal UserEntity user) {
        return reviewService.addReview(reviewDto, user);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
    }

}
