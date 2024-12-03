package com.gokakkar.banking_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private LocalDateTime transactionDate;
    private String fromAccount;
    private String toAccount;
    private Double amount;
    private String transactionType;

    // For H2 database
    public Transaction()
    {

    }
}