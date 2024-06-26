package com.example.elibrary.model.dto;

import com.example.elibrary.model.Book;
import com.example.elibrary.model.UserEntity;
import lombok.Data;

@Data
public class BookDto {
    private String author;
    private String isbn;
    private String title;
}
