package com.example.demo.repository;

import com.example.demo.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByCardNumber(String cardNumber);

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS salespointschema.card CASCADE", nativeQuery = true)
    int dropTable();


    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS salespointschema.card(\n" +
                   "    id                         bigserial primary key,\n" +
                   "    card_number                varchar(50) UNIQUE,\n" +
                   "    expiration_date            date,\n" +
                   "    holder_name                varchar(50),\n"  +
                   "    payment_system_id          bigint REFERENCES salespointschema.payment_system (id) ON DELETE CASCADE\n" +
                   "        ON UPDATE CASCADE\n" +
                   ")", nativeQuery = true)
    void createTable();


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO salespointschema.card (card_number, expiration_date, holder_name, payment_system_id)\n" +
                   "VALUES ('4123450000000019', '2025-12-31', 'IVAN I. IVANOV', 2 ),\n" +
                   "('5123450000000024', '2025-12-31', 'SEMION E. PETROV', 3) " +
                   "ON CONFLICT (card_number) DO NOTHING", nativeQuery = true)
    int insertDefaultValues();


}
