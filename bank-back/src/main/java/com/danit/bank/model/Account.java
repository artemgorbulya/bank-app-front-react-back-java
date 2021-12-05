package com.danit.bank.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="account")
public class Account extends AbstractEntity {
    private String number;
    @Enumerated(EnumType.STRING)
    @Column(name="currency")
    private Currency currency;
    private Double balance;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", updatable = false)
    private Customer customer;
}


