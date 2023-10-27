package com.payroll.erp.entities.repo;

import com.payroll.erp.entities.EmpAddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmpAddressInfoRepo extends JpaRepository<EmpAddressInfo, Long> {

    List<EmpAddressInfo> findByEmpCodeAndIsActive(String empCode, String isActive);
    List<EmpAddressInfo> findByEmpCode(String empCode);
}
