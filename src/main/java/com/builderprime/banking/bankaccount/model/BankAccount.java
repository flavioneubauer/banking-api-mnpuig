package com.builderprime.banking.bankaccount.model;

import com.builderprime.banking.customer.model.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BankAccount {
    
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer owner;

    @DecimalMin(value = "0.0", inclusive = false, message = "Balance should be greater than zero")
    @Column(nullable = false)
    private BigDecimal balance;
}
