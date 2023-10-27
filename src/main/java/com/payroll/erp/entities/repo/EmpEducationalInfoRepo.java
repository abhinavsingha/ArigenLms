package com.payroll.erp.entities.repo;


import com.payroll.erp.entities.EmpEducationalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpEducationalInfoRepo extends JpaRepository<EmpEducationalInfo, Long> {

    List<EmpEducationalInfo> findByEmpCodeAndIsActive(String empCode, String isActive);
    List<EmpEducationalInfo> findByEmpCode(String empCode);
}
