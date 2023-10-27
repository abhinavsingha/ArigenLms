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
@Table(name = "EMP_BANK_INFO")
public class EmpBankInfo {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "ACC_NO")
    private Number accNo;

    @Column(name = "IFSC")
    private String ifsc;

    @Column(name = "BRANCH")
    private String branch;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED_ON", nullable = false)
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    private Timestamp updatedOn;

}
