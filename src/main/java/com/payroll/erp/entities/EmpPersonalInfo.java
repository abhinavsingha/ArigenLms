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
@Table(name = "EMP_PERSONAL_INFO")
public class EmpPersonalInfo {


    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MOTHER_NAME")
    private String motherName;

    @Column(name = "FATHER_NAME")
    private String fatherName;

    @Column(name = "AADHAAR")
    private Number aadhaar;

    @Column(name = "PAN")
    private String pan;

    @Column(name = "UAN")
    private String uan;

    @Column(name = "PASSPORT")
    private String passport;

    @Column(name = "PASSPORT_VALID_TILL")
    private Timestamp passportValidTill;

    @Column(name = "VISA")
    private String visa;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    private Timestamp updatedOn;


}
