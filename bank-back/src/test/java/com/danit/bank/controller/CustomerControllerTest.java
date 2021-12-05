package com.danit.bank.controller;

import com.danit.bank.dto.CustomerRequest;
import com.danit.bank.dto.CustomerResponse;
import com.danit.bank.dto.EmployerResponse;
import com.danit.bank.model.Account;
import com.danit.bank.model.Customer;
import com.danit.bank.model.Employer;
import com.danit.bank.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testGetAllCustomers() throws Exception {

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(1L)
                .name("aaa")
                .email("asdas@dasd.ru")
                .phone("+380977300397")
                .age(20)
                .accounts(null)
                .build();
        Customer customer = Customer.builder()
                .name("aaa")
                .email("asdas@dasd.ru")
                .password("123456")
                .age(20)
                .phone("+380977300397")
                .accounts(null)
                .build();

        when(modelMapper.map(any(), any())).thenReturn(customerResponse);
        when(customerService.getAllCustomersPage(1, 10)).thenReturn(Collections.singletonList(customer));

        mockMvc.perform(get("/customers?page=1"))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testGetCustomerById() throws Exception {

        Customer customer = Customer.builder()
                .name("aaa")
                .email("asdas@dasd.ru")
                .password("123456")
                .age(20)
                .phone("+380977300397")
                .accounts(null)
                .build();
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(1L)
                .name("aaa")
                .email("asdas@dasd.ru")
                .phone("+380977300397")
                .age(20)
                .build();

        when(customerService.getOne(anyLong())).thenReturn(customer);
        when(modelMapper.map(any(), any())).thenReturn(customerResponse);
        mockMvc.perform(get("/customers/{id}", 1L))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("aaa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("asdas@dasd.ru"))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testDeleteCustomerById() throws Exception {
        when(customerService.deleteById(anyLong())).thenReturn(true);
        mockMvc.perform(delete("/customers/{id}", 1L))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testCreateCustomer() throws Exception {

        CustomerRequest customerRequest = CustomerRequest.builder()
                .id(1L)
                .name("aaa")
                .email("asdas@dasd.ru")
                .password("123456")
                .age(20)
                .phone("+380977300397")
                .accounts(null)
                .build();
        Customer customer = Customer.builder()
                .name("aaa")
                .email("asdas@dasd.ru")
                .password("123456")
                .age(20)
                .phone("+380977300397")
                .accounts(null)
                .build();
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(1L)
                .name("aaa")
                .email("asdas@dasd.ru")
                .phone("+380977300397")
                .age(20)
                .build();
        when(modelMapper.map(customerRequest, Customer.class)).thenReturn(customer);
        when(modelMapper.map(customerService.save(customer), CustomerResponse.class)).thenReturn(customerResponse);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("aaa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("asdas@dasd.ru"))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testEditCustomerById() throws Exception {


    }
}