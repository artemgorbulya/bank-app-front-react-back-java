package com.danit.bank.controller;

import com.danit.bank.dto.AccountRequest;
import com.danit.bank.dto.AccountResponse;
import com.danit.bank.model.Account;
import com.danit.bank.model.Customer;
import com.danit.bank.service.AccountService;
import com.danit.bank.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService accountService;
    private CustomerService customerService;
    private ModelMapper modelMapper;

    public AccountController(AccountService accountService, CustomerService customerService, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    ResponseEntity getAllAccount() {
        List<AccountResponse> ar = accountService.findAll().stream()
                .map(e->modelMapper.map(e, AccountResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(ar, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        AccountResponse ar = modelMapper.map(accountService.getOne(id), AccountResponse.class);
        return new ResponseEntity<>(ar, HttpStatus.ACCEPTED);
    }

    @GetMapping("/customer/{id}")
    ResponseEntity<List<AccountResponse>> getAccountByCustomerId(@PathVariable Long id) {
        List<AccountResponse> ar = accountService.getByCustomerId(id).stream()
                .map(e->modelMapper.map(e, AccountResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(ar, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAccountById(@PathVariable Long id) {
        accountService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/customer/{id}")
    ResponseEntity<Void> postCustomerById(@RequestBody @Valid AccountRequest accountRequest, @PathVariable Long id) {
        Account account = modelMapper.map(accountRequest, Account.class);

        Account account1 = new Account();
        account1.setNumber(UUID.randomUUID().toString());
        account1.setCurrency(account.getCurrency());
        account1.setBalance(account.getBalance());

        Customer customer1 = customerService.getOne(id);
        customer1.addAccount(account1);
        customerService.edit(customer1);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/addmoney")
    ResponseEntity<AccountResponse> addMoneyAccount(@RequestParam("accountnumber") String number, @RequestParam("value") Double value) {
        Double oldBalance = accountService.getByNumber(number).getBalance();
        Double newBalance = oldBalance+value;
        Account acc = accountService.getByNumber(number);
        acc.setBalance(newBalance);
        accountService.save(acc);
        AccountResponse ar = modelMapper.map(accountService.getByNumber(number), AccountResponse.class);
        return new ResponseEntity<>(ar, HttpStatus.ACCEPTED);
    }

    @PostMapping("/withdraw")
    ResponseEntity<AccountResponse> withdrawMoneyAccount(@RequestParam("accountnumber") String number, @RequestParam("value") Double value) {
        if (accountService.getByNumber(number).getBalance() >= value){
            Double oldBalance = accountService.getByNumber(number).getBalance();
            Double newBalance = oldBalance-value;
            Account acc = accountService.getByNumber(number);
            acc.setBalance(newBalance);
            AccountResponse ar = modelMapper.map(accountService.getByNumber(number), AccountResponse.class);
            return new ResponseEntity<>(ar, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/transfer")
    ResponseEntity<AccountResponse> transferMoneyAccount(@RequestParam("fromaccountnumber") String fromnumber, @RequestParam("toaccountnumber") String tonumber, @RequestParam("value") Double value) {
        if (accountService.getByNumber(fromnumber).getBalance() >= value){
            Account accfrom = accountService.getByNumber(fromnumber);
            Double fromvalue = accountService.getByNumber(fromnumber).getBalance();
            Account accto = accountService.getByNumber(tonumber);
            Double tovalue = accountService.getByNumber(tonumber).getBalance();
            accfrom.setBalance(fromvalue-value);
            accto.setBalance(tovalue+value);
            AccountResponse ar = modelMapper.map(accountService.getByNumber(tonumber), AccountResponse.class);
            return new ResponseEntity<>(ar, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}