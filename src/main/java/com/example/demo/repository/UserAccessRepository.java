package com.example.demo.repository;

import com.example.demo.model.UserAccess;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, Long> {
    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS salespointschema.user_access CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS salespointschema.user_access (" +
                   "    id bigserial primary key," +
                   "    user_login varchar(255) UNIQUE not null," +
                   "    user_password varchar(255) not null," +
                   "    full_name varchar(255) not null," +
                   "    user_role varchar(255) not null)", nativeQuery = true)
    void createTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO salespointschema.user_access(user_login, user_password, full_name, user_role) VALUES " +
                   "('administrator', '111', 'Administrators full name', 'administrator')," +
                   "('manager', '222', 'Managers full name', 'user') ON CONFLICT (user_login) DO NOTHING", nativeQuery = true)
    int insertDefaultValues();
}
