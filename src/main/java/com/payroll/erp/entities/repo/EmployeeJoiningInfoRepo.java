package com.payroll.erp.entities.repo;

import com.payroll.erp.entities.EmployeeJoiningInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeJoiningInfoRepo extends JpaRepository<EmployeeJoiningInfo, Long> {

    List<EmployeeJoiningInfo> findByEmpCodeAndIsActive(String empCode, String isActive);

    List<EmployeeJoiningInfo> findByEmpCode(String empCode);
    List<EmployeeJoiningInfo> findByIsActive(String isActive);
}
