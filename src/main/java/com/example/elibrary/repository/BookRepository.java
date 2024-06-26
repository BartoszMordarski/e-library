package com.example.elibrary.repository;

import com.example.elibrary.model.Book;
import com.example.elibrary.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
}