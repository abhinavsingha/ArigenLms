package com.lms.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class UserCreationReq {
        private String empCode;
        private String name;
        private String email;
        private String mobileNo;
        private String curPassword;
        private String roleId;
}
