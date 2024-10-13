package com.lms.entities.repo;


import com.lms.entities.MasBloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasBloodGroupRepository extends JpaRepository<MasBloodGroup, Long> {

}
