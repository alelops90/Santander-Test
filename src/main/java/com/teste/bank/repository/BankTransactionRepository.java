package com.teste.bank.repository;

import com.teste.bank.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Integer> {
}
