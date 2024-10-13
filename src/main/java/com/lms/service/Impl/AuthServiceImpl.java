package com.lms.service.Impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.lms.entities.MasRole;
import com.lms.entities.repo.MasRoleRepo;
import com.lms.entities.repo.UsersInfoRepo;
import com.lms.exception.SDDException;
import com.lms.helperUtil.HelperUtils;
import com.lms.helperUtil.ResponseUtils;
import com.lms.request.PasswordChangeReq;
import com.lms.request.ResetPasswordReq;
import com.lms.responce.*;
import com.lms.entities.UsersInfo;
import com.lms.request.UserCreationReq;
import com.lms.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersInfoRepo usersInfoRepo;
    @Autowired
    private MasRoleRepo masRoleRepo;
    @Autowired
    private JavaMailSender emailSender;

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

//    @Value("${twilio.account.sid}")
//    private String twilioAccountSid;
//    @Value("${twilio.auth.token}")
//    private String twilioAuthToken;
//    @Value("${twilio.phone.number}")
//    private String twilioPhoneNumber;

    public void sendEmail(String toEmail, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        try{
            message.setFrom("arvindrajcs@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(msg);
            emailSender.send(message);
        }catch (Exception e){
            logger.error("An error occurred while sending email", e);
        }
    }

//    public void sendOtp(String to, String msg) {
//        String twilioApiUrl = "https://api.twilio.com/2010-04-01/Accounts/" + twilioAccountSid + "/Messages.json";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(twilioAccountSid, twilioAuthToken);
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("From", twilioPhoneNumber);
//        body.add("To", to);
//        body.add("Body", "Your OTP is: " + msg);
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
//        restTemplate.postForObject(twilioApiUrl, request, String.class);
//    }
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public UsersInfo createFirstUser(UsersInfo usersInfo){

        if (usersInfo.getEmpCode() == null || usersInfo.getEmpCode().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        if (usersInfo.getName() == null || usersInfo.getName().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "NAME CAN NOT BE BLANK");
        }
        if (usersInfo.getCurPassword() == null || usersInfo.getCurPassword().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PASSWORD CAN NOT BE BLANK");
        }
        if (usersInfo.getRoleId() == null || usersInfo.getRoleId().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "ROLE CAN NOT BE BLANK");
        }
        if (usersInfo.getEmail() == null || usersInfo.getEmail().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMAIL CAN NOT BE BLANK");
        }
        if (usersInfo.getMobileNo() == null || usersInfo.getMobileNo().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "MOBILE NO CAN NOT BE BLANK");
        }

        try {
            usersInfo.setId(HelperUtils.getUserId());
            usersInfo.setEmpCode(usersInfo.getEmpCode());
            usersInfo.setName(usersInfo.getName());
            usersInfo.setEmail(usersInfo.getEmail());
            usersInfo.setMobileNo(usersInfo.getMobileNo());
            usersInfo.setUsername(usersInfo.getName()+usersInfo.getEmpCode());
            usersInfo.setCurPassword(passwordEncoder.encode(usersInfo.getCurPassword()));
            usersInfo.setOldPassword(passwordEncoder.encode(usersInfo.getCurPassword()));
            usersInfo.setRoleId(usersInfo.getRoleId());
            usersInfo.setIsActive(true);
            usersInfo.setCreatedOn(HelperUtils.getCurrentTimeStamp());
            usersInfo.setUpdatedOn(HelperUtils.getCurrentTimeStamp());

        } catch (Exception e) {
            logger.error("An error occurred while creating firstUser", e);
        }
        return usersInfoRepo.save(usersInfo);
    }

    public List<RoleInfoResp> getRole(String username){
        UsersInfo isActiveUser = usersInfoRepo.findByUsername(username);
        List<RoleInfoResp> obj =new ArrayList<RoleInfoResp>();
        String[] roleArrays = isActiveUser.getRoleId().split(",");
        try {
            for (String item: roleArrays) {
                RoleInfoResp rolResp = new RoleInfoResp();
                String rollCode=item;
                MasRole roleObj=masRoleRepo.findByRoleCodeAndIsActive(rollCode,true);
                if(roleObj!=null){
                    rolResp.setUserFullName(isActiveUser.getName());
                    rolResp.setRoleCode(roleObj.getRoleCode());
                    rolResp.setRoleDesc(roleObj.getRoleDesc());
                    rolResp.setIsActive(roleObj.getIsActive());
                }
                obj.add(rolResp);
            }
        } catch (Exception e) {
            logger.error("An error occurred while getting role list", e);
        }
        return obj;
    }

    @Override
    public ApiResponse<UserInfoResp> getEmpName(String empCode) {
        UserInfoResp resp = new UserInfoResp();
        if (empCode == null || empCode.isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        List<UsersInfo> isActiveUser = usersInfoRepo.findByEmpCodeAndIsActive(empCode, true);
        if (isActiveUser.size()<=0) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "ACTIVE USER NOT FOUND");
        }
        try {
            resp.setName(isActiveUser.get(0).getName());
            resp.setUsername(isActiveUser.get(0).getUsername());
        } catch (Exception e) {
            logger.error("An error occurred while getting emp name", e);
        }

        return ResponseUtils.createSuccessResponse(resp, new TypeReference<UserInfoResp>() {
        });

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> CreateUsers(UserCreationReq userCreationReq) {

        DefaultResponse defaultResponse = new DefaultResponse();
        if (userCreationReq.getEmpCode() == null || userCreationReq.getEmpCode().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        if (userCreationReq.getName() == null || userCreationReq.getName().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "NAME CAN NOT BE BLANK");
        }
        if (userCreationReq.getCurPassword() == null || userCreationReq.getCurPassword().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PASSWORD CAN NOT BE BLANK");
        }
        if (userCreationReq.getRoleId() == null || userCreationReq.getRoleId().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "ROLE CAN NOT BE BLANK");
        }
        if (userCreationReq.getEmail() == null || userCreationReq.getEmail().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMAIL CAN NOT BE BLANK");
        }
        if (userCreationReq.getMobileNo() == null || userCreationReq.getMobileNo().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "MOBILE NO CAN NOT BE BLANK");
        }
        UsersInfo userObj = usersInfoRepo.findByEmpCode(userCreationReq.getEmpCode());
        if (userObj !=null) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER ALREADY EXISTS");
        }
        try {
            UsersInfo obj =new UsersInfo();
            obj.setId(HelperUtils.getUserId());
            obj.setEmpCode(userCreationReq.getEmpCode());
            obj.setName(userCreationReq.getName());
            obj.setEmail(userCreationReq.getEmail());
            obj.setMobileNo(userCreationReq.getMobileNo());
            obj.setUsername(userCreationReq.getName()+userCreationReq.getEmpCode());
            obj.setCurPassword(passwordEncoder.encode(userCreationReq.getCurPassword()));
            obj.setOldPassword(passwordEncoder.encode(userCreationReq.getCurPassword()));
            obj.setRoleId(userCreationReq.getRoleId());
            obj.setIsActive(true);
            obj.setCreatedOn(HelperUtils.getCurrentTimeStamp());
            obj.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
            usersInfoRepo.save(obj);
            //sendEmail("vikrant2099ad@gmail.com","Test Mail","www.w3schools.com");
        } catch (Exception e) {
            logger.error("An error occurred while creating users", e);
        }

        defaultResponse.setMsg("USER Created successfully");
        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> UpdateUsers(UserCreationReq userCreationReq) {

        DefaultResponse defaultResponse = new DefaultResponse();
//        if (userCreationReq.getName() == null || userCreationReq.getName().isEmpty()) {
//            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "NAME CAN NOT BE BLANK");
//        }
//        if (userCreationReq.getCurPassword() == null || userCreationReq.getCurPassword().isEmpty()) {
//            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PASSWORD CAN NOT BE BLANK");
//        }
//        if (userCreationReq.getRoleId() == null || userCreationReq.getRoleId().isEmpty()) {
//            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "ROLE CAN NOT BE BLANK");
//        }
//        if (userCreationReq.getEmail() == null || userCreationReq.getEmail().isEmpty()) {
//            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMAIL CAN NOT BE BLANK");
//        }
        UsersInfo userUpdtObj = usersInfoRepo.findByEmpCode(userCreationReq.getEmpCode());
        if (userUpdtObj !=null) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER IS ALREADY EXISTS");
        }
        try {
            userUpdtObj.setName(userCreationReq.getName());
            userUpdtObj.setUsername(userCreationReq.getName()+userCreationReq.getEmpCode());
            userUpdtObj.setEmail(userCreationReq.getEmail());
            userUpdtObj.setCurPassword(passwordEncoder.encode(userCreationReq.getCurPassword()));
            // userUpdtObj.setOldPassword(passwordEncoder.encode(userCreationReq.getCurPassword()));
            userUpdtObj.setRoleId(userCreationReq.getRoleId());
            userUpdtObj.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
            usersInfoRepo.save(userUpdtObj);
        } catch (Exception e) {
            logger.error("An error occurred while updating users", e);
        }
        defaultResponse.setMsg("USER Updated successfully");
        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> userActiveOrDeactive(String empCode,Boolean status) {

        DefaultResponse defaultResponse = new DefaultResponse();
        if (empCode == null || empCode.isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        if (status == null) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "STATUS CAN NOT BE BLANK");
        }
        UsersInfo userObj = usersInfoRepo.findByEmpCode(empCode);
        if (userObj ==null) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER NOT FOUND");
        }
        try {
            if(status==true){
                userObj.setIsActive(true);
                userObj.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
                defaultResponse.setMsg("User Activate successfully");
                usersInfoRepo.save(userObj);
            }else{
                userObj.setIsActive(false);
                userObj.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
                defaultResponse.setMsg("User DeActivate successfully");
                usersInfoRepo.save(userObj);
            }
        } catch (Exception e) {
            logger.error("An error occurred while activate/deactivate users", e);
        }

        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> removeRole(String empCode,String rollIds) {

        DefaultResponse defaultResponse = new DefaultResponse();
        if (empCode == null || empCode.isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        if (rollIds == null || rollIds.isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "ROLE ID CAN NOT BE BLANK");
        }
        UsersInfo userObj = usersInfoRepo.findByEmpCode(empCode);
        if (userObj ==null) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER NOT FOUND");
        }
        try {
            String[] r1 = userObj.getRoleId().split(",");
            List<String> userExistingRole = new ArrayList<>(Arrays.asList(r1));

            String[] r2 = rollIds.split(",");
            List<String> roleForRemoval = new ArrayList<>(Arrays.asList(r2));

            userExistingRole.removeAll(roleForRemoval);

            String newRoll="";
            for (String item: userExistingRole) {
                newRoll += item+",";
            }
            userObj.setRoleId(newRoll);
            userObj.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
            usersInfoRepo.save(userObj);
            if (newRoll==null || newRoll.isEmpty()){
                userObj.setIsActive(false);
                userObj.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
                usersInfoRepo.save(userObj);
                defaultResponse.setMsg("User Activate successfully");
            }        } catch (Exception e) {
            logger.error("An error occurred while removing role", e);
        }
        defaultResponse.setMsg("Role Remove successfully");
        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }
    @Override
    public ApiResponse<List<MasRole>> getMasRoleList() {
       List<MasRole> role=masRoleRepo.findAll();
        List<MasRole> roles=role.stream().filter(e->e.getIsActive().equals(true)).collect(Collectors.toList());
       if(roles.isEmpty()){
           throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "NO ANY ROLE FOUND FROM DB");
       }
        return ResponseUtils.createSuccessResponse(roles, new TypeReference<List<MasRole>>() {
        });
    }
    @Override
    public ApiResponse<UserDetailsResp> getUsers(String empCode) {
        UserDetailsResp obj =new UserDetailsResp();
        if (empCode == null || empCode.isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        UsersInfo userObj=usersInfoRepo.findByEmpCode(empCode);
        if(userObj ==null){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER NOT FOUND FROM DB");
        }
        List<RoleInfoResp> roleObj=new ArrayList<RoleInfoResp>();
        try {
            obj.setName(userObj.getName());
            obj.setUsername(userObj.getUsername());
            obj.setEmail(userObj.getEmail());
            //obj.setCurPassword(userObj.getCurPassword());
            String[] roleArrays = userObj.getRoleId().split(",");
            for (String item: roleArrays) {
                RoleInfoResp rolResp = new RoleInfoResp();
                String rollCode=item;
                MasRole roleObjs=masRoleRepo.findByRoleCodeAndIsActive(rollCode,true);
                if(roleObjs!=null){
                    rolResp.setUserFullName(userObj.getName());
                    rolResp.setRoleCode(roleObjs.getRoleCode());
                    rolResp.setRoleDesc(roleObjs.getRoleDesc());
                    rolResp.setIsActive(roleObjs.getIsActive());
                }
                roleObj.add(rolResp);
            }
            obj.setRole(roleObj);
        } catch (Exception e) {
            logger.error("An error occurred while getting users", e);
        }
        return ResponseUtils.createSuccessResponse(obj, new TypeReference<UserDetailsResp>() {
        });
    }

    @Override
    public ApiResponse<List<UserDetailsResp>> getAllUsers() {
        List<UserDetailsResp> objct =new ArrayList<UserDetailsResp>();

        List<UsersInfo> userObjct=usersInfoRepo.findAll();
        if(userObjct.isEmpty()){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER NOT FOUND FROM DB");
        }
        try {
            for(UsersInfo items:userObjct){
                UserDetailsResp usrObj=new UserDetailsResp();
                usrObj.setName(items.getName());
                usrObj.setUsername(items.getUsername());
                usrObj.setEmail(items.getEmail());
                List<RoleInfoResp> roleObj=new ArrayList<RoleInfoResp>();
                String[] roleArrays = items.getRoleId().split(",");
                for (String item: roleArrays) {
                    RoleInfoResp rolResp = new RoleInfoResp();
                    String rollCode=item;
                    MasRole roleObjs=masRoleRepo.findByRoleCodeAndIsActive(rollCode,true);
                    if(roleObjs!=null){
                        rolResp.setRoleCode(roleObjs.getRoleCode());
                        rolResp.setRoleDesc(roleObjs.getRoleDesc());
                        rolResp.setIsActive(roleObjs.getIsActive());
                    }
                    roleObj.add(rolResp);
                }
                usrObj.setRole(roleObj);
                objct.add(usrObj);
            }
            //sendEmail("vikrant2099ad@gmail.com","Test Mail","www.w3schools.com");
        }catch (Exception e) {
            logger.error("An error occurred while getting all users", e);
        }
        return ResponseUtils.createSuccessResponse(objct, new TypeReference<List<UserDetailsResp>>() {
        });
    }

    @Override
    public ApiResponse<DefaultResponse> changePassword(PasswordChangeReq request) {

        DefaultResponse resp=new DefaultResponse();
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USERNAME CAN NOT BE BLANK");
        }
        if (request.getCurrentPassword() == null || request.getCurrentPassword().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "CURRENT PASSWORD CAN NOT BE BLANK");
        }
        if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "NEW PASSWORD CAN NOT BE BLANK");
        }
        UsersInfo userObjct=usersInfoRepo.findByUsername(request.getUsername());
        if(userObjct ==null){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER NOT FOUND FROM DB");
        }
        try {
            boolean isPasswordMatch = passwordEncoder.matches(request.getCurrentPassword(), userObjct.getCurPassword());
            boolean newpswd = passwordEncoder.matches(request.getNewPassword(), userObjct.getCurPassword());

            if (isPasswordMatch==true) {
                if(newpswd==true){
                    resp.setMsg("CURRENT PASSWORD & NEW PASSWORD CAN NOT BE SAME");
                }else{
                    userObjct.setCurPassword(passwordEncoder.encode(request.getNewPassword()));
                    userObjct.setOldPassword(passwordEncoder.encode(request.getNewPassword()));
                    userObjct.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
                    usersInfoRepo.save(userObjct);
                    resp.setMsg("PASSWORD CHANGED SUCCESSFULLY");
                }
            } else {
                resp.setMsg("CURRENT PASSWORD NOT MATCHED");
            }
        } catch (Exception e) {
            logger.error("An error occurred while changing password", e);
        }
        return ResponseUtils.createSuccessResponse(resp, new TypeReference<DefaultResponse>() {
        });
    }

    @Override
    public ApiResponse<DefaultResponse> sendOTP(String empCode) {
        DefaultResponse objMsg =new DefaultResponse();
        List<UsersInfo> userObjct=usersInfoRepo.findByEmpCodeAndIsActive(empCode,true);
        if(userObjct.isEmpty()){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER NOT FOUND FROM DB");
        }
        String userEmail=userObjct.get(0).getEmail();
        if(userEmail.isEmpty() || userEmail==null){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER EMAIL NOT FOUND FROM DB");
        }
        try {
            String otp=HelperUtils.getOtp();
            userObjct.get(0).setUpdatedOn(HelperUtils.getCurrentTimeStamp());
            userObjct.get(0).setOtp(otp);
            usersInfoRepo.save(userObjct.get(0));
            String msg= otp +" "+"is The One Time Password(OTP) for Reset Your Password.This OTP is usable only once and is valid for 10 mins. PLS DO NOT SHARE THE OTP WITH ANYONE -Intek Micro Pvt Ltd ";
            sendEmail(userEmail,"Reset Password",msg);
            objMsg.setMsg("OTP sent Successfully on your Registered "+userEmail);
        } catch (Exception e) {
            logger.error("An error occurred while sending OTP on email", e);
        }
        return ResponseUtils.createSuccessResponse(objMsg, new TypeReference<DefaultResponse>() {
        });
    }


    @Override
    public ApiResponse<DefaultResponse> sendOtpOnMobileNo(String empCode) {
        DefaultResponse objMsg =new DefaultResponse();
        List<UsersInfo> userObjct=usersInfoRepo.findByEmpCodeAndIsActive(empCode,true);
        if(userObjct.isEmpty()){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER NOT FOUND FROM DB");
        }
        String userMobileNo=userObjct.get(0).getMobileNo();
        if(userMobileNo.isEmpty() || userMobileNo==null){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER MOBILE NO NOT FOUND FROM DB");
        }
        try {
            String otp=HelperUtils.getOtp();
            userObjct.get(0).setUpdatedOn(HelperUtils.getCurrentTimeStamp());
            userObjct.get(0).setOtp(otp);
            usersInfoRepo.save(userObjct.get(0));
            String msg= otp +" "+"is The One Time Password(OTP) for Reset Your Password.This OTP is usable only once and is valid for 10 mins. PLS DO NOT SHARE THE OTP WITH ANYONE -Intek Micro Pvt Ltd ";
            //sendOtp(userMobileNo,msg);
            objMsg.setMsg("OTP sent Successfully on your Registered Mobile No "+userMobileNo.substring(6,10));
        } catch (Exception e) {
            logger.error("An error occurred while sending OTP on mobile no", e);
        }
        return ResponseUtils.createSuccessResponse(objMsg, new TypeReference<DefaultResponse>() {
        });
    }

    @Override
    public ApiResponse<DefaultResponse> resetPassword(ResetPasswordReq resetPasswordReq) {
        DefaultResponse objMsg =new DefaultResponse();

        if(resetPasswordReq.getEmpCode()==null ||resetPasswordReq.getEmpCode().isEmpty()){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        if(resetPasswordReq.getOtp()==null ||resetPasswordReq.getOtp().isEmpty()){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "OTP CAN NOT BE BLANK");
        }
        if(resetPasswordReq.getNewPassword()==null ||resetPasswordReq.getNewPassword().isEmpty()){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "NEW PASSWORD NOT NOT BE BLANK");
        }
        if(resetPasswordReq.getReEnterPassword()==null ||resetPasswordReq.getReEnterPassword().isEmpty()){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "RE-ENTER PASSWORD NOT NOT BE BLANK");
        }
        if(!resetPasswordReq.getNewPassword().equals(resetPasswordReq.getReEnterPassword())){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "NEW PASSWORD & RE-ENTER PASSWORD MUST BE SAME");
        }
        List<UsersInfo> userObjct=usersInfoRepo.findByEmpCodeAndIsActive(resetPasswordReq.getEmpCode(),true);
        if(userObjct.isEmpty()){
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "USER NOT FOUND FROM DB");
        }
        try {
            String userOtp=userObjct.get(0).getOtp();
            Timestamp time=userObjct.get(0).getUpdatedOn();
            Timestamp currentTime=HelperUtils.getCurrentTimeStamp();
            if(userOtp.equals(resetPasswordReq.getOtp())){
                if(currentTime.getTime() -time.getTime() > 600000){
                    throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "OTP has Expired");
                }else {
                    userObjct.get(0).setCurPassword(passwordEncoder.encode(resetPasswordReq.getNewPassword()));
                    userObjct.get(0).setOldPassword(passwordEncoder.encode(resetPasswordReq.getNewPassword()));
                    usersInfoRepo.save(userObjct.get(0));
                    objMsg.setMsg("Your Password has been Successfully Reset");
                }
            }else{
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "INVALID OTP");
            }
        } catch (Exception e) {
            logger.error("An error occurred while reset password", e);
        }
        return ResponseUtils.createSuccessResponse(objMsg, new TypeReference<DefaultResponse>() {
        });
    }



}
