package com.danit.bank.dao;

import com.danit.bank.model.Customer;
import com.danit.bank.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

}