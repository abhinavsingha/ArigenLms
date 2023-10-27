package com.payroll.erp.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpBankReq {
    private String empCode;
    private String bankName;
    private Number accNo;
    private String ifsc;
    private String branch;
    private String address;
}
