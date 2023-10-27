package com.payroll.erp.entities.repo;


import com.payroll.erp.entities.EmpProfessionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmpProfessionalInfoRepo extends JpaRepository<EmpProfessionalInfo, Long> {
    List<EmpProfessionalInfo> findByEmpCodeAndIsActive(String empCode, String isActive);
    List<EmpProfessionalInfo> findByEmpCode(String empCode);
}
