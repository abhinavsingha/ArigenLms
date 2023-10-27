package com.payroll.erp.service;

import com.payroll.erp.request.*;
import com.payroll.erp.responce.ApiResponse;
import com.payroll.erp.responce.DefaultResponse;
import com.payroll.erp.responce.FilePathResponse;


import java.util.List;

public interface UsersService {

    ApiResponse<DefaultResponse> saveEmpJoiningInfo(UserDetailsReq userDetailsReq);
    ApiResponse<DefaultResponse> saveEmpPersonalInfo(PersonalInfoReq personalInfoReq);
    ApiResponse<DefaultResponse> saveEmpBankInfo(EmpBankReq empBankReq);
    ApiResponse<DefaultResponse> saveEmpAddressInfo(List<EmpAddressReq> empAddressReq);
    ApiResponse<DefaultResponse> saveEmpEduInfo(List<EmpEduInfoReq> empEduInfoReq);
    ApiResponse<DefaultResponse> saveEmpProfInfo(List<EmpProfessionalInfoReq> empProfessionalInfoReq);
    ApiResponse<List<FilePathResponse>> getSalrySlip(String empCode);
    ApiResponse<List<FilePathResponse>> getTdsSlip(String empCode);
    ApiResponse<List<UserDetailsReq>> getAllEmployee();
    ApiResponse<List<UserDetailsReq>> getEmployee(String empCode);
//    ApiResponse<DefaultResponse> updateEmp(UserDetailsReq userDetailsReq);
}
