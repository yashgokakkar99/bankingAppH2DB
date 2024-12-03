package com.gokakkar.banking_system.repository;

import com.gokakkar.banking_system.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByAccountNumber(String accountNumber);
}
