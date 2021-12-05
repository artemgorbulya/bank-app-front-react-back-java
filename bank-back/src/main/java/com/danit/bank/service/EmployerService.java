package com.danit.bank.service;

import com.danit.bank.dao.EmployerRepository;
import com.danit.bank.model.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class EmployerService {
    public EmployerRepository employerRepository;

    @Autowired
    public EmployerService(EmployerRepository employerRepository){
        this.employerRepository = employerRepository;
    }

    public Employer save(Employer obj) {
        return employerRepository.save(obj);
    }

    public boolean delete(Employer obj) {
        if (employerRepository.existsById(obj.getId())){
            employerRepository.delete(obj);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAll(List<Employer> entities) {
        employerRepository.deleteAll(entities);
    }

    public void saveAll(List<Employer> entities) {
        employerRepository.saveAll(entities);
    }

    public List<Employer> findAll() {
        return employerRepository.findAll();
    }

    public boolean deleteById(long id) {

        if (employerRepository.existsById(id)) {
            employerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public Employer getOne(long id) {
        return employerRepository.getById(id);
    }

}
