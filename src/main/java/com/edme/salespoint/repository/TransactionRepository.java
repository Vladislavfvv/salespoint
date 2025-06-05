package com.edme.salespoint.repository;

import com.edme.salespoint.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS salespointschema.transaction CASCADE", nativeQuery = true)
    int dropTable();


    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS salespointschema.transaction (" +
                   "    id                         bigserial primary key," +
                   "    transaction_date           date," +
                   "    sum                        decimal," +
                   "    transaction_type_id        bigint REFERENCES salespointschema.transaction_type (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                   "    card_id                    bigint REFERENCES salespointschema.card (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                   "    terminal_id                bigint REFERENCES salespointschema.terminal (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                   "    response_code_id           bigint REFERENCES salespointschema.response_code (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                   "    authorization_code         varchar(6)," +
                   "    CONSTRAINT unique_transaction_fields UNIQUE (transaction_date, sum, transaction_type_id, card_id, terminal_id, response_code_id)" +
                   ")", nativeQuery = true)
    void createTable();


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO salespointschema.transaction (transaction_date," +
                   "                                                sum," +
                   "                                                transaction_type_id," +
                   "                                                card_id," +
                   "                                                terminal_id," +
                   "                                                response_code_id," +
                   "                                                authorization_code)" +
                   "VALUES ('2022-10-22', 10.11, 1, 1, 2, 1, 2)," +
                   "       ('2022-04-06', 50.92, 1, 2, 2, 2, 3)" +
                   "ON CONFLICT (transaction_date, sum, transaction_type_id, card_id, terminal_id, response_code_id) DO NOTHING", nativeQuery = true)
    void insertDefaultValues();
}
