package com.example.demo.repository;

import com.example.demo.model.Card;
import com.example.demo.model.SalesPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SalesPointRepository extends JpaRepository<SalesPoint, Long> {

    Optional<SalesPoint> findByPosInn(String cardNumber);

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS salespointschema.sales_point CASCADE", nativeQuery = true)
    int dropTable();


    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS salespointschema.sales_point(" +
                   "    id                bigserial primary key,\n" +
                   "    pos_name          varchar(255) not null,\n" +
                   "    pos_address       varchar(255) not null,\n" +
                   "    pos_inn           varchar(12) UNIQUE not null,\n" +
                   "    acquiring_bank_id bigint REFERENCES salespointschema.acquiring_bank (id) ON DELETE CASCADE\n" +
                   "        ON UPDATE CASCADE\n" +
                   ")", nativeQuery = true)
    void createTable();


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO salespointschema.sales_point (pos_name,\n" +
                   "                                                pos_address,\n" +
                   "                                                pos_inn,\n" +
                   "                                                acquiring_bank_id)\n" +
                   "VALUES ('Shop №1', 'City 1-st 1', '123456788888', 1),\n" +
                   "       ('Shop №2 ', 'City, 2-st 2 ', '159148777777', 3),\n" +
                   "       ('Shop №3', 'City, 3-st 3 ', '123596222222', 1)" +
                   "ON CONFLICT (pos_inn) DO NOTHING", nativeQuery = true)
    void insertDefaultValues();
}
