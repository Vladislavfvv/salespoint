package com.example.demo.repository;

import com.example.demo.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS salespointschema.transaction_type CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS salespointschema.transaction_type (" +
                   "    id                    bigserial primary key," +
                   "    transaction_type_name varchar(255) UNIQUE not null," +
                   "    operator              varchar(1) not null" +
                   ")", nativeQuery = true)
    void createTable();


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO salespointschema.transaction_type (transaction_type_name, operator)\n" +
                   "VALUES ('Списание со счета ', '-'),\n" +
                   "       ('Пополнение счета', '+') ON CONFLICT (transaction_type_name) DO NOTHING", nativeQuery = true)
    void insertDefaultValues();
}
