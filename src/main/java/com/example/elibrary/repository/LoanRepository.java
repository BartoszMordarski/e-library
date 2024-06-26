package com.example.elibrary.repository;

import com.example.elibrary.model.Loan;
import com.example.elibrary.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByUserId(Long UserId);
    Loan findLoanById(Long id);
}
