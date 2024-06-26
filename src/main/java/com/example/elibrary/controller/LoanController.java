package com.example.elibrary.controller;

import com.example.elibrary.model.Book;
import com.example.elibrary.model.Loan;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @GetMapping("/user/{id}")
    public List<Loan> getLoanByUserId(@PathVariable Long id) {
        return loanService.getLoansByUserId(id);
    }

    @PostMapping("/{bookId}")
    public Loan addLoan(@PathVariable Long bookId, @AuthenticationPrincipal UserEntity user) {
        return loanService.addLoan(bookId, user);
    }

    @PutMapping("/{loanId}")
    public void returnBook(@PathVariable Long loanId) {
        loanService.returnBook(loanId);
    }

    @DeleteMapping("/{loanId}")
    public void deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
    }
}
