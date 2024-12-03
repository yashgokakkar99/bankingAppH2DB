package com.gokakkar.banking_system.model;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Account {
    @Id
    @Column(unique = true, nullable = false)
    private String accountNumber;
    @Column(nullable=false)
    private double balance;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    
    // For H2 database
    public Account()
    {

    }
}
