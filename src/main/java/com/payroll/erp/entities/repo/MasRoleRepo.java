package com.payroll.erp.entities.repo;

import com.payroll.erp.entities.MasRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MasRoleRepo extends JpaRepository<MasRole, Long> {

    MasRole findByRoleCodeAndIsActive(String rollCode, Boolean isActive);
}
