package com.payroll.erp.jwt;

import com.payroll.erp.responce.RoleInfoResp;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class JwtResponce {

    private String jwtToken;
    private String username;
    private ArrayList<RoleInfoResp> role;

}
