package com.example.demo.repository;

import com.example.demo.model.SalesPoint;
import com.example.demo.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long> {
    Optional<Terminal> findByTerminalId(String terminalId);

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS salespointschema.terminal CASCADE", nativeQuery = true)
    int dropTable();


    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS salespointschema.terminal(" +
                   "    id          bigserial primary key," +
                   "    terminal_id varchar(9) UNIQUE not null," +
                   "    mcc_id      bigint REFERENCES salespointschema.merchant_category_code (id) ON DELETE CASCADE" +
                   "        ON UPDATE CASCADE," +
                   "    sales_point_id bigint REFERENCES salespointschema.sales_point (id) ON DELETE CASCADE" +
                   "        ON UPDATE CASCADE)", nativeQuery = true)
    void createTable();


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO salespointschema.terminal (terminal_id,\n" +
                   "                                             mcc_id,\n" +
                   "                                             sales_point_id)\n" +
                   "VALUES ('00000001', 1, 1),\n" +
                   "       ('00000002', 2, 2),\n" +
                   "       ('00000003', 3, 3)" +
                   "ON CONFLICT (terminal_id) DO NOTHING", nativeQuery = true)
    int insertDefaultValues();
}
