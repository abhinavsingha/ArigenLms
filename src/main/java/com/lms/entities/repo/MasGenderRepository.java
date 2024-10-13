package com.lms.entities.repo;


import com.lms.entities.MasGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasGenderRepository extends JpaRepository<MasGender, String> {
//    MasGender findById(int genderId);
}
