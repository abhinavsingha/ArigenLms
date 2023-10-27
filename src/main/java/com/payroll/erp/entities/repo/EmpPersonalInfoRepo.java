package com.payroll.erp.entities.repo;

import com.payroll.erp.entities.EmpPersonalInfo;
import com.payroll.erp.entities.EmployeeJoiningInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface EmpPersonalInfoRepo extends JpaRepository<EmpPersonalInfo, Integer> {
    Optional<EmpPersonalInfo> findByName(String username);
    List<EmpPersonalInfo> findByEmpCodeAndIsActive(String empCode, String isActive);
}
