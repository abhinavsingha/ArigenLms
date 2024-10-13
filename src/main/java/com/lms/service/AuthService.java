package com.lms.service;

import com.lms.entities.MasRole;
import com.lms.request.PasswordChangeReq;
import com.lms.request.ResetPasswordReq;
import com.lms.responce.ApiResponse;
import com.lms.responce.DefaultResponse;
import com.lms.responce.UserDetailsResp;
import com.lms.responce.UserInfoResp;
import com.lms.entities.UsersInfo;
import com.lms.request.UserCreationReq;

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
