package com.payroll.erp.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class PasswordChangeReq {
    private String username;
    private String currentPassword;
    private String newPassword;
}
