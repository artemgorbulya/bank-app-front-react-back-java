package com.danit.bank.service;

import com.danit.bank.dao.CustomerRepository;
import com.danit.bank.dao.EmployerRepository;
import com.danit.bank.model.Customer;
import com.danit.bank.model.Employer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    public void testFindAll() {

        Customer customer1 = Customer.builder()
                .name("aaa")
                .email("asdas@dasd.ru")
                .password("123456")
                .age(20)
                .phone("+380977300397")
                .accounts(null)
                .build();
        Customer customer2 = Customer.builder()
                .name("aaa2")
                .email("asd2as@dasd.ru")
                .password("1223456")
                .age(20)
                .phone("+380977300397")
                .accounts(null)
                .build();
        List<Customer> list = List.of(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(list);
        List<Customer> result = customerService.findAll();
        assertThat(result).isEqualTo(list);

    }

    @Test
    public void testGetAllCustomersPage() {


    }

    @Test
    public void testGetOne() {

        Customer customer1 = Customer.builder()
                .name("aaa")
                .email("asdas@dasd.ru")
                .password("123456")
                .age(20)
                .phone("+380977300397")
                .accounts(null)
                .build();
        customer1.setId(1l);
        when(customerRepository.getById(anyLong())).thenReturn(customer1);
        Customer result = customerService.getOne(1L);
        assertThat(result).isEqualTo(customer1);
    }

    @Test
    public void testDeleteById() {

        Customer customer1 = Customer.builder()
                .name("aaa")
                .email("asdas@dasd.ru")
                .password("123456")
                .age(20)
                .phone("+380977300397")
                .accounts(null)
                .build();
        customer1.setId(1l);
        when(customerRepository.existsById(anyLong())).thenReturn(true);
        boolean result = customerService.deleteById(1L);
        assertThat(result).isEqualTo(true);

    }

    @Test
    public void testSave() {

        Customer customer1 = Customer.builder()
                .name("aaa")
                .email("asdas@dasd.ru")
                .password("123456")
                .age(20)
                .phone("+380977300397")
                .accounts(null)
                .build();
        customer1.setId(1l);
        when(customerRepository.save(customer1)).thenReturn(customer1);
        Customer saved = customerService.save(customer1);
        assertThat(saved).isEqualTo(customer1);
    }
}