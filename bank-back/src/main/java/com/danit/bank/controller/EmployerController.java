package com.danit.bank.controller;

import com.danit.bank.dto.EmployerRequest;
import com.danit.bank.dto.EmployerResponse;
import com.danit.bank.model.Employer;
import com.danit.bank.service.EmployerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employers")
public class EmployerController {
    private EmployerService employerService;
    private ModelMapper modelMapper;

    public EmployerController(EmployerService employerService, ModelMapper modelMapper) {
        this.employerService = employerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    ResponseEntity hello() {
        List<EmployerResponse> er = employerService.findAll().stream()
                .map(e->modelMapper.map(e,EmployerResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(er, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployerResponse> getEmployerById(@PathVariable Long id) {
        EmployerResponse er = modelMapper.map(employerService.getOne(id), EmployerResponse.class);
        return new ResponseEntity<>(er, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEmployerById(@PathVariable Long id) {
        employerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping
    ResponseEntity<Employer> getCustomerById(@RequestBody @Valid EmployerRequest employerRequest) {
        Employer employer = modelMapper.map(employerRequest, Employer.class);
        Employer ebb = new Employer();
        ebb.setName(employer.getName());
        ebb.setAddress(employer.getAddress());
        return new ResponseEntity<>(employerService.save(ebb), HttpStatus.ACCEPTED);
    }
}