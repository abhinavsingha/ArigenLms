package com.payroll.erp.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class UserDetailsReq {
    private String empCode;
    private String empType;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String bloodGroup;
    private String maritalStatus;
    private String designation;
    private String ctc;
    private Number contactNo;
    private Number emgContactNo;
    private String email;
    private String shiftType;
    private Timestamp joinDate;
    private Timestamp probationDate;
    private Timestamp resgnDate;
    private String reasonForLeaving;
    private Timestamp lwd;
    private List<EmpAddressReq> empAddress;
    private List<EmpBankReq> empBanks;
    private List<EmpEduInfoReq> empEdu;
    private List<EmpProfessionalInfoReq> empProf;
    private List<PersonalInfoReq> empPer;

}
