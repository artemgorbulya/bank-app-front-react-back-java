package com.danit.bank.service;

import com.danit.bank.dao.AccountRepository;
import com.danit.bank.dao.EmployerRepository;
import com.danit.bank.model.Account;
import com.danit.bank.model.Currency;
import com.danit.bank.model.Employer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployerServiceTest {

    @MockBean
    private EmployerRepository employerRepository;

    @Autowired
    private EmployerService employerService;

    @Test
    public void testSave() {
        Employer employer = new Employer("name",  "address");
        when(employerRepository.save(employer)).thenReturn(employer);
        Employer saved = employerService.save(employer);
        assertThat(saved).isEqualTo(employer);
    }

    @Test
    public void testFindAll() {

        Employer employer1 = new Employer("name1",  "address1");
        Employer employer2 = new Employer("name2",  "address2");
        List<Employer> list = List.of(employer1, employer2);
        when(employerRepository.findAll()).thenReturn(list);
        List<Employer> result = employerService.findAll();
        assertThat(result).isEqualTo(list);
    }

    @Test
    public void testDeleteById() {

        Employer employer1 = new Employer("name1",  "address1");
        employer1.setId(1l);
        when(employerRepository.existsById(anyLong())).thenReturn(true);
        boolean result = employerService.deleteById(1L);
        assertThat(result).isEqualTo(true);

    }

    @Test
    public void testGetOne() {

        Employer employer1 = new Employer("name1",  "address1");
        employer1.setId(1l);
        when(employerRepository.getById(anyLong())).thenReturn(employer1);
        Employer result = employerService.getOne(1L);
        assertThat(result).isEqualTo(employer1);
    }
}