package com.danit.bank.controller;

import com.danit.bank.dto.CustomerRequest;
import com.danit.bank.dto.CustomerResponse;
import com.danit.bank.model.Account;
import com.danit.bank.model.Customer;
import com.danit.bank.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }



    @GetMapping
        ResponseEntity getAllCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
             List<CustomerResponse> cr = customerService.getAllCustomersPage(page, size).stream()
                     .map(e->modelMapper.map(e, CustomerResponse.class))
                     .collect(Collectors.toList());
        return new ResponseEntity<>(cr, HttpStatus.ACCEPTED);

    }

//    @GetMapping
//    ResponseEntity hello() {
//
//        List<CustomerResponse> cr = customerService.findAll().stream()
//                .map(e->modelMapper.map(e, CustomerResponse.class))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(cr, HttpStatus.ACCEPTED);
//    }


    @GetMapping("/{id}")
    ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse cr = modelMapper.map(customerService.getOne(id), CustomerResponse.class);
        return new ResponseEntity<>(cr, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping
    ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        Customer cust = modelMapper.map(customerRequest, Customer.class);

        Customer customer = new Customer();
        customer.setName(cust.getName());
        customer.setAge(cust.getAge());
        customer.setEmail(cust.getEmail());
        customer.setPassword(passwordEncoder.encode(cust.getPassword()));
        customer.setPhone(cust.getPhone());
        customer.setAccounts(cust.getAccounts());
        CustomerResponse cr = modelMapper.map(customerService.save(customer), CustomerResponse.class);
        return new ResponseEntity<>(cr, HttpStatus.ACCEPTED);
    }

    @PutMapping
    ResponseEntity<Void> editCustomerById(@RequestBody @Valid CustomerRequest customerRequest) {

        Customer customer = modelMapper.map(customerRequest, Customer.class);

        String newName = customer.getName() == null ? customerService.getOne(customer.getId()).getName() : customer.getName();
        String newEmail = customer.getEmail() == null ? customerService.getOne(customer.getId()).getEmail() : customer.getEmail();
        int newAge = customer.getAge() == null ? customerService.getOne(customer.getId()).getAge() : customer.getAge();
        String newPassword = customer.getPassword() == null ? customerService.getOne(customer.getId()).getPassword() : customer.getPassword();
        String newPhone = customer.getPhone() == null ? customerService.getOne(customer.getId()).getPhone() : customer.getPhone();
        List<Account> newList = customer.getAccounts() == null ? customerService.getOne(customer.getId()).getAccounts() : customer.getAccounts();

        Customer result = new Customer();

        result.setId(customer.getId());
        result.setName(newName);
        result.setAge(newAge);
        result.setEmail(newEmail);
        result.setPassword(passwordEncoder.encode(newPassword));
        result.setPhone(newPhone);
        result.setAccounts(newList);
        customerService.edit(result);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
