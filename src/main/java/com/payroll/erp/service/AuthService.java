package com.payroll.erp.service;

import com.payroll.erp.entities.MasRole;
import com.payroll.erp.entities.UsersInfo;
import com.payroll.erp.jwt.JwtRequest;
import com.payroll.erp.request.PasswordChangeReq;
import com.payroll.erp.request.ResetPasswordReq;
import com.payroll.erp.request.UserCreationReq;
import com.payroll.erp.responce.ApiResponse;
import com.payroll.erp.responce.DefaultResponse;
import com.payroll.erp.responce.UserDetailsResp;
import com.payroll.erp.responce.UserInfoResp;

import java.util.List;

public interface AuthService {

    UsersInfo createFirstUser(UsersInfo usersInfo);
    ApiResponse<UserInfoResp> getEmpName(String empCode);
    ApiResponse<DefaultResponse> CreateUsers(UserCreationReq userCreationReq);
    ApiResponse<DefaultResponse> UpdateUsers(UserCreationReq userCreationReq);
    ApiResponse<DefaultResponse> userActiveOrDeactive(String empCode,Boolean status);
    ApiResponse<DefaultResponse> removeRole(String empCode,String rollIds);
    ApiResponse<List<MasRole>> getMasRoleList();
    ApiResponse<UserDetailsResp> getUsers(String empCode);
    ApiResponse<List<UserDetailsResp>> getAllUsers();
    ApiResponse<DefaultResponse> changePassword(PasswordChangeReq request);
    ApiResponse<DefaultResponse> sendOTP(String empCode);
    ApiResponse<DefaultResponse> sendOtpOnMobileNo(String empCode);
    ApiResponse<DefaultResponse> resetPassword(ResetPasswordReq resetPasswordReq);
}
