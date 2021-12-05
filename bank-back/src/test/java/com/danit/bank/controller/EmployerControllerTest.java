package com.danit.bank.controller;

import com.danit.bank.dto.EmployerRequest;
import com.danit.bank.dto.EmployerResponse;
import com.danit.bank.model.Employer;
import com.danit.bank.service.EmployerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(EmployerController.class)
class EmployerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EmployerService employerService;
    @MockBean
    private ModelMapper modelMapper;


    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testGetAllEmployer() throws Exception {

        Employer employer = Employer.builder()
                .name("name")
                .address("address")
                .build();
        EmployerResponse employerResp = EmployerResponse.builder()
                .id(1L)
                .name("name")
                .address("address")
                .build();
        
        when(employerService.findAll()).thenReturn(List.of(employer));
        when(modelMapper.map(any(), any())).thenReturn(employerResp);
        mockMvc.perform(get("/employers"))
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(employerResp))))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testGetEmployerById() throws Exception {

        Employer employer = Employer.builder()
                .name("name")
                .address("address")
                .build();
        EmployerResponse employerResp = EmployerResponse.builder()
                .id(1L)
                .name("name")
                .address("address")
                .build();

        when(employerService.getOne(anyLong())).thenReturn(employer);
        when(modelMapper.map(any(), any())).thenReturn(employerResp);
        mockMvc.perform(get("/employers/{id}", 1L))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("address"))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testDeleteEmployer() throws Exception {

        when(employerService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/employers/{id}", 1L))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value="admin",roles="ADMIN")
    public void testAddNewEmployer() throws Exception {

        EmployerRequest employerRequest = EmployerRequest.builder()
                .name("name1")
                .address("address1")
                .build();
        Employer employer = Employer.builder()
                .name("name2")
                .address("address2")
                .build();

        when(employerService.save(any())).thenReturn(employer);
        when(modelMapper.map(any(), any())).thenReturn(employer);
        mockMvc.perform(post("/employers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employerRequest)))
                        .andDo(print())
                .andExpect(status().isAccepted());

    }

}
