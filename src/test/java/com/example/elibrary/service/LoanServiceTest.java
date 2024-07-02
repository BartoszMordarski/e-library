package com.example.elibrary.service;

import com.example.elibrary.model.Book;
import com.example.elibrary.model.BookStatus;
import com.example.elibrary.model.Loan;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.repository.BookRepository;
import com.example.elibrary.repository.LoanRepository;
import com.example.elibrary.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLoans() {
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan1, loan2));

        List<Loan> loans = loanService.getAllLoans();

        assertEquals(2, loans.size());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void testGetLoanById() {
        Loan loan = new Loan();
        when(loanRepository.findLoanById(1L)).thenReturn(loan);

        Loan foundLoan = loanService.getLoanById(1L);

        assertNotNull(foundLoan);
        verify(loanRepository, times(1)).findLoanById(1L);
    }

    @Test
    void testGetLoansByUserId() {
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        when(loanRepository.findAllByUserId(1L)).thenReturn(Arrays.asList(loan1, loan2));

        List<Loan> loans = loanService.getLoansByUserId(1L);

        assertEquals(2, loans.size());
        verify(loanRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void testAddLoan() {
        Book book =  new Book();
        book.setId(1L);
        book.setStatus(BookStatus.AVAILABLE);
        UserEntity user = new UserEntity();
        user.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Loan loan = loanService.addLoan(1L, user);

        assertNotNull(loan);
        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(BookStatus.LOANED, book.getStatus());
        verify(bookRepository, times(1)).save(book);
        verify(loanRepository, times(1)).save(any(Loan.class));
    }


    @Test
    void testReturnBook() {
        Book book = new Book();
        book.setStatus(BookStatus.LOANED);
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setBook(book);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(bookRepository.save(book)).thenReturn(book);
        when(loanRepository.save(loan)).thenReturn(loan);

        loanService.returnBook(1L);

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertNotNull(loan.getReturnDate());
        verify(bookRepository, times(1)).save(book);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void testDeleteLoan() {
        Book book = new Book();
        book.setStatus(BookStatus.LOANED);
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setBook(book);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        doNothing().when(loanRepository).delete(loan);
        when(bookRepository.save(book)).thenReturn(book);

        loanService.deleteLoan(1L);

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        verify(bookRepository, times(1)).save(book);
        verify(loanRepository, times(1)).delete(loan);
    }
}
