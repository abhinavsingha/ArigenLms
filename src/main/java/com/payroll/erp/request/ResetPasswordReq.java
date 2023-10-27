package com.payroll.erp.request;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class ResetPasswordReq {
    private String empCode;
    private String otp;
    private String newPassword;
    private String reEnterPassword;
}
