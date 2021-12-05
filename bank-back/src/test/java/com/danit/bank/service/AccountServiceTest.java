package com.danit.bank.service;

import com.danit.bank.dao.AccountRepository;
import com.danit.bank.model.Account;
import com.danit.bank.model.Currency;
import com.danit.bank.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void testSave() {

        Account account = new Account("1", Currency.CHF, 2342.2, null);
        when(accountRepository.save(account)).thenReturn(account);
        Account saved = accountService.save(account);
        assertThat(saved).isEqualTo(account);
    }

    @Test
    public void testFindAll() {

        Account account1 = new Account("111", Currency.CHF, 2342.2, null);
        Account account2 = new Account("222", Currency.CHF, 2342.2, null);
        List<Account> list = List.of(account1, account2);
        when(accountRepository.findAll()).thenReturn(list);

        List<Account> result = accountService.findAll();
        assertThat(result).isEqualTo(list);


    }

    @Test
    public void testDeleteById() {

        Account account = new Account("1", Currency.CHF, 2342.2, null);
        account.setId(1l);
        when(accountRepository.existsById(anyLong())).thenReturn(true);
        boolean result = accountService.deleteById(1L);
        assertThat(result).isEqualTo(true);

    }

    @Test
    public void testGetOne() {

        Account account = new Account("1", Currency.CHF, 2342.2, null);
        when(accountRepository.getById(anyLong())).thenReturn(account);
        Account accountId = accountService.getOne(1);
        assertThat(accountId).isEqualTo(account);
    }


    @Test
    public void testGetByNumber() {

        Account account1 = new Account("111", Currency.CHF, 2342.2, null);
        Account account2 = new Account("222", Currency.CHF, 2342.2, null);
        List<Account> list = List.of(account1, account2);
        when(accountRepository.findAll()).thenReturn(list);
        Account result = accountService.getByNumber("111");
        assertThat(result).isEqualTo(account1);
    }
}