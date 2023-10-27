package com.payroll.erp.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PersonalInfoReq {

    private String empCode;
    private String name;
    private String motherName;
    private String fatherName;
    private Number aadhaar;
    private String pan;
    private String uan;
    private String passport;
    private Timestamp passportValidTill;
    private String visa;

}
