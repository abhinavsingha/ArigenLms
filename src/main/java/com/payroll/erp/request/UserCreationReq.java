package com.payroll.erp.request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserCreationReq {
        private String empCode;
        private String name;
        private String email;
        private String mobileNo;
        private String curPassword;
        private String roleId;
}
