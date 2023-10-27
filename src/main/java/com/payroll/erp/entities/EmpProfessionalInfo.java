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
@Table(name = "EMP_PROFESSIONAL_INFO")
public class EmpProfessionalInfo {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "COMPANY_ADDRESS")
    private String companyAddress;

    @Column(name = "COMPANY_CONTACT")
    private String companyContact;

    @Column(name = "PREV_EMP_ID")
    private String preEmpId;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "CTC")
    private String ctc;

    @Column(name = "FROM_DATE")
    private Timestamp fromDate;

    @Column(name = "TO_DATE")
    private Timestamp toDate;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED_ON", nullable = false)
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    private Timestamp updatedOn;
}
