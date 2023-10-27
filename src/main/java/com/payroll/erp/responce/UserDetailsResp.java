package com.payroll.erp.responce;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserDetailsResp {
    private String name;
    private String email;
    private String username;
    private String curPassword;
    private List<RoleInfoResp> role;
}
