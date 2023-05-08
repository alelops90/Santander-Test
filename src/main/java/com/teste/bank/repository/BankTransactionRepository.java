package com.teste.bank.repository;

import com.teste.bank.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Integer>, PagingAndSortingRepository<BankTransaction, Integer> {

    @Query(value = "SELECT * FROM BANK_TRANSACTION " +
            "WHERE NUMBER_ACCOUNT = :numberAccount " +
            "AND TRANSACTION_TIME BETWEEN :startDate " +
            "AND :endDate ", nativeQuery = true)
    List<BankTransaction> findAllByDate(String numberAccount, String startDate, String endDate);
}
