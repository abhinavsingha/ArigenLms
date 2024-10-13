package com.lms.entities.repo;

import com.lms.entities.MasState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MasStateRepository extends JpaRepository<MasState, String> {
    List<MasState> findByCountryId(int countryId);
}
