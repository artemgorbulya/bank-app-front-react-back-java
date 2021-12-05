package com.danit.bank.dto;

import com.danit.bank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private Currency currency;
    @Min(value = 0, message = "Can't be less than 0!")
    private Double balance;
}
