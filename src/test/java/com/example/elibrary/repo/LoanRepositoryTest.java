package com.example.elibrary.repo;

import com.example.elibrary.model.Book;
import com.example.elibrary.model.Loan;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LoanRepository loanRepository;

    @Test
    public void testFindAllByUserId() {
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");
        user1.setPassword("password1");

        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");
        user2.setPassword("password2");

        entityManager.persistAndFlush(user1);
        entityManager.persistAndFlush(user2);

        Book book1 = new Book();
        book1.setTitle("Test Book 1");
        book1.setAuthor("Test Author 1");
        book1.setIsbn("1234567890");

        Book book2 = new Book();
        book2.setTitle("Test Book 2");
        book2.setAuthor("Test Author 2");
        book2.setIsbn("0987654321");

        entityManager.persistAndFlush(book1);
        entityManager.persistAndFlush(book2);

        Loan loan1 = new Loan();
        loan1.setUser(user1);
        loan1.setBook(book1);

        Loan loan2 = new Loan();
        loan2.setUser(user1);
        loan2.setBook(book2);

        Loan loan3 = new Loan();
        loan3.setUser(user2);
        loan3.setBook(book1);

        entityManager.persistAndFlush(loan1);
        entityManager.persistAndFlush(loan2);
        entityManager.persistAndFlush(loan3);

        List<Loan> loansByUser1 = loanRepository.findAllByUserId(user1.getId());
        List<Loan> loansByUser2 = loanRepository.findAllByUserId(user2.getId());

        assertThat(loansByUser1).hasSize(2);
        assertThat(loansByUser2).hasSize(1);
    }

    @Test
    public void testFindLoanById() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("password1");

        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");

        entityManager.persistAndFlush(book);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);

        entityManager.persistAndFlush(loan);

        Optional<Loan> foundLoan = loanRepository.findById(loan.getId());

        assertThat(foundLoan).isPresent();
        assertThat(foundLoan.get().getUser()).isEqualTo(user);
        assertThat(foundLoan.get().getBook()).isEqualTo(book);
    }
}
