package com.danit.bank.service;

import com.danit.bank.dao.CustomerRepository;
import com.danit.bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class CustomerService {

    public CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }


    public List<Customer> findAll() {
        return customerRepository.findAll();
    }


    public List<Customer> getAllCustomersPage( int page, int size) {
        Pageable paging = PageRequest.of(page-1, size);
        Page<Customer> pagedResult = customerRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Customer>();
        }
    }

    public void edit(Customer obj) {
        customerRepository.save(obj);
    }

    public Customer getOne(long id) {
        return customerRepository.getById(id);
    }

    public boolean deleteById(long id) {
        if (customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Customer save(Customer obj) {

        return customerRepository.save(obj);
    }



}

