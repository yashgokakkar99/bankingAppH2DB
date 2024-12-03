package com.gokakkar.banking_system.repository;

import com.gokakkar.banking_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
