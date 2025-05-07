package com.example.demo.repository;

import com.example.demo.model.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentSystemRepository extends JpaRepository<PaymentSystem, Long> {
    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS salespointschema.payment_system CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS salespointschema.payment_system (" +
                   "id bigserial primary key," +
                   "    payment_system_name varchar(50) UNIQUE not null)", nativeQuery = true)
    void createTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO salespointschema.payment_system (payment_system_name) values" +
                   "('VISA International Service Association')," +
                   "('Mastercard')," +
                   "('JCB')," +
                   "('American Express')," +
                   "('Diners Club International')," +
                   "('China UnionPay') ON CONFLICT (payment_system_name) DO NOTHING", nativeQuery = true)
    int insertDefaultValues();

}

