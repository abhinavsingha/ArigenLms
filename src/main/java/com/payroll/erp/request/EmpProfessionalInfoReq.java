package com.payroll.erp.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class EmpProfessionalInfoReq {

    private String empCode;
    private String companyName;
    private String companyAddress;
    private String companyContact;
    private String preEmpId;
    private String designation;
    private String ctc;
    private Timestamp fromDate;
    private Timestamp toDate;
}
