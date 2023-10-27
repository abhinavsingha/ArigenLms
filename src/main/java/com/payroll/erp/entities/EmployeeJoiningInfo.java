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
@Table(name = "EMP_JOINING_INFO")
public class EmployeeJoiningInfo {


    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "EMP_TYPE")
    private String empType;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "BLOOD_GROUP")
    private String bloodGroup;

    @Column(name = "Marital_STATUS")
    private String maritalStatus;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "CTC")
    private String ctc;

    @Column(name = "CONTACT_NO")
    private Number contactNo;

    @Column(name = "EMG_CONTACT_NO")
    private Number emgContactNo;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SHIFT_TYPE")
    private String shiftType;

    @Column(name = "JOIN_DATE")
    private Timestamp joinDate;

    @Column(name = "PROBATION_DATE")
    private Timestamp probationDate;

    @Column(name = "RESGN_DATE")
    private Timestamp resgnDate;

    @Column(name = "REASON_FOR_LEAVING")
    private String reasonForLeaving;

    @Column(name = "LWD")
    private Timestamp lwd;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    private Timestamp updatedOn;






}
