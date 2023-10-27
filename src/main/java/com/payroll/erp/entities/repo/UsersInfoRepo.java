package com.payroll.erp.entities.repo;

import com.payroll.erp.entities.UsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersInfoRepo extends JpaRepository<UsersInfo, Long> {

    List<UsersInfo>findByEmpCodeAndIsActive(String empCode,Boolean isActive);
    UsersInfo findByUsername(String username);

    UsersInfo findByEmpCode(String empCode);
}
