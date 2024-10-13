package com.lms.entities.repo;

import com.lms.entities.MasRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MasRoleRepo extends JpaRepository<MasRole, Long> {

    MasRole findByRoleCodeAndIsActive(String rollCode, Boolean isActive);
}
