package com.danit.bank.controller;

import com.danit.bank.dto.AccountRequest;
import com.danit.bank.dto.AccountResponse;
import com.danit.bank.model.Account;
import com.danit.bank.model.Currency;
import com.danit.bank.model.Customer;
import com.danit.bank.service.AccountService;
import com.danit.bank.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService accountService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testGetAllAccount() throws Exception {

        Account account = Account.builder()
                .number("aaa")
                .currency(Currency.UAH)
                .balance(123.0)
                .customer(null)
                .build();
        AccountResponse accountResp = AccountResponse.builder()
                .id(1L).number("aaa")
                .currency(Currency.UAH)
                .balance(123.0)
                .build();

        when(accountService.findAll()).thenReturn(List.of(account));
        when(modelMapper.map(any(), any())).thenReturn(accountResp);

        mockMvc.perform(get("/accounts"))
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(accountResp))))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testGetAccountById() throws Exception {

        Account account = Account.builder()
                .number("aaa")
                .currency(Currency.UAH)
                .balance(123.0)
                .customer(null)
                .build();
        AccountResponse accountResp = AccountResponse.builder()
                .id(1L)
                .number("aaa")
                .currency(Currency.UAH)
                .balance(123.0)
                .build();

        when(accountService.getOne(anyLong())).thenReturn(account);
        when(modelMapper.map(any(), any())).thenReturn(accountResp);
        mockMvc.perform(get("/accounts/{id}", 1L))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("aaa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(123.0))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testGetAccountByCustomerId() throws Exception {

        Account account = Account.builder()
                .number("aaa")
                .currency(Currency.UAH)
                .balance(123.0)
                .customer(null)
                .build();
        AccountResponse accountResp = AccountResponse.builder()
                .id(1L).number("aaa")
                .currency(Currency.UAH)
                .balance(123.0)
                .build();

        when(accountService.getByCustomerId(anyLong())).thenReturn(List.of(account));
        when(modelMapper.map(any(), any())).thenReturn(accountResp);

        mockMvc.perform(get("/accounts/customer/{id}", 1L))
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(accountResp))))
                .andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testDeleteAccountById() throws Exception {

        when(accountService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/accounts/{id}", 1L))
                .andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testPostCustomerById() throws Exception {

        Account account = Account.builder()
                .number("aaa")
                .currency(Currency.UAH)
                .balance(123.0)
                .build();
        Customer customer = Customer.builder()
                .name("aaa")
                .email("asdas@dasd.ru")
                .password("123456")
                .age(20)
                .phone("+380977300397")
                .accounts(new ArrayList<Account>(Collections.singletonList(account)))
                .build();
        AccountRequest accountRequest = AccountRequest.builder()
                .currency(Currency.UAH)
                .balance(123.0)
                .build();

       when(modelMapper.map(any(), any())).thenReturn(account);
       when(customerService.getOne(anyLong())).thenReturn(customer);

        mockMvc.perform(post("/accounts/customer/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountRequest)))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testAddMoneyAccount() throws Exception {

        Account account = Account.builder()
                .number("fdc17651-45b9-446c-b3ad-bfda10cffc85")
                .currency(Currency.UAH)
                .balance(300.0)
                .customer(null)
                .build();
        AccountResponse accountResp = AccountResponse.builder()
                .id(2L)
                .number("fdc17651-45b9-446c-b3ad-bfda10cffc85")
                .currency(Currency.UAH)
                .balance(450.0)
                .build();

        when(accountService.getByNumber(any())).thenReturn(account);
        when(modelMapper.map(any(), any())).thenReturn(accountResp);

        mockMvc.perform(post("/accounts/addmoney")
                        .param("accountnumber", "fdc17651-45b9-446c-b3ad-bfda10cffc85")
                        .param("value", "150")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("fdc17651-45b9-446c-b3ad-bfda10cffc85"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(450.0))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testWithdrawMoneyAccount() throws Exception {

        Account account = Account.builder()
                .number("fdc17651-45b9-446c-b3ad-bfda10cffc85")
                .currency(Currency.UAH)
                .balance(300.0)
                .customer(null)
                .build();
        AccountResponse accountResp = AccountResponse.builder()
                .id(2L)
                .number("fdc17651-45b9-446c-b3ad-bfda10cffc85")
                .currency(Currency.UAH)
                .balance(150.0)
                .build();

        when(accountService.getByNumber(any())).thenReturn(account);
        when(modelMapper.map(any(), any())).thenReturn(accountResp);

        mockMvc.perform(post("/accounts/withdraw")
                        .param("accountnumber", "fdc17651-45b9-446c-b3ad-bfda10cffc85")
                        .param("value", "150")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("fdc17651-45b9-446c-b3ad-bfda10cffc85"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(150.0))
                .andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testTransferMoneyAccount() throws Exception {


        Account account = Account.builder()
                .number("fdc17651-45b9-446c-b3ad-bfda10cffc85")
                .currency(Currency.UAH)
                .balance(300.0)
                .customer(null)
                .build();
        AccountResponse accountResp = AccountResponse.builder()
                .id(2L)
                .number("fdc17651-45b9-446c-b3ad-bfda10cffc85")
                .currency(Currency.UAH)
                .balance(327.0)
                .build();

        when(accountService.getByNumber(any())).thenReturn(account);
        when(modelMapper.map(any(), any())).thenReturn(accountResp);


        mockMvc.perform(post("/accounts/transfer")
                        .param("fromaccountnumber", "8387783e-a51b-4c13-94c8-0d6bfbd436da")
                        .param("toaccountnumber", "fdc17651-45b9-446c-b3ad-bfda10cffc85")
                        .param("value", "27")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("fdc17651-45b9-446c-b3ad-bfda10cffc85"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(327.0))
                .andExpect(status().isAccepted());

    }
}