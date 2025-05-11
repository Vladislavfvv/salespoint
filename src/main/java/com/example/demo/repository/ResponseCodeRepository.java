package com.example.demo.repository;

import com.example.demo.model.ResponseCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ResponseCodeRepository extends JpaRepository<ResponseCode, Long> {
    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS salespointschema.response_code CASCADE", nativeQuery = true)
    int dropTable();


    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS salespointschema.response_code (" +
                   "id                bigserial primary key," +
                   "error_code        varchar(2) UNIQUE not null," +
                   "error_description varchar(255) not null," +
                   "error_level       varchar(255) not null" +
                   ")", nativeQuery = true)
    void createTable();


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO salespointschema.response_code (error_code, error_description, error_level) VALUES " +
                   "('00', 'одобрено и завершено', 'Все в порядке'),\n" +
                   "('01', 'авторизация отклонена, обратиться в банк-эмитент', 'не критическая'),\n" +
                   "('03', 'незарегистрированная торговая точка или агрегатор платежей', 'не критическая'),\n" +
                   "('05', 'авторизация отклонена, оплату не проводить', 'критическая'),\n" +
                   "('41', 'карта утеряна, изъять', 'критическая'),\n" +
                   "('51', 'недостаточно средств на счёте', 'сервисная или аппаратная ошибка'),\n" +
                   "('55', 'неправильный PIN', 'не критическая') ON CONFLICT (error_code) DO NOTHING", nativeQuery = true)
    int insertDefaultValues();
}
