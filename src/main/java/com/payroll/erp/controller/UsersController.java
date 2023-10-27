package com.payroll.erp.controller;


import com.payroll.erp.request.*;
import com.payroll.erp.responce.ApiResponse;
import com.payroll.erp.responce.DefaultResponse;
import com.payroll.erp.responce.FilePathResponse;
import com.payroll.erp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "${angular.corss.url}")
@RestController
@RequestMapping("/usersController")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/saveEmpJoiningInfo")
    public ResponseEntity<ApiResponse<DefaultResponse>> saveEmpJoiningInfo(@RequestBody UserDetailsReq userDetailsReq) {
        return new ResponseEntity<>(usersService.saveEmpJoiningInfo(userDetailsReq), HttpStatus.OK);
    }
    @PostMapping("/saveEmpPersonalInfo")
    public ResponseEntity<ApiResponse<DefaultResponse>> saveEmpPersonalInfo(@RequestBody PersonalInfoReq personalInfoReq) {
        return new ResponseEntity<>(usersService.saveEmpPersonalInfo(personalInfoReq), HttpStatus.OK);
    }
    @PostMapping("/saveEmpBankInfo")
    public ResponseEntity<ApiResponse<DefaultResponse>> saveEmpBankInfo(@RequestBody EmpBankReq empBankReq) {
        return new ResponseEntity<>(usersService.saveEmpBankInfo(empBankReq), HttpStatus.OK);
    }
    @PostMapping("/saveEmpAddressInfo")
    public ResponseEntity<ApiResponse<DefaultResponse>> saveEmpAddressInfo(@RequestBody List<EmpAddressReq> empAddressReq) {
        return new ResponseEntity<>(usersService.saveEmpAddressInfo(empAddressReq), HttpStatus.OK);
    }
    @PostMapping("/saveEmpEduInfo")
    public ResponseEntity<ApiResponse<DefaultResponse>> saveEmpEduInfo(@RequestBody List<EmpEduInfoReq> empEduInfoReq) {
        return new ResponseEntity<>(usersService.saveEmpEduInfo(empEduInfoReq), HttpStatus.OK);
    }
    @PostMapping("/saveEmpProfInfo")
    public ResponseEntity<ApiResponse<DefaultResponse>> saveEmpProfInfo(@RequestBody List<EmpProfessionalInfoReq> empProfessionalInfoReq) {
        return new ResponseEntity<>(usersService.saveEmpProfInfo(empProfessionalInfoReq), HttpStatus.OK);
    }
    @GetMapping("/getSalrySlip/{empCode}")
    public ResponseEntity<ApiResponse<List<FilePathResponse>>> getSalrySlip(@PathVariable(value = "empCode") String empCode) {
        return new ResponseEntity<>(usersService.getSalrySlip(empCode), HttpStatus.OK);
    }
    @GetMapping("/getAttendanceRpt/{empCode}")
    public ResponseEntity<ApiResponse<List<FilePathResponse>>> getAttendanceRpt(@PathVariable(value = "empCode") String empCode) {
        return new ResponseEntity<>(usersService.getAttendanceRpt(empCode), HttpStatus.OK);
    }
    @GetMapping("/getTdsSlip/{empCode}")
    public ResponseEntity<ApiResponse<List<FilePathResponse>>> getTdsSlip(@PathVariable(value = "empCode") String empCode) {
        return new ResponseEntity<>(usersService.getTdsSlip(empCode), HttpStatus.OK);
    }
    @GetMapping("/getAllEmployee")
    public ResponseEntity<ApiResponse<List<UserDetailsReq>>> getAllEmployee() {
        return new ResponseEntity<>(usersService.getAllEmployee(), HttpStatus.OK);
    }
    @GetMapping("/getEmployee/{empCode}")
    public ResponseEntity<ApiResponse<List<UserDetailsReq>>> getEmployee(@PathVariable(value = "empCode") String empCode) {
        return new ResponseEntity<>(usersService.getEmployee(empCode), HttpStatus.OK);
    }

//    @PutMapping("/updateEmp/{empCode}")
//    public ResponseEntity<ApiResponse<DefaultResponse>> updateEmp(@RequestBody UserDetailsReq userDetailsReq) {
//        return new ResponseEntity<>(usersService.updateEmp(userDetailsReq), HttpStatus.OK);
//    }
}
