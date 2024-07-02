package com.example.elibrary.controller;

import com.example.elibrary.controller.LoanController;
import com.example.elibrary.model.Book;
import com.example.elibrary.model.Loan;
import com.example.elibrary.model.UserEntity;
import com.example.elibrary.service.BookService;
import com.example.elibrary.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
@AutoConfigureMockMvc
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @WithMockUser
    void testGetAllLoans() throws Exception {
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        List<Loan> loans = Arrays.asList(loan1, loan2);

        when(loanService.getAllLoans()).thenReturn(loans);

        mockMvc.perform(get("/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(loans.size()));

        verify(loanService, times(1)).getAllLoans();
    }

    @Test
    @WithMockUser
    void testGetLoanById() throws Exception {
        Loan loan = new Loan();
        loan.setId(1L);
        when(loanService.getLoanById(anyLong())).thenReturn(loan);

        mockMvc.perform(get("/loans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(loanService, times(1)).getLoanById(1L);
    }

    @Test
    @WithMockUser
    void testGetLoansByUserId() throws Exception {
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        List<Loan> loans = Arrays.asList(loan1, loan2);

        when(loanService.getLoansByUserId(anyLong())).thenReturn(loans);

        mockMvc.perform(get("/loans/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(loans.size()));

        verify(loanService, times(1)).getLoansByUserId(1L);
    }


    @Test
    @WithMockUser
    void testReturnBook() throws Exception {
        doNothing().when(loanService).returnBook(anyLong());

        mockMvc.perform(put("/loans/1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(loanService, times(1)).returnBook(1L);
    }

    @Test
    @WithMockUser
    void testDeleteLoan() throws Exception {
        doNothing().when(loanService).deleteLoan(anyLong());

        mockMvc.perform(delete("/loans/1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(loanService, times(1)).deleteLoan(1L);
    }

}
