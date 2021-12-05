package com.danit.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="customer")
public class Customer extends AbstractEntity {

    @NonNull
    private String name;
    @NonNull
    private String email;
    private String password;
    private String phone;
    @NonNull
    private Integer age;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Account> accounts;

    public Account addAccount(Account account) {
        account.setCustomer(this);
        accounts.add(account);
        return account;
    }


}
