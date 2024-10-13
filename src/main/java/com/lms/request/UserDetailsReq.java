package com.lms.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Data
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

}
