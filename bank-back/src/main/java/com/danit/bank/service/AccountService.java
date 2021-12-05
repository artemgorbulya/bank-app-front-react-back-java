package com.danit.bank.service;

import com.danit.bank.dao.AccountRepository;
import com.danit.bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Transactional
@Service
public class AccountService {
    public AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account save(Account obj) {
        return accountRepository.save(obj);
    }

    public boolean delete(Account obj) {
        if (accountRepository.existsById(obj.getId())) {
            accountRepository.delete(obj);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAll(List<Account> entities) {
        accountRepository.deleteAll();
    }

    public void saveAll(List<Account> entities) {
        accountRepository.saveAll(entities);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public boolean deleteById(long id) {
        if (accountRepository.existsById(id)){
            accountRepository.deleteById(id);

            return true;
        } else {
            return false;
        }

    }

    public Account getOne(long id) {
        return accountRepository.getById(id);
    }

    public List<Account> getByCustomerId(long id) {
        return accountRepository.findAll().stream().filter(i -> i.getCustomer().getId()==id).collect(Collectors.toList());
    }
    public Account getByNumber(String string) {
        return accountRepository.findAll().stream().filter(i -> Objects.equals(i.getNumber(), string)).findFirst().orElse(null);

    }


}
