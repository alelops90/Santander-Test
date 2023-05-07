package com.teste.bank.repository;

import com.teste.bank.entity.BankClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankClientRepository extends JpaRepository<BankClient, Integer> {

    @Query(value = "SELECT * FROM BANK_CLIENT bc WHERE bc.NAME = :name ", nativeQuery = true)
    BankClient findByName(String name);
}
