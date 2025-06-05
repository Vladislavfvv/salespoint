package com.edme.salespoint.repository;

import com.edme.salespoint.model.Terminal;
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
    @Query(value = "INSERT INTO salespointschema.terminal (terminal_id," +
                   "                                             mcc_id," +
                   "                                             sales_point_id)" +
                   "VALUES ('00000001', 1, 1)," +
                   "       ('00000002', 2, 2)," +
                   "       ('00000003', 3, 3)" +
                   "ON CONFLICT (terminal_id) DO NOTHING", nativeQuery = true)
    void insertDefaultValues();
}
