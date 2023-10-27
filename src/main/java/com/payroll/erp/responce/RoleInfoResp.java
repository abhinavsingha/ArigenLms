package com.payroll.erp.responce;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class RoleInfoResp {
    private String userFullName;
    private String roleCode;
    private String roleDesc;
    private Boolean isActive;
}
