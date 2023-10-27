package com.payroll.erp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "EMP_EDUCATION_INFO")
public class EmpEducationalInfo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "QUALIFICATION")
    private String qualification;

    @Column(name = "COLLAGE")
    private String collage;

    @Column(name = "UNIVERSITY")
    private String university;

    @Column(name = "COURSE")
    private String course;

    @Column(name = "PASSING_YR")
    private String passingYr;

    @Column(name = "DIV")
    private String div;

    @Column(name = "PERCENTAGE")
    private String percentage;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED_ON", nullable = false)
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    private Timestamp updatedOn;
}
