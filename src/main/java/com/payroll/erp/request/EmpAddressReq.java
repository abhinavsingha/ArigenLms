package com.payroll.erp.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpAddressReq {
    private String empCode;
    private String addressType;
    private String country;
    private String state;
    private String district;
    private Number pinCode;
    private String addressLine1;
    private String addressLine2;
}
