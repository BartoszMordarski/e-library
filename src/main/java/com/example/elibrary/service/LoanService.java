package com.example.elibrary.service;

import com.example.elibrary.model.Book;
import com.example.elibrary.model.BookStatus;
import com.example.elibrary.model.Loan;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.repository.BookRepository;
import com.example.elibrary.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public List<Loan> getAllLoans() { return loanRepository.findAll(); }

    public Loan getLoanById(Long Id) { return loanRepository.findLoanById(Id); }

    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findAllByUserId(userId);
    }

    public Loan addLoan(Long bookId, UserEntity user) {
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isPresent() && book.get().getStatus().equals(BookStatus.AVAILABLE)) {
            Loan loan = new Loan();
            loan.setBook(book.get());
            loan.setUser(user);
            loan.setLoanDate(LocalDate.now());
            book.get().setStatus(BookStatus.LOANED);
            bookRepository.save(book.get());
            return loanRepository.save(loan);
        } else {
            throw new IllegalArgumentException("Book is not available or does not exist");
        }
    }

    public void returnBook(Long loanId) {
        Optional<Loan>  loan = loanRepository.findById(loanId);
        if(loan.isPresent()) {
            Book book = loan.get().getBook();
            book.setStatus(BookStatus.AVAILABLE);
            bookRepository.save(book);
            loan.get().setReturnDate(LocalDate.now());
            loanRepository.save(loan.get());
        } else {
            throw new IllegalArgumentException("Loan does not exist");
        }
    }

    public void deleteLoan(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if(loan.isPresent()) {
            Book book = loan.get().getBook();
            book.setStatus(BookStatus.AVAILABLE);
            bookRepository.save(book);
            loanRepository.delete(loan.get());
        } else {
            throw new IllegalArgumentException("Loan does not exist");
        }
    }

}
