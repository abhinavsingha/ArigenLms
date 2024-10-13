package com.lms.controller;

import com.lms.entities.MasRole;
import com.lms.jwt.CustomeUserDetailsImpl;
import com.lms.jwt.JwtHelper;
import com.lms.jwt.JwtRequest;
import com.lms.jwt.JwtResponce;
import com.lms.responce.*;
import com.lms.entities.UsersInfo;
import com.lms.request.PasswordChangeReq;
import com.lms.request.ResetPasswordReq;
import com.lms.request.UserCreationReq;
import com.lms.service.AuthService;
import com.lms.service.Impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "${angular.corss.url}")
@RestController
@Tag(name = "authController",description = "This controller only for use User Related any task.")
@RequestMapping("/authController")
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthServiceImpl authServiceImpl;
    @Autowired
    private CustomeUserDetailsImpl userDetailsServiceImpl;
    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/create-first-users")
    public UsersInfo createUser(@RequestBody UsersInfo usersInfo){
        return authService.createFirstUser(usersInfo);
    }

    @GetMapping("/getEmpName/{empCode}")
    public ResponseEntity<ApiResponse<UserInfoResp>> getEmpName(@PathVariable(value = "empCode") String empCode) {
        return new ResponseEntity<>(authService.getEmpName(empCode), HttpStatus.OK);
    }
    @Operation(summary = "This method is used to get Token for access any API.")
    @PostMapping("/login")
    public ResponseEntity<JwtResponce> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);
        ArrayList<RoleInfoResp> obj= (ArrayList<RoleInfoResp>) authServiceImpl.getRole(request.getUsername());
        JwtResponce response = JwtResponce.builder()
                .jwtToken(token)
                .username(userDetails.getUsername())
                .role(obj).build();
        logger.info("hello test");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @GetMapping("/curentUser")
    public String getCurrentUsers(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/CreateUsers")
    public ResponseEntity<ApiResponse<DefaultResponse>> CreateUsers(@RequestBody UserCreationReq userCreationReq) {
        return new ResponseEntity<>(authService.CreateUsers(userCreationReq), HttpStatus.OK);
    }
    @PutMapping("/UpdateUsers")
    public ResponseEntity<ApiResponse<DefaultResponse>> UpdateUsers(@RequestBody UserCreationReq userCreationReq) {
        return new ResponseEntity<>(authService.UpdateUsers(userCreationReq), HttpStatus.OK);
    }
    @GetMapping("/userActiveOrDeactive/{empCode}/{status}")
    public ResponseEntity<ApiResponse<DefaultResponse>> userActiveOrDeactive(@PathVariable(value = "empCode") String empCode,@PathVariable(value = "status") Boolean status) {
        return new ResponseEntity<>(authService.userActiveOrDeactive(empCode,status), HttpStatus.OK);
    }
    @GetMapping("/removeRole/{empCode}/{rollIds}")
    public ResponseEntity<ApiResponse<DefaultResponse>> removeRole(@PathVariable(value = "empCode") String empCode,@PathVariable(value = "rollIds") String rollIds) {
        return new ResponseEntity<>(authService.removeRole(empCode,rollIds), HttpStatus.OK);
    }
    @GetMapping("/getMasRoleList")
    public ResponseEntity<ApiResponse<List<MasRole>>> getMasRoleList() {
        return new ResponseEntity<>(authService.getMasRoleList(), HttpStatus.OK);
    }
    @GetMapping("/getUsers/{empCode}")
    public ResponseEntity<ApiResponse<UserDetailsResp>> getUsers(@PathVariable(value = "empCode") String empCode) {
        return new ResponseEntity<>(authService.getUsers(empCode), HttpStatus.OK);
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse<List<UserDetailsResp>>> getAllUsers() {
        return new ResponseEntity<>(authService.getAllUsers(), HttpStatus.OK);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponse<DefaultResponse>> changePassword(@RequestBody PasswordChangeReq request) {
        return new ResponseEntity<>(authService.changePassword(request), HttpStatus.OK);
    }
    @GetMapping("/sendOTP/{empCode}")
    public ResponseEntity<ApiResponse<DefaultResponse>> sendOTP(@PathVariable(value = "empCode") String empCode) {
        return new ResponseEntity<>(authService.sendOTP(empCode), HttpStatus.OK);
    }
    @GetMapping("/sendOtpOnMobileNo/{empCode}")
    public ResponseEntity<ApiResponse<DefaultResponse>> sendOtpOnMobileNo(@PathVariable(value = "empCode") String empCode) {
        return new ResponseEntity<>(authService.sendOtpOnMobileNo(empCode), HttpStatus.OK);
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponse<DefaultResponse>> resetPassword(@RequestBody ResetPasswordReq resetPasswordReq) {
        return new ResponseEntity<>(authService.resetPassword(resetPasswordReq), HttpStatus.OK);
    }

}
