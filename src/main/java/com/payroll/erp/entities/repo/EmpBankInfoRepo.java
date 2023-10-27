package com.payroll.erp.entities.repo;

import com.payroll.erp.entities.EmpBankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmpBankInfoRepo extends JpaRepository<EmpBankInfo, Long> {

    List<EmpBankInfo> findByEmpCodeAndIsActive(String empCode, String isActive);
    List<EmpBankInfo> findByEmpCode(String empCode);

}
