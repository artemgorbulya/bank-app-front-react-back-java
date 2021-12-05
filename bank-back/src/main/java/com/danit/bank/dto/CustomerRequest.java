package com.danit.bank.dto;

import com.danit.bank.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private Long id;

    @Size(min = 2, message = "Name must be longer than 2 chars!")
    private String name;

    @Pattern(
            regexp = "^(.+)@(.+)$",
            message = "Invalid email!"
    )
    private String email;

    @Size(min = 2, max = 12, message = "Password must be longer than 2 chars!")
    private String password;

    @Size(min = 2, message = "Name must be longer than 2 chars!")
    @Pattern(
            regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\\\s\\\\./0-9]*$",
            message = "Invalid Phone number!"
    )
    private String phone;

    @Min(value = 18, message = "Age can't be less than 18!")
    private Integer age;

    private List<Account> accounts;

}
