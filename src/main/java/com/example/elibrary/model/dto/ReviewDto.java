package com.example.elibrary.model.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ReviewDto {
    private Long bookId;
    private String content;
    private Integer rating;

}
