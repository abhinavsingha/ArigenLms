package com.lms.entities.repo;

import com.lms.entities.DgMasInvestigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DgMasInvestigationRepository extends JpaRepository<DgMasInvestigation, Long> {

    @Query("SELECT e FROM DgMasInvestigation e WHERE e.investigationName LIKE %:substring%")
    List<DgMasInvestigation> findByNameContaining(String substring);
}
