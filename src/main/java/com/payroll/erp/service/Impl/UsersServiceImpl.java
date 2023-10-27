package com.payroll.erp.service.Impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.payroll.erp.entities.*;
import com.payroll.erp.entities.repo.*;
import com.payroll.erp.exception.SDDException;
import com.payroll.erp.helperUtil.HelperUtils;
import com.payroll.erp.helperUtil.ResponseUtils;
import com.payroll.erp.request.*;
import com.payroll.erp.responce.ApiResponse;
import com.payroll.erp.responce.DefaultResponse;
import com.payroll.erp.responce.FilePathResponse;
import com.payroll.erp.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private EmployeeJoiningInfoRepo employeeJoiningInfoRepo;
    @Autowired
    private EmpPersonalInfoRepo empPersonalInfoRepo;
    @Autowired
    private EmpBankInfoRepo empBankInfoRepo;
    @Autowired
    private EmpAddressInfoRepo empAddressInfoRepo;
    @Autowired
    private EmpEducationalInfoRepo empEducationalInfoRepo;
    @Autowired
    private EmpProfessionalInfoRepo empProfessionalInfoRepo;


    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> saveEmpJoiningInfo(UserDetailsReq userDetailsReq) {

        DefaultResponse defaultResponse = new DefaultResponse();

        if (userDetailsReq.getEmpCode() == null || userDetailsReq.getEmpCode().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        if (userDetailsReq.getEmpType() == null || userDetailsReq.getEmpType().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP TYPE CAN NOT BE BLANK");
        }
        if (userDetailsReq.getFirstName() == null || userDetailsReq.getFirstName().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "FIRST NAME CAN NOT BE BLANK");
        }
        if (userDetailsReq.getGender() == null || userDetailsReq.getGender().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "GENDER CAN NOT BE BLANK");
        }
  /*      if (userDetailsReq.getBloodGroup() == null ) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "BLOOD GROUP CAN NOT BE BLANK");
        }*/
        if (userDetailsReq.getMaritalStatus() == null || userDetailsReq.getMaritalStatus().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "MARITAL STATUS CAN NOT BE BLANK");
        }
        if (userDetailsReq.getDesignation() == null || userDetailsReq.getDesignation().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "DESIGNATION CAN NOT BE BLANK");
        }
        if (userDetailsReq.getCtc() == null || userDetailsReq.getCtc().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "ANNUAL CTC CAN NOT BE BLANK");
        }
        if (userDetailsReq.getContactNo() == null ) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "CONTACT NO CAN NOT BE BLANK");
        }
        if (userDetailsReq.getEmgContactNo() == null) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMG CONTACT NO CAN NOT BE BLANK");
        }
        if (userDetailsReq.getEmail() == null || userDetailsReq.getEmail().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "Email CAN NOT BE BLANK");
        }
        if (userDetailsReq.getShiftType() == null || userDetailsReq.getShiftType().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "SHIFT TYPE CAN NOT BE BLANK");
        }
        if (userDetailsReq.getJoinDate() == null ) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "JOINING DATE CAN NOT BE BLANK");
        }
        if (userDetailsReq.getProbationDate() == null ) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PROBATION DATE CAN NOT BE BLANK");
        }

        List<EmployeeJoiningInfo> isActiveUser = employeeJoiningInfoRepo.findByEmpCodeAndIsActive(userDetailsReq.getEmpCode(), "1");
        if (isActiveUser.size()>0) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "THIS EMPLOYEE CODE IS ALREADY REGISTERED AND ACTIVE");
        }
        try {
            EmployeeJoiningInfo emp= new EmployeeJoiningInfo();
            emp.setId(HelperUtils.getJoiningId());
            emp.setEmpCode(userDetailsReq.getEmpCode());
            emp.setEmpType(userDetailsReq.getEmpType());
            emp.setFirstName(userDetailsReq.getFirstName());
            emp.setMiddleName(userDetailsReq.getMiddleName());
            emp.setLastName(userDetailsReq.getLastName());
            emp.setGender(userDetailsReq.getGender());
            emp.setBloodGroup(userDetailsReq.getBloodGroup());
            emp.setMaritalStatus(userDetailsReq.getMaritalStatus());
            emp.setContactNo(userDetailsReq.getContactNo());
            emp.setEmgContactNo(userDetailsReq.getEmgContactNo());
            emp.setEmail(userDetailsReq.getEmail());
            emp.setDesignation(userDetailsReq.getDesignation());
            emp.setCtc(userDetailsReq.getCtc());
            emp.setShiftType(userDetailsReq.getShiftType());
            emp.setIsActive(true);
            emp.setJoinDate(userDetailsReq.getJoinDate());
            emp.setProbationDate(userDetailsReq.getProbationDate());
            emp.setCreatedOn(HelperUtils.getCurrentTimeStamp());
            emp.setUpdatedOn(HelperUtils.getCurrentTimeStamp());

            employeeJoiningInfoRepo.save(emp);
        } catch (Exception e) {
            logger.error("An error occurred while saving EmployeeJoiningInfo data", e);
        }

        defaultResponse.setMsg("Employee Registration successfully");
        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> saveEmpPersonalInfo(PersonalInfoReq personalInfoReq) {

        DefaultResponse defaultResponse = new DefaultResponse();

        if (personalInfoReq.getEmpCode() == null || personalInfoReq.getEmpCode().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        if (personalInfoReq.getName() == null || personalInfoReq.getName().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP NAME  CAN NOT BE BLANK");
        }
        if (personalInfoReq.getMotherName() == null || personalInfoReq.getMotherName().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "MOTHER NAME CAN NOT BE BLANK");
        }
        if (personalInfoReq.getFatherName() == null || personalInfoReq.getFatherName().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "FATHER NAME CAN NOT BE BLANK");
        }
        if (personalInfoReq.getAadhaar() == null) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "AADHAR NO CAN NOT BE BLANK");
        }
        if (personalInfoReq.getPan() == null || personalInfoReq.getPan().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PAN NO CAN NOT BE BLANK");
        }
        List<EmpPersonalInfo> isActiveUser = empPersonalInfoRepo.findByEmpCodeAndIsActive(personalInfoReq.getEmpCode(), "1");
        if (isActiveUser.size()>0) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "THIS EMPLOYEE DETAILS ARE ALREADY REGISTERED AND ACTIVE");
        }
        try {
            EmpPersonalInfo empObj= new EmpPersonalInfo();
            empObj.setId(HelperUtils.getPernlId());
            empObj.setEmpCode(personalInfoReq.getEmpCode());
            empObj.setName(personalInfoReq.getName());
            empObj.setMotherName(personalInfoReq.getMotherName());
            empObj.setFatherName(personalInfoReq.getFatherName());
            empObj.setAadhaar(personalInfoReq.getAadhaar());
            empObj.setPan(personalInfoReq.getPan());
            empObj.setUan(personalInfoReq.getUan());
            empObj.setPassport(personalInfoReq.getPassport());
            empObj.setPassportValidTill(personalInfoReq.getPassportValidTill());
            empObj.setVisa(personalInfoReq.getVisa());
            empObj.setIsActive(true);
            empObj.setCreatedOn(HelperUtils.getCurrentTimeStamp());
            empObj.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
            empPersonalInfoRepo.save(empObj);
        } catch (Exception e) {
            logger.error("An error occurred while saving EmpPersonalInfo data", e);
        }

        defaultResponse.setMsg("Employee Registration successfully");
        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> saveEmpBankInfo(EmpBankReq empBankReq) {

        DefaultResponse defaultResponse = new DefaultResponse();

        if (empBankReq.getEmpCode() == null || empBankReq.getEmpCode().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
        }
        if (empBankReq.getBankName() == null || empBankReq.getBankName().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "BANK NAME CAN NOT BE BLANK");
        }
        if (empBankReq.getAccNo() == null) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "ACCOUNT NO CAN NOT BE BLANK");
        }
        if (empBankReq.getIfsc() == null || empBankReq.getIfsc().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "IFSC CODE CAN NOT BE BLANK");
        }
        if (empBankReq.getBranch() == null || empBankReq.getBranch().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "BRANCH CAN NOT BE BLANK");
        }
        if (empBankReq.getAddress() == null || empBankReq.getAddress().isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "ADDRESS CAN NOT BE BLANK");
        }
        List<EmpBankInfo> isActiveUser = empBankInfoRepo.findByEmpCodeAndIsActive(empBankReq.getEmpCode(), "1");
        if (isActiveUser.size()>0) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "THIS EMPLOYEE BANK DETAILS ARE ALREADY REGISTERED AND ACTIVE");
        }
        try {
            EmpBankInfo empObj= new EmpBankInfo();
            empObj.setId(HelperUtils.getBankId());
            empObj.setEmpCode(empBankReq.getEmpCode());
            empObj.setBankName(empBankReq.getBankName());
            empObj.setAccNo(empBankReq.getAccNo());
            empObj.setIfsc(empBankReq.getIfsc());
            empObj.setBranch(empBankReq.getBranch());
            empObj.setAddress(empBankReq.getAddress());
            empObj.setIsActive(true);
            empObj.setCreatedOn(HelperUtils.getCurrentTimeStamp());
            empObj.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
            empBankInfoRepo.save(empObj);
        } catch (Exception e) {
            logger.error("An error occurred while saving EmpBankInfo data", e);
        }

        defaultResponse.setMsg("Employee Registration successfully");
        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> saveEmpAddressInfo(List<EmpAddressReq> empAddressReq) {

        DefaultResponse defaultResponse = new DefaultResponse();

        for (Integer i = 0; i < empAddressReq.size(); i++) {

            if (empAddressReq.get(i).getEmpCode() == null || empAddressReq.get(i).getEmpCode().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
            }
            if (empAddressReq.get(i).getCountry() == null || empAddressReq.get(i).getCountry().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "COUNTRY CAN NOT BE BLANK");
            }
            if (empAddressReq.get(i).getState() == null || empAddressReq.get(i).getState().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "STATE CAN NOT BE BLANK");
            }
            if (empAddressReq.get(i).getDistrict() == null || empAddressReq.get(i).getDistrict().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "DISTRICT CAN NOT BE BLANK");
            }
            if (empAddressReq.get(i).getPinCode() == null ) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PIN CODE CAN NOT BE BLANK");
            }
            if (empAddressReq.get(i).getAddressType() == null || empAddressReq.get(i).getAddressType().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "ADDRESS TYPE CAN NOT BE BLANK");
            }
            if (empAddressReq.get(i).getAddressLine1() == null || empAddressReq.get(i).getAddressLine1().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "HOUSE NO/LANDMARK CAN NOT BE BLANK");
            }
        }
        List<EmpAddressInfo> isActiveUser = empAddressInfoRepo.findByEmpCodeAndIsActive(empAddressReq.get(0).getEmpCode(), "1");
        if (isActiveUser.size()>0) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "THIS EMPLOYEE ADDRESS ARE ALREADY REGISTERED AND ACTIVE");
        }
        try {
            for(int i = 0; i < empAddressReq.size(); i++) {
                EmpAddressInfo address = new EmpAddressInfo();
                address.setId(HelperUtils.getAddressId());
                address.setEmpCode(empAddressReq.get(i).getEmpCode());
                address.setAddressType(empAddressReq.get(i).getAddressType());
                address.setCountry(empAddressReq.get(i).getCountry());
                address.setState(empAddressReq.get(i).getState());
                address.setDistrict(empAddressReq.get(i).getDistrict());
                address.setPinCode(empAddressReq.get(i).getPinCode());
                address.setAddressLine1(empAddressReq.get(i).getAddressLine1());
                address.setAddressLine2(empAddressReq.get(i).getAddressLine2());
                address.setIsActive(true);
                address.setCreatedOn(HelperUtils.getCurrentTimeStamp());
                address.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
                empAddressInfoRepo.save(address);
            }
        } catch (Exception e) {
            logger.error("An error occurred while saving EmpAddressInfo data", e);
        }

        defaultResponse.setMsg("Employee Registration successfully");
        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> saveEmpEduInfo(List<EmpEduInfoReq> empEduInfoReq) {

        DefaultResponse defaultResponse = new DefaultResponse();

        for (Integer i = 0; i < empEduInfoReq.size(); i++) {

            if (empEduInfoReq.get(i).getEmpCode() == null || empEduInfoReq.get(i).getEmpCode().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
            }
            if (empEduInfoReq.get(i).getQualification() == null || empEduInfoReq.get(i).getQualification().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "QUALIFICATION CAN NOT BE BLANK");
            }
            if (empEduInfoReq.get(i).getCollage() == null || empEduInfoReq.get(i).getCollage().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "COLLAGE NAME CAN NOT BE BLANK");
            }
            if (empEduInfoReq.get(i).getUniversity() == null || empEduInfoReq.get(i).getUniversity().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "UNIVERSITY NAME CAN NOT BE BLANK");
            }
            if (empEduInfoReq.get(i).getCourse() == null || empEduInfoReq.get(i).getCourse().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "COURSE CAN NOT BE BLANK");
            }
            if (empEduInfoReq.get(i).getPassingYr() == null || empEduInfoReq.get(i).getPassingYr().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PASSING YEAR CAN NOT BE BLANK");
            }
            if (empEduInfoReq.get(i).getDiv() == null || empEduInfoReq.get(i).getDiv().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "DIVISION/CLASS CAN NOT BE BLANK");
            }
            if (empEduInfoReq.get(i).getPercentage() == null || empEduInfoReq.get(i).getPercentage().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PERCENTAGE CAN NOT BE BLANK");
            }
        }
        List<EmpEducationalInfo> isActiveUser = empEducationalInfoRepo.findByEmpCodeAndIsActive(empEduInfoReq.get(0).getEmpCode(), "1");
        if (isActiveUser.size()>0) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "THIS EMPLOYEE EDUCATION DETAILS ARE ALREADY REGISTERED AND ACTIVE");
        }
        try {
            for(int i = 0; i < empEduInfoReq.size(); i++) {
                EmpEducationalInfo address = new EmpEducationalInfo();
                address.setId(HelperUtils.getEduId());
                address.setEmpCode(empEduInfoReq.get(i).getEmpCode());
                address.setQualification(empEduInfoReq.get(i).getQualification());
                address.setCollage(empEduInfoReq.get(i).getCollage());
                address.setUniversity(empEduInfoReq.get(i).getUniversity());
                address.setCourse(empEduInfoReq.get(i).getCourse());
                address.setPassingYr(empEduInfoReq.get(i).getPassingYr());
                address.setDiv(empEduInfoReq.get(i).getDiv());
                address.setPercentage(empEduInfoReq.get(i).getPercentage());
                address.setIsActive(true);
                address.setCreatedOn(HelperUtils.getCurrentTimeStamp());
                address.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
                empEducationalInfoRepo.save(address);
            }
        } catch (Exception e) {
            logger.error("An error occurred while saving EmpEducationalInfo data", e);
        }

        defaultResponse.setMsg("Employee Registration successfully");
        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse<DefaultResponse> saveEmpProfInfo(List<EmpProfessionalInfoReq> empProfessionalInfoReq) {

        DefaultResponse defaultResponse = new DefaultResponse();

        for (Integer i = 0; i < empProfessionalInfoReq.size(); i++) {

            if (empProfessionalInfoReq.get(i).getEmpCode() == null || empProfessionalInfoReq.get(i).getEmpCode().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMP CODE CAN NOT BE BLANK");
            }
            if (empProfessionalInfoReq.get(i).getCompanyName() == null || empProfessionalInfoReq.get(i).getCompanyName().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "COMPANY NAME CAN NOT BE BLANK");
            }
            if (empProfessionalInfoReq.get(i).getCompanyAddress() == null || empProfessionalInfoReq.get(i).getCompanyAddress().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "COMPANY ADDRESS CAN NOT BE BLANK");
            }
            if (empProfessionalInfoReq.get(i).getPreEmpId() == null || empProfessionalInfoReq.get(i).getPreEmpId().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PREVIOUS EMP ID CAN NOT BE BLANK");
            }
            if (empProfessionalInfoReq.get(i).getDesignation() == null || empProfessionalInfoReq.get(i).getDesignation().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "LAST DESIGNATION CAN NOT BE BLANK");
            }
   /*         if (empProfessionalInfoReq.get(i).getCompanyContact() == null || empProfessionalInfoReq.get(i).getCompanyContact().isEmpty()) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "PREVIOUS COMPANY CONTACT CAN NOT BE BLANK");
            }*/
            if (empProfessionalInfoReq.get(i).getFromDate() == null ) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "FROM DATE CAN NOT BE BLANK");
            }
            if (empProfessionalInfoReq.get(i).getToDate() == null ) {
                throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "TO DATE CAN NOT BE BLANK");
            }
        }
        List<EmpProfessionalInfo> isActiveUser = empProfessionalInfoRepo.findByEmpCodeAndIsActive(empProfessionalInfoReq.get(0).getEmpCode(), "1");
        if (isActiveUser.size()>0) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "THIS EMPLOYEE EDUCATION DETAILS ARE ALREADY REGISTERED AND ACTIVE");
        }
        try {
            for(int i = 0; i < empProfessionalInfoReq.size(); i++) {
                EmpProfessionalInfo proResp = new EmpProfessionalInfo();
                proResp.setId(HelperUtils.getProfId());
                proResp.setEmpCode(empProfessionalInfoReq.get(i).getEmpCode());
                proResp.setCompanyName(empProfessionalInfoReq.get(i).getCompanyName());
                proResp.setCompanyAddress(empProfessionalInfoReq.get(i).getCompanyAddress());
                proResp.setCompanyContact(empProfessionalInfoReq.get(i).getCompanyContact());
                proResp.setPreEmpId(empProfessionalInfoReq.get(i).getPreEmpId());
                proResp.setDesignation(empProfessionalInfoReq.get(i).getDesignation());
                proResp.setCtc(empProfessionalInfoReq.get(i).getCtc());
                proResp.setFromDate(empProfessionalInfoReq.get(i).getFromDate());
                proResp.setToDate(empProfessionalInfoReq.get(i).getToDate());
                proResp.setIsActive(true);
                proResp.setCreatedOn(HelperUtils.getCurrentTimeStamp());
                proResp.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
                empProfessionalInfoRepo.save(proResp);
            }
        } catch (Exception e) {
            logger.error("An error occurred while saving EmpProfessionalInfo data", e);
        }

        defaultResponse.setMsg("Employee Registration successfully");
        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
        });
    }

    @Override
    public ApiResponse<List<FilePathResponse>> getSalrySlip(String empCode) {

        List<FilePathResponse> dtoList = new ArrayList<FilePathResponse>();

        if (empCode == null || empCode.isEmpty()) {
            return ResponseUtils.createFailureResponse(dtoList, new TypeReference<List<FilePathResponse>>() {
            }, "EMP CODE CAN NOT BE NULL OR EMPTY", HttpStatus.OK.value());
        }
        try {
            Document document = new Document(PageSize.A4);

            File folder = new File(HelperUtils.LASTFOLDERPATH);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String timemilisec = String.valueOf(System.currentTimeMillis());
            String path = folder.getAbsolutePath() + "/" + "PaySlip" + timemilisec + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(new File(path)));

            document.open();
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font boldFont1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 8);


            PdfPTable tables0 = new PdfPTable(2);
            tables0.setWidthPercentage(100);
            Image img = Image.getInstance("D:/payroll/src/main/resources/static/intek.jpg");
            img.scaleToFit(100, 100);
            PdfPCell c1111 = new PdfPCell(img);
            PdfPCell c2222 = new PdfPCell();
            PdfPCell c3333 = new PdfPCell();
            PdfPCell c4444 = new PdfPCell(new Phrase("Payslip for the month of jan 2023",cellFont));

            Paragraph topParagraph = new Paragraph("INTEK MICRO SYSTEM PVT LTD.", boldFont);
            topParagraph.setAlignment(Element.ALIGN_TOP);
            c2222.addElement(topParagraph);
            c1111.setPadding(4);
            c2222.setPadding(4);
            c1111.setBorder(Rectangle.LEFT | Rectangle.TOP);
            c2222.setBorder(Rectangle.RIGHT | Rectangle.TOP);
            c3333.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
            c4444.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tables0.addCell(c1111);
            tables0.addCell(c2222);
            tables0.addCell(c3333);
            tables0.addCell(c4444);
            document.add(tables0);

            PdfPTable tables = new PdfPTable(4);
            tables.setWidthPercentage(100);
            PdfPCell c1 = new PdfPCell(new Phrase("Emp Code ", cellFont));
            PdfPCell c2 = new PdfPCell(new Phrase(": "+"IN286", cellFont));
            PdfPCell c3 = new PdfPCell(new Phrase("PF AC NO ", cellFont));
            PdfPCell c4 = new PdfPCell(new Phrase(": "+"98712456890", cellFont));
            PdfPCell c5 = new PdfPCell(new Phrase("Name ", cellFont));
            PdfPCell c6 = new PdfPCell(new Phrase(": "+"ARAVIND KUMAR", cellFont));
            PdfPCell c7 = new PdfPCell(new Phrase("Date Of Joining ", cellFont));
            PdfPCell c8 = new PdfPCell(new Phrase(": "+"10-Apr-2023", cellFont));
            PdfPCell c9 = new PdfPCell(new Phrase("Designation ", cellFont));
            PdfPCell c10 = new PdfPCell(new Phrase(": "+"SOFTWARE DEVELOPER", cellFont));
            PdfPCell c11 = new PdfPCell(new Phrase("PAN NO ", cellFont));
            PdfPCell c12 = new PdfPCell(new Phrase(": "+"BHBPN5643F", cellFont));
            PdfPCell c13 = new PdfPCell(new Phrase("Paid Days ", cellFont));
            PdfPCell c14 = new PdfPCell(new Phrase(": "+"31.00", cellFont));
            PdfPCell c15 = new PdfPCell(new Phrase("EL After 10/10/23 ", cellFont));
            PdfPCell c16 = new PdfPCell(new Phrase(": "+"50.00", cellFont));
            PdfPCell c17 = new PdfPCell(new Phrase("UAN NO ", cellFont));
            PdfPCell c18 = new PdfPCell(new Phrase(": "+"UN012653789", cellFont));
            PdfPCell c19 = new PdfPCell(new Phrase("El Balance as on 10/10/23 ", cellFont));
            PdfPCell c20 = new PdfPCell(new Phrase(": "+"0.00", cellFont));
            c1.setPadding(4);
            c2.setPadding(4);
            c3.setPadding(4);
            c4.setPadding(4);
            c5.setPadding(4);
            c6.setPadding(4);
            c7.setPadding(4);
            c8.setPadding(4);
            c9.setPadding(4);
            c10.setPadding(4);
            c11.setPadding(4);
            c12.setPadding(4);
            c13.setPadding(4);
            c14.setPadding(4);
            c15.setPadding(4);
            c16.setPadding(4);
            c17.setPadding(4);
            c18.setPadding(4);
            c19.setPadding(4);
            c20.setPadding(4);

            c1.setBorder(Rectangle.LEFT );
            c2.setBorder(Rectangle.NO_BORDER );
            c3.setBorder(Rectangle.NO_BORDER );
            c4.setBorder( Rectangle.RIGHT);
            c5.setBorder(Rectangle.LEFT );
            c6.setBorder(Rectangle.NO_BORDER );
            c7.setBorder(Rectangle.NO_BORDER );
            c8.setBorder( Rectangle.RIGHT);
            c9.setBorder(Rectangle.LEFT );
            c10.setBorder(Rectangle.NO_BORDER );
            c11.setBorder(Rectangle.NO_BORDER );
            c12.setBorder( Rectangle.RIGHT);
            c13.setBorder(Rectangle.LEFT );
            c14.setBorder(Rectangle.NO_BORDER );
            c15.setBorder(Rectangle.NO_BORDER );
            c16.setBorder( Rectangle.RIGHT);
            c17.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
            c18.setBorder( Rectangle.BOTTOM);
            c19.setBorder( Rectangle.BOTTOM);
            c20.setBorder( Rectangle.RIGHT| Rectangle.BOTTOM);


            tables.addCell(c1);
            tables.addCell(c2);
            tables.addCell(c3);
            tables.addCell(c4);
            tables.addCell(c5);
            tables.addCell(c6);
            tables.addCell(c7);
            tables.addCell(c8);
            tables.addCell(c9);
            tables.addCell(c10);
            tables.addCell(c11);
            tables.addCell(c12);
            tables.addCell(c13);
            tables.addCell(c14);
            tables.addCell(c15);
            tables.addCell(c16);
            tables.addCell(c17);
            tables.addCell(c18);
            tables.addCell(c19);
            tables.addCell(c20);
            document.add(tables);

            PdfPTable table1 = new PdfPTable(4);
            table1.setWidthPercentage(100);

            PdfPCell cell1 = new PdfPCell(new Phrase("Earnings ", boldFont1));
            PdfPCell cell2 = new PdfPCell(new Phrase("AMOUNT", boldFont1));
            PdfPCell cell3 = new PdfPCell(new Phrase("Deduction", boldFont1));
            PdfPCell cell4 = new PdfPCell(new Phrase("AMOUNT", boldFont1));
            cell1.setPadding(4);
            cell2.setPadding(4);
            cell3.setPadding(4);
            cell4.setPadding(4);

            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);

            cell1.setBorder(Rectangle.LEFT |Rectangle.BOTTOM );
            cell2.setBorder(Rectangle.BOTTOM );
            cell3.setBorder(Rectangle.LEFT | Rectangle.BOTTOM );
            cell4.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM );

            table1.addCell(cell1);
            table1.addCell(cell2);
            table1.addCell(cell3);
            table1.addCell(cell4);
            document.add(table1);

            PdfPTable table2 = new PdfPTable(4);
            table2.setWidthPercentage(100);

            PdfPCell c011 = new PdfPCell(new Phrase("Basic Salary ", cellFont));
            PdfPCell c21 = new PdfPCell(new Phrase(": "+"20000.00", cellFont));
            PdfPCell c31 = new PdfPCell(new Phrase("Provident Fund ", cellFont));
            PdfPCell c41 = new PdfPCell(new Phrase(": "+"3750.00", cellFont));
            PdfPCell c51 = new PdfPCell(new Phrase("HRA ", cellFont));
            PdfPCell c61 = new PdfPCell(new Phrase(": "+"2389.00", cellFont));
            PdfPCell c71 = new PdfPCell(new Phrase("TDS ", cellFont));
            PdfPCell c81 = new PdfPCell(new Phrase(": "+"7689.00", cellFont));
            PdfPCell c91 = new PdfPCell(new Phrase("Software Allowance ", cellFont));
            PdfPCell c101 = new PdfPCell(new Phrase(": "+"6000.00", cellFont));
            PdfPCell c111 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c121 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c131 = new PdfPCell(new Phrase("LTA ", cellFont));
            PdfPCell c141 = new PdfPCell(new Phrase(": "+"3100.00", cellFont));
            PdfPCell c151 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c161 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c171 = new PdfPCell(new Phrase("Meal Allowance ", cellFont));
            PdfPCell c181 = new PdfPCell(new Phrase(": "+"3420.00", cellFont));
            PdfPCell c191 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c201 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c0110 = new PdfPCell(new Phrase("Telephone Allowance ", cellFont));
            PdfPCell c210 = new PdfPCell(new Phrase(": "+"1000.00", cellFont));
            PdfPCell c310 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c410 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c510 = new PdfPCell(new Phrase("Books & Perodicals ", cellFont));
            PdfPCell c610 = new PdfPCell(new Phrase(": "+"2389.00", cellFont));
            PdfPCell c710 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c810 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c910 = new PdfPCell(new Phrase("Car Fual & Maintence ", cellFont));
            PdfPCell c1010 = new PdfPCell(new Phrase(": "+"6000.00", cellFont));
            PdfPCell c1110 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c1210 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c1310 = new PdfPCell(new Phrase("Driver Allowance ", cellFont));
            PdfPCell c1410 = new PdfPCell(new Phrase(": "+"3100.00", cellFont));
            PdfPCell c1510 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c1610 = new PdfPCell(new Phrase("", cellFont));

            c011.setPadding(4);
            c21.setPadding(4);
            c31.setPadding(4);
            c41.setPadding(4);
            c51.setPadding(4);
            c61.setPadding(4);
            c71.setPadding(4);
            c81.setPadding(4);
            c91.setPadding(4);
            c101.setPadding(4);
            c111.setPadding(4);
            c121.setPadding(4);
            c131.setPadding(4);
            c141.setPadding(4);
            c151.setPadding(4);
            c161.setPadding(4);
            c171.setPadding(4);
            c181.setPadding(4);
            c191.setPadding(4);
            c201.setPadding(4);
            c0110.setPadding(4);
            c210.setPadding(4);
            c310.setPadding(4);
            c410.setPadding(4);
            c510.setPadding(4);
            c610.setPadding(4);
            c710.setPadding(4);
            c810.setPadding(4);
            c910.setPadding(4);
            c1010.setPadding(4);
            c1110.setPadding(4);
            c1210.setPadding(4);
            c1310.setPadding(4);
            c1410.setPadding(4);
            c1510.setPadding(4);
            c1610.setPadding(4);


            c21.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c41.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c61.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c81.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c101.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c121.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c141.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c161.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c181.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c201.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c210.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c410.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c610.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c810.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1010.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1210.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1410.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1610.setHorizontalAlignment(Element.ALIGN_RIGHT);

            c011.setBorder(Rectangle.LEFT );
            c21.setBorder(Rectangle.RIGHT );
            c31.setBorder(Rectangle.NO_BORDER );
            c41.setBorder(Rectangle.RIGHT );
            c51.setBorder(Rectangle.LEFT );
            c61.setBorder(Rectangle.RIGHT );
            c71.setBorder(Rectangle.NO_BORDER );
            c81.setBorder(Rectangle.RIGHT );
            c91.setBorder(Rectangle.LEFT );
            c101.setBorder(Rectangle.RIGHT );
            c111.setBorder(Rectangle.NO_BORDER );
            c121.setBorder(Rectangle.RIGHT );
            c131.setBorder(Rectangle.LEFT );
            c141.setBorder(Rectangle.RIGHT );
            c151.setBorder(Rectangle.NO_BORDER );
            c161.setBorder(Rectangle.RIGHT );
            c171.setBorder(Rectangle.LEFT );
            c181.setBorder(Rectangle.RIGHT );
            c191.setBorder(Rectangle.NO_BORDER );
            c201.setBorder(Rectangle.RIGHT );
            c0110.setBorder(Rectangle.LEFT );
            c210.setBorder(Rectangle.RIGHT );
            c310.setBorder(Rectangle.NO_BORDER );
            c410.setBorder(Rectangle.RIGHT );
            c510.setBorder(Rectangle.LEFT );
            c610.setBorder(Rectangle.RIGHT );
            c710.setBorder(Rectangle.NO_BORDER );
            c810.setBorder(Rectangle.RIGHT );
            c910.setBorder(Rectangle.LEFT );
            c1010.setBorder(Rectangle.RIGHT );
            c1110.setBorder(Rectangle.NO_BORDER );
            c1210.setBorder(Rectangle.RIGHT );
            c1310.setBorder(Rectangle.BOTTOM|Rectangle.LEFT );
            c1410.setBorder(Rectangle.BOTTOM|Rectangle.RIGHT );
            c1510.setBorder(Rectangle.BOTTOM );
            c1610.setBorder(Rectangle.RIGHT|Rectangle.BOTTOM );



            table2.addCell(c011);
            table2.addCell(c21);
            table2.addCell(c31);
            table2.addCell(c41);
            table2.addCell(c51);
            table2.addCell(c61);
            table2.addCell(c71);
            table2.addCell(c81);
            table2.addCell(c91);
            table2.addCell(c101);
            table2.addCell(c111);
            table2.addCell(c121);
            table2.addCell(c131);
            table2.addCell(c141);
            table2.addCell(c151);
            table2.addCell(c161);
            table2.addCell(c171);
            table2.addCell(c181);
            table2.addCell(c191);
            table2.addCell(c201);
            table2.addCell(c0110);
            table2.addCell(c210);
            table2.addCell(c310);
            table2.addCell(c410);
            table2.addCell(c510);
            table2.addCell(c610);
            table2.addCell(c710);
            table2.addCell(c810);
            table2.addCell(c910);
            table2.addCell(c1010);
            table2.addCell(c1110);
            table2.addCell(c1210);
            table2.addCell(c1310);
            table2.addCell(c1410);
            table2.addCell(c1510);
            table2.addCell(c1610);
            document.add(table2);

            PdfPTable table3 = new PdfPTable(4);
            table3.setWidthPercentage(100);

            PdfPCell c30 = new PdfPCell(new Phrase("Gross Earning ", boldFont1));
            PdfPCell c01 = new PdfPCell(new Phrase(": "+"20000.00", boldFont1));
            PdfPCell c32 = new PdfPCell(new Phrase("Gross Deduction:", boldFont1));
            PdfPCell c33 = new PdfPCell(new Phrase(": "+"3750.00", boldFont1));

            c30.setPadding(4);
            c01.setPadding(4);
            c32.setPadding(4);
            c33.setPadding(4);

            c01.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c33.setHorizontalAlignment(Element.ALIGN_RIGHT);

            c30.setBorder(Rectangle.LEFT |Rectangle.BOTTOM|Rectangle.TOP );
            c01.setBorder(Rectangle.BOTTOM |Rectangle.TOP );
            c32.setBorder(Rectangle.LEFT | Rectangle.BOTTOM|Rectangle.TOP  );
            c33.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM|Rectangle.TOP  );

            table3.addCell(c30);
            table3.addCell(c01);
            table3.addCell(c32);
            table3.addCell(c33);
            document.add(table3);

            PdfPTable table4 = new PdfPTable(2);
            table4.setWidthPercentage(100);

            PdfPCell c401 = new PdfPCell(new Phrase("Net Amount"+"    : ", boldFont1));
            PdfPCell c402= new PdfPCell(new Phrase("203033.00", cellFont));
            PdfPCell c403 = new PdfPCell(new Phrase("Net Pay in word"+"    : ", boldFont1));
            PdfPCell c404 = new PdfPCell(new Phrase("Two thausand  one handered only", cellFont));

            c401.setPadding(4);
            c402.setPadding(4);
            c403.setPadding(4);
            c404.setPadding(4);

            c401.setBorder(Rectangle.LEFT |Rectangle.BOTTOM );
            c402.setBorder(Rectangle.RIGHT |Rectangle.BOTTOM );
            c403.setBorder(Rectangle.LEFT |Rectangle.BOTTOM );
            c404.setBorder(Rectangle.RIGHT |Rectangle.BOTTOM );

            table4.addCell(c401);
            table4.addCell(c402);
            table4.addCell(c403);
            table4.addCell(c404);
            document.add(table4);

            PdfPTable table5 = new PdfPTable(5);
            table5.setWidthPercentage(100);
            PdfPCell c551 = new PdfPCell(new Phrase("Mode Of Payment", boldFont1));
            PdfPCell c552 = new PdfPCell(new Phrase("Disbursement Date", boldFont1));
            PdfPCell c553 = new PdfPCell(new Phrase("Employee Bank", boldFont1));
            PdfPCell c554 = new PdfPCell(new Phrase("Acoount No.", boldFont1));
            PdfPCell c555 = new PdfPCell(new Phrase("Amount", boldFont1));
            PdfPCell c556 = new PdfPCell(new Phrase("Bank Transfer", cellFont));
            PdfPCell c557 = new PdfPCell(new Phrase("10-10-23", cellFont));
            PdfPCell c558 = new PdfPCell(new Phrase("State Bank Of India", cellFont));
            PdfPCell c559 = new PdfPCell(new Phrase("9876543207.", cellFont));
            PdfPCell c550 = new PdfPCell(new Phrase("25000", cellFont));
            c551.setPadding(4);
            c552.setPadding(4);
            c553.setPadding(4);
            c554.setPadding(4);
            c555.setPadding(4);
            c556.setPadding(4);
            c557.setPadding(4);
            c558.setPadding(4);
            c559.setPadding(4);
            c550.setPadding(4);

            c551.setBorder(Rectangle.LEFT |Rectangle.BOTTOM );
            c552.setBorder(Rectangle.BOTTOM );
            c553.setBorder(Rectangle.BOTTOM);
            c554.setBorder(Rectangle.BOTTOM);
            c555.setBorder(Rectangle.BOTTOM|Rectangle.RIGHT );
            c556.setBorder(Rectangle.LEFT |Rectangle.BOTTOM);
            c557.setBorder(Rectangle.BOTTOM );
            c558.setBorder(Rectangle.BOTTOM );
            c559.setBorder(Rectangle.BOTTOM);
            c550.setBorder(Rectangle.RIGHT |Rectangle.BOTTOM );

            table5.addCell(c551);
            table5.addCell(c552);
            table5.addCell(c553);
            table5.addCell(c554);
            table5.addCell(c555);
            table5.addCell(c556);
            table5.addCell(c557);
            table5.addCell(c558);
            table5.addCell(c559);
            table5.addCell(c550);
            document.add(table5);

            document.close();
            FilePathResponse dto = new FilePathResponse();
            dto.setPath(HelperUtils.FILEPATH + "PaySlip"+ timemilisec + ".pdf");
            dto.setFileName("PaySlip" + timemilisec + ".pdf");
            dtoList.add(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("An error occurred while generating PaySlip.pdf ", e);
        }
        return ResponseUtils.createSuccessResponse(dtoList, new TypeReference<List<FilePathResponse>>() {
        });
    }

    @Override
    public ApiResponse<List<FilePathResponse>> getAttendanceRpt(String empCode) {

        List<FilePathResponse> dtoList = new ArrayList<FilePathResponse>();

        if (empCode == null || empCode.isEmpty()) {
            return ResponseUtils.createFailureResponse(dtoList, new TypeReference<List<FilePathResponse>>() {
            }, "EMP CODE CAN NOT BE NULL OR EMPTY", HttpStatus.OK.value());
        }
        try {
            Document document = new Document(PageSize.A4);

            File folder = new File(HelperUtils.LASTFOLDERPATH);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String timemilisec = String.valueOf(System.currentTimeMillis());
            String path = folder.getAbsolutePath() + "/" + "AttendanceRpt" + timemilisec + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(new File(path)));

            document.open();
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font boldFont1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 8);

            Paragraph paragraph = new Paragraph();
            paragraph.add(new Chunk("INTEK" + " "+ " DAILY ATTENDANCE ", boldFont));
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);
            document.add(new Paragraph("\n"));

            Paragraph paragraph1 = new Paragraph();
            paragraph1.add(new Chunk("EMP NAME:" + " "+ " Aravind Kumar ", boldFont1));
            paragraph1.add(new Chunk(new VerticalPositionMark()));
            paragraph1.add(new Chunk("EMP CODE:" + " "+ " IN1234 ", boldFont1));
            document.add(paragraph1);
            document.add(new Paragraph("\n"));

            PdfPTable tables = new PdfPTable(5);
            tables.setWidthPercentage(100);
            PdfPCell c1 = new PdfPCell(new Phrase("DATE ", boldFont1));
            PdfPCell c2 = new PdfPCell(new Phrase("IN-COMING", boldFont1));
            PdfPCell c3 = new PdfPCell(new Phrase("OUT-GOING ", boldFont1));
            PdfPCell c4 = new PdfPCell(new Phrase("HOURS.", boldFont1));
            PdfPCell c5 = new PdfPCell(new Phrase("REMARKS", boldFont1));

            c1.setPadding(5);
            c2.setPadding(5);
            c3.setPadding(5);
            c4.setPadding(5);
            c5.setPadding(5);

            tables.addCell(c1);
            tables.addCell(c2);
            tables.addCell(c3);
            tables.addCell(c4);
            tables.addCell(c5);
            document.add(tables);

            PdfPTable tables1 = new PdfPTable(5);
            tables1.setWidthPercentage(100);
            PdfPCell c11 = new PdfPCell(new Phrase("01-01-2023 ", cellFont));
            PdfPCell c21 = new PdfPCell(new Phrase("09:30 am", cellFont));
            PdfPCell c31 = new PdfPCell(new Phrase("06:00 pm ", cellFont));
            PdfPCell c41 = new PdfPCell(new Phrase("08 hours:30 min", cellFont));
            PdfPCell c51 = new PdfPCell(new Phrase("REMARKS", cellFont));

            c11.setPadding(5);
            c21.setPadding(5);
            c31.setPadding(5);
            c41.setPadding(5);
            c51.setPadding(5);

            tables1.addCell(c11);
            tables1.addCell(c21);
            tables1.addCell(c31);
            tables1.addCell(c41);
            tables1.addCell(c51);
            document.add(tables1);
            document.add(new Paragraph("\n"));

            PdfPCell c021 = new PdfPCell(new Phrase("Work Satisfaction Form", boldFont1));
            c021.setPadding(5);
            c021.setColspan(7);
            c021.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell c111 = new PdfPCell(new Phrase("Tick mark on any above field", boldFont1));
            c111.setPadding(5);
            c111.setColspan(7);
            c111.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell emptyCell = new PdfPCell(new Phrase("", boldFont1));
            emptyCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell emptyRow = new PdfPCell(new Phrase("", boldFont1));
            emptyRow.setColspan(7);
            emptyRow.setBorder(Rectangle.NO_BORDER);
            emptyRow.setFixedHeight(10f);

            PdfPTable tables3 = new PdfPTable(7);
            tables3.setWidths(new float[] {1f,0.1f,0.5f,0.1f,1f,0.1f,0.5f});
            PdfPCell c031 = new PdfPCell(new Phrase("Very Bad:", boldFont1));
            PdfPCell c033 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c035 = new PdfPCell(new Phrase("Good:", boldFont1));
            PdfPCell c037 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c038 = new PdfPCell(new Phrase("Bad:", boldFont1));
            PdfPCell c040 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c042 = new PdfPCell(new Phrase("Good but Need Improvement:", boldFont1));
            PdfPCell c044 = new PdfPCell(new Phrase("", cellFont));
            PdfPCell c045 = new PdfPCell(new Phrase("Average:", boldFont1));
            PdfPCell c046 = new PdfPCell(new Phrase("", cellFont));
            c042.setRowspan(3);
            c044.setRowspan(3);
            c031.setPadding(5);
            c033.setPadding(5);
            c035.setPadding(5);
            c037.setPadding(5);
            c038.setPadding(5);
            c040.setPadding(5);
            c042.setPadding(5);
            c044.setPadding(5);
            c045.setPadding(5);
            c046.setPadding(5);

            tables3.addCell(c021);
            tables3.addCell(emptyRow);
            tables3.addCell(c031);
            tables3.addCell(emptyCell);
            tables3.addCell(c033);
            tables3.addCell(emptyCell);
            tables3.addCell(c035);
            tables3.addCell(emptyCell);
            tables3.addCell(c037);
            tables3.addCell(emptyRow);
            tables3.addCell(c038);
            tables3.addCell(emptyCell);
            tables3.addCell(c040);
            tables3.addCell(emptyCell);
            tables3.addCell(c042);
            tables3.addCell(emptyCell);
            tables3.addCell(c044);
            tables3.addCell(emptyRow);
            tables3.addCell(c045);
            tables3.addCell(emptyCell);
            tables3.addCell(c046);
            tables3.addCell(emptyCell);
            tables3.addCell(emptyCell);
            tables3.addCell(emptyCell);
            tables3.addCell(emptyCell);
            tables3.addCell(emptyRow);
            tables3.addCell(c111);

            PdfPTable tables4 = new PdfPTable(1);
            PdfPCell c022 = new PdfPCell(new Phrase("Verify By", boldFont1));
            PdfPCell c0222 = new PdfPCell(new Phrase("nabha kishore", cellFont));
            c022.setHorizontalAlignment(Element.ALIGN_CENTER);
            c022.setPadding(5);
            c0222.setPadding(5);
            tables4.addCell(c022);
            tables4.addCell(c0222);

            PdfPTable tables5 = new PdfPTable(2);
            tables5.setWidthPercentage(100);

            tables5.setWidths(new float[] {70,30});
            tables5.addCell(tables3);
            tables5.addCell(tables4);

            document.add(tables5);

            document.close();
            FilePathResponse dto = new FilePathResponse();
            dto.setPath(HelperUtils.FILEPATH + "AttendanceRpt"+ timemilisec + ".pdf");
            dto.setFileName("AttendanceRpt" + timemilisec + ".pdf");
            dtoList.add(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("An error occurred while generating AttendanceRpt.pdf ", e);
        }
        return ResponseUtils.createSuccessResponse(dtoList, new TypeReference<List<FilePathResponse>>() {
        });
    }

    @Override
    public ApiResponse<List<FilePathResponse>> getTdsSlip(String empCode) {

        List<FilePathResponse> dtoList = new ArrayList<FilePathResponse>();

        if (empCode == null || empCode.isEmpty()) {
            return ResponseUtils.createFailureResponse(dtoList, new TypeReference<List<FilePathResponse>>() {
            }, "EMP CODE CAN NOT BE NULL OR EMPTY", HttpStatus.OK.value());
        }
        try {
            Document document = new Document(PageSize.A4);

            File folder = new File(HelperUtils.LASTFOLDERPATH);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String timemilisec = String.valueOf(System.currentTimeMillis());
            String path = folder.getAbsolutePath() + "/" + "TdsPaySlip" + timemilisec + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(new File(path)));

            document.open();
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 8);


            PdfPTable tables00 = new PdfPTable(2);
            tables00.setWidthPercentage(100);
            PdfPCell c00 = new PdfPCell(new Phrase("TDS Projection Report :",boldFont));
            PdfPCell c01 = new PdfPCell(new Phrase("For Accounting Year 2022-2023"+" "+",Assessment Year 2023-2024 ",normalFont));
            PdfPCell c02 = new PdfPCell(new Phrase("IN286"+"      "+"Aravind Kumar Malakar",boldFont));
            PdfPCell c03 = new PdfPCell(new Phrase("",normalFont));
            c00.setPadding(4);
            c01.setPadding(4);
            c02.setPadding(4);
            c03.setPadding(4);
            c00.setBorder(Rectangle.LEFT | Rectangle.TOP| Rectangle.BOTTOM);
            c01.setBorder(Rectangle.RIGHT | Rectangle.TOP| Rectangle.BOTTOM);
            c02.setBorder(Rectangle.LEFT | Rectangle.TOP);
            c03.setBorder(Rectangle.RIGHT | Rectangle.TOP);
            tables00.addCell(c00);
            tables00.addCell(c01);
            tables00.addCell(c02);
            tables00.addCell(c03);
            document.add(tables00);

            PdfPTable tables01 = new PdfPTable(4);
            tables01.setWidthPercentage(100);
            PdfPCell c001 = new PdfPCell(new Phrase("Designation :",boldFont));
            PdfPCell c002 = new PdfPCell(new Phrase("Software Developer",normalFont));
            PdfPCell c003 = new PdfPCell(new Phrase("PAN : ",boldFont));
            PdfPCell c004 = new PdfPCell(new Phrase("BHBKM0987G",normalFont));
            c001.setPadding(4);
            c002.setPadding(4);
            c003.setPadding(4);
            c004.setPadding(4);
            c001.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
            c002.setBorder(Rectangle.BOTTOM);
            c003.setBorder(Rectangle.BOTTOM);
            c004.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tables01.addCell(c001);
            tables01.addCell(c002);
            tables01.addCell(c003);
            tables01.addCell(c004);
            document.add(tables01);

            PdfPTable tables02 = new PdfPTable(4);
            tables02.setWidthPercentage(100);
            PdfPCell c0020 = new PdfPCell(new Phrase("Particulars",boldFont));
            PdfPCell c0021 = new PdfPCell(new Phrase("Annual (For 11 Months)",boldFont));
            PdfPCell c0022 = new PdfPCell(new Phrase("Total (For 1 Months)",boldFont));
            PdfPCell c0023 = new PdfPCell(new Phrase("Total",boldFont));
            PdfPCell c0024 = new PdfPCell(new Phrase("Salary,Allowances and Parks",boldFont));
            PdfPCell c0025 = new PdfPCell(new Phrase("",boldFont));
            PdfPCell c0026 = new PdfPCell(new Phrase("",boldFont));
            PdfPCell c0027 = new PdfPCell(new Phrase("",boldFont));
            c0020.setPadding(4);
            c0021.setPadding(4);
            c0022.setPadding(4);
            c0023.setPadding(4);
            c0024.setPadding(4);
            c0025.setPadding(4);
            c0026.setPadding(4);
            c0027.setPadding(4);

            c0020.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
            c0021.setBorder(Rectangle.BOTTOM);
            c0022.setBorder(Rectangle.BOTTOM);
            c0023.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            c0024.setBorder(Rectangle.LEFT);
            c0025.setBorder(Rectangle.NO_BORDER);
            c0026.setBorder(Rectangle.NO_BORDER);
            c0027.setBorder(Rectangle.RIGHT);

            tables02.addCell(c0020);
            tables02.addCell(c0021);
            tables02.addCell(c0022);
            tables02.addCell(c0023);
            tables02.addCell(c0024);
            tables02.addCell(c0025);
            tables02.addCell(c0026);
            tables02.addCell(c0027);
            document.add(tables02);

            PdfPTable tables03 = new PdfPTable(4);
            tables03.setWidthPercentage(100);
            PdfPCell c0030 = new PdfPCell(new Phrase("Basic Salary",normalFont));
            PdfPCell c0031 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c0032 = new PdfPCell(new Phrase("18000.00",normalFont));
            PdfPCell c0033 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c0034 = new PdfPCell(new Phrase("Software Allowances",normalFont));
            PdfPCell c0035 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0036 = new PdfPCell(new Phrase("3500.00",normalFont));
            PdfPCell c0037 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0038 = new PdfPCell(new Phrase("Meal Allowances",normalFont));
            PdfPCell c0039 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c0040 = new PdfPCell(new Phrase("18000.00",normalFont));
            PdfPCell c0041 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c0042 = new PdfPCell(new Phrase("HRA",normalFont));
            PdfPCell c0043 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0044 = new PdfPCell(new Phrase("3500.00",normalFont));
            PdfPCell c0045 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0046 = new PdfPCell(new Phrase("Car Fuel and Maintenance",normalFont));
            PdfPCell c0047 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c0048 = new PdfPCell(new Phrase("18000.00",normalFont));
            PdfPCell c0049 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c0050 = new PdfPCell(new Phrase("Driver Rent",normalFont));
            PdfPCell c0051 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0052 = new PdfPCell(new Phrase("3500.00",normalFont));
            PdfPCell c0053 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0054 = new PdfPCell(new Phrase("ITA",normalFont));
            PdfPCell c0055 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c0056 = new PdfPCell(new Phrase("18000.00",normalFont));
            PdfPCell c0057 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c0058 = new PdfPCell(new Phrase("Telephone Allowances",normalFont));
            PdfPCell c0059 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0060 = new PdfPCell(new Phrase("3500.00",normalFont));
            PdfPCell c0061 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0062 = new PdfPCell(new Phrase("Books and Periodicals",normalFont));
            PdfPCell c0063 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0064 = new PdfPCell(new Phrase("3500.00",normalFont));
            PdfPCell c0065 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0066 = new PdfPCell(new Phrase("Total Earnings (-) Exemption",boldFont));
            PdfPCell c0067 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c0068 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c0069 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0070 = new PdfPCell(new Phrase("HRA",normalFont));
            PdfPCell c0071 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c0072 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c0073 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0074 = new PdfPCell(new Phrase("Total Exemption",boldFont));
            PdfPCell c0075 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c0076 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c0077 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c0078 = new PdfPCell(new Phrase("Gross Salary (-) Deductions",boldFont));
            PdfPCell c0079 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c0080 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c0081 = new PdfPCell(new Phrase("1350000.00",normalFont));
            c0030.setPadding(4);
            c0031.setPadding(4);
            c0032.setPadding(4);
            c0033.setPadding(4);
            c0034.setPadding(4);
            c0035.setPadding(4);
            c0036.setPadding(4);
            c0037.setPadding(4);
            c0038.setPadding(4);
            c0039.setPadding(4);
            c0040.setPadding(4);
            c0041.setPadding(4);
            c0042.setPadding(4);
            c0043.setPadding(4);
            c0044.setPadding(4);
            c0045.setPadding(4);
            c0046.setPadding(4);
            c0047.setPadding(4);
            c0048.setPadding(4);
            c0049.setPadding(4);
            c0050.setPadding(4);
            c0051.setPadding(4);
            c0052.setPadding(4);
            c0053.setPadding(4);
            c0054.setPadding(4);
            c0055.setPadding(4);
            c0056.setPadding(4);
            c0057.setPadding(4);
            c0058.setPadding(4);
            c0059.setPadding(4);
            c0060.setPadding(4);
            c0061.setPadding(4);
            c0062.setPadding(4);
            c0063.setPadding(4);
            c0064.setPadding(4);
            c0065.setPadding(4);
            c0066.setPadding(4);
            c0069.setPadding(4);
            c0070.setPadding(4);
            c0073.setPadding(4);
            c0074.setPadding(4);
            c0077.setPadding(4);
            c0078.setPadding(4);
            c0081.setPadding(4);

            c0030.setBorder(Rectangle.LEFT);
            c0031.setBorder(Rectangle.NO_BORDER);
            c0032.setBorder(Rectangle.NO_BORDER);
            c0033.setBorder(Rectangle.RIGHT);
            c0034.setBorder(Rectangle.LEFT);
            c0035.setBorder(Rectangle.NO_BORDER);
            c0036.setBorder(Rectangle.NO_BORDER);
            c0037.setBorder(Rectangle.RIGHT);
            c0038.setBorder(Rectangle.LEFT);
            c0039.setBorder(Rectangle.NO_BORDER);
            c0040.setBorder(Rectangle.NO_BORDER);
            c0041.setBorder(Rectangle.RIGHT);
            c0042.setBorder(Rectangle.LEFT);
            c0043.setBorder(Rectangle.NO_BORDER);
            c0044.setBorder(Rectangle.NO_BORDER);
            c0045.setBorder(Rectangle.RIGHT);
            c0046.setBorder(Rectangle.LEFT);
            c0047.setBorder(Rectangle.NO_BORDER);
            c0048.setBorder(Rectangle.NO_BORDER);
            c0049.setBorder(Rectangle.RIGHT);
            c0050.setBorder(Rectangle.LEFT);
            c0051.setBorder(Rectangle.NO_BORDER);
            c0052.setBorder(Rectangle.NO_BORDER);
            c0053.setBorder(Rectangle.RIGHT);
            c0054.setBorder(Rectangle.LEFT);
            c0055.setBorder(Rectangle.NO_BORDER);
            c0056.setBorder(Rectangle.NO_BORDER);
            c0057.setBorder(Rectangle.RIGHT);
            c0058.setBorder(Rectangle.LEFT);
            c0059.setBorder(Rectangle.NO_BORDER);
            c0060.setBorder(Rectangle.NO_BORDER);
            c0061.setBorder(Rectangle.RIGHT);
            c0062.setBorder(Rectangle.LEFT);
            c0063.setBorder(Rectangle.NO_BORDER);
            c0064.setBorder(Rectangle.NO_BORDER);
            c0065.setBorder(Rectangle.RIGHT);
            c0066.setBorder(Rectangle.LEFT);
            c0067.setBorder(Rectangle.NO_BORDER);
            c0068.setBorder(Rectangle.NO_BORDER);
            c0069.setBorder(Rectangle.RIGHT);
            c0070.setBorder(Rectangle.LEFT);
            c0071.setBorder(Rectangle.NO_BORDER);
            c0072.setBorder(Rectangle.NO_BORDER);
            c0073.setBorder(Rectangle.RIGHT);
            c0074.setBorder(Rectangle.LEFT);
            c0075.setBorder(Rectangle.NO_BORDER);
            c0076.setBorder(Rectangle.NO_BORDER);
            c0077.setBorder(Rectangle.RIGHT);
            c0078.setBorder(Rectangle.LEFT);
            c0079.setBorder(Rectangle.NO_BORDER);
            c0080.setBorder(Rectangle.NO_BORDER);
            c0081.setBorder(Rectangle.RIGHT);

            tables03.addCell(c0030);
            tables03.addCell(c0031);
            tables03.addCell(c0032);
            tables03.addCell(c0033);
            tables03.addCell(c0034);
            tables03.addCell(c0035);
            tables03.addCell(c0036);
            tables03.addCell(c0037);
            tables03.addCell(c0038);
            tables03.addCell(c0039);
            tables03.addCell(c0040);
            tables03.addCell(c0041);
            tables03.addCell(c0042);
            tables03.addCell(c0043);
            tables03.addCell(c0044);
            tables03.addCell(c0045);
            tables03.addCell(c0046);
            tables03.addCell(c0047);
            tables03.addCell(c0048);
            tables03.addCell(c0049);
            tables03.addCell(c0050);
            tables03.addCell(c0051);
            tables03.addCell(c0052);
            tables03.addCell(c0053);
            tables03.addCell(c0054);
            tables03.addCell(c0055);
            tables03.addCell(c0056);
            tables03.addCell(c0057);
            tables03.addCell(c0058);
            tables03.addCell(c0059);
            tables03.addCell(c0060);
            tables03.addCell(c0061);
            tables03.addCell(c0062);
            tables03.addCell(c0063);
            tables03.addCell(c0064);
            tables03.addCell(c0065);
            tables03.addCell(c0066);
            tables03.addCell(c0067);
            tables03.addCell(c0068);
            tables03.addCell(c0069);
            tables03.addCell(c0070);
            tables03.addCell(c0071);
            tables03.addCell(c0072);
            tables03.addCell(c0073);
            tables03.addCell(c0074);
            tables03.addCell(c0075);
            tables03.addCell(c0076);
            tables03.addCell(c0077);
            tables03.addCell(c0078);
            tables03.addCell(c0079);
            tables03.addCell(c0080);
            tables03.addCell(c0081);
            document.add(tables03);

            PdfPTable tables04 = new PdfPTable(4);
            tables04.setWidthPercentage(100);
            PdfPCell c00304 = new PdfPCell(new Phrase("Standard Deduction",normalFont));
            PdfPCell c00314 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c00324 = new PdfPCell(new Phrase("18000.00",normalFont));
            PdfPCell c00334= new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c00344 = new PdfPCell(new Phrase("Total Income Salary",boldFont));
            PdfPCell c00354 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00364 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00374 = new PdfPCell(new Phrase("1350000.00",normalFont));
            PdfPCell c00384 = new PdfPCell(new Phrase("Others Income",boldFont));
            PdfPCell c00394 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00404 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00414 = new PdfPCell(new Phrase("0.00",normalFont));
            PdfPCell c00424 = new PdfPCell(new Phrase("Gross Total Income",boldFont));
            PdfPCell c00434 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00444 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00454 = new PdfPCell(new Phrase("1350000.00",normalFont));
            PdfPCell c00464 = new PdfPCell(new Phrase("Investments (80CCC - 80U)",boldFont));
            PdfPCell c00474 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00484 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00494 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00504 = new PdfPCell(new Phrase("Provident Fund",normalFont));
            PdfPCell c00514 = new PdfPCell(new Phrase("50000.00",normalFont));
            PdfPCell c00524 = new PdfPCell(new Phrase("350.00",normalFont));
            PdfPCell c00534 = new PdfPCell(new Phrase("50000.00",normalFont));
            PdfPCell c00544 = new PdfPCell(new Phrase("Life Insurance Premium 80C",normalFont));
            PdfPCell c00554 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c00564 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00574 = new PdfPCell(new Phrase("180000.00",normalFont));
            PdfPCell c00584 = new PdfPCell(new Phrase("30000",normalFont));
            PdfPCell c00594 = new PdfPCell(new Phrase("20000.00",normalFont));
            PdfPCell c00604 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00614 = new PdfPCell(new Phrase("20000.00",normalFont));
            PdfPCell c00624 = new PdfPCell(new Phrase("Deductions u/s 80C",normalFont));
            PdfPCell c00634 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c00644 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00654 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c00664 = new PdfPCell(new Phrase("Deductions u/s 80CCC",normalFont));
            PdfPCell c00674 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c00684 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00694 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c00704 = new PdfPCell(new Phrase("Deductions u/s 80D",normalFont));
            PdfPCell c00714 = new PdfPCell(new Phrase("10000.00",normalFont));
            PdfPCell c00724 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00734 = new PdfPCell(new Phrase("10000.00",normalFont));
            PdfPCell c00744 = new PdfPCell(new Phrase("Total Investments (80CCC - 80U)",boldFont));
            PdfPCell c00754 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00764 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00774 = new PdfPCell(new Phrase("350000.00",normalFont));
            PdfPCell c00784 = new PdfPCell(new Phrase("Taxable Income",boldFont));
            PdfPCell c00794 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00804 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c00814 = new PdfPCell(new Phrase("50000.00",normalFont));
            PdfPCell c101 = new PdfPCell(new Phrase("Income Tax",normalFont));
            PdfPCell c102 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c103 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c104 = new PdfPCell(new Phrase("10000.00",normalFont));
            PdfPCell c105 = new PdfPCell(new Phrase("(+)Surcharge",normalFont));
            PdfPCell c106 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c107 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c108 = new PdfPCell(new Phrase("2000.00",normalFont));
            PdfPCell c109 = new PdfPCell(new Phrase("(+)Health and Education Cess",normalFont));
            PdfPCell c110 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c111 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c112 = new PdfPCell(new Phrase("1000.00",normalFont));
            PdfPCell c113 = new PdfPCell(new Phrase("Total Tax",boldFont));
            PdfPCell c114 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c115 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c116 = new PdfPCell(new Phrase("1000.00",normalFont));
            PdfPCell c117 = new PdfPCell(new Phrase("(-)TDS on Other Income Reported",normalFont));
            PdfPCell c118 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c119 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c120 = new PdfPCell(new Phrase("1000.00",normalFont));
            PdfPCell c121 = new PdfPCell(new Phrase("(-)TDS Deduction",normalFont));
            PdfPCell c122 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c123 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c124 = new PdfPCell(new Phrase("1000.00",normalFont));
            PdfPCell c125 = new PdfPCell(new Phrase("Balance Tax Deductible",normalFont));
            PdfPCell c126 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c127 = new PdfPCell(new Phrase("",normalFont));
            PdfPCell c128 = new PdfPCell(new Phrase("1000.00",normalFont));

            c00304.setPadding(4);
            c00314.setPadding(4);
            c00324.setPadding(4);
            c00334.setPadding(4);
            c00344.setPadding(4);
            c00354.setPadding(4);
            c00364.setPadding(4);
            c00374.setPadding(4);
            c00384.setPadding(4);
            c00394.setPadding(4);
            c00404.setPadding(4);
            c00414.setPadding(4);
            c00424.setPadding(4);
            c00434.setPadding(4);
            c00444.setPadding(4);
            c00454.setPadding(4);
            c00464.setPadding(4);
            c00474.setPadding(4);
            c00484.setPadding(4);
            c00494.setPadding(4);
            c00504.setPadding(4);
            c00514.setPadding(4);
            c00524.setPadding(4);
            c00534.setPadding(4);
            c00544.setPadding(4);
            c00554.setPadding(4);
            c00564.setPadding(4);
            c00574.setPadding(4);
            c00584.setPadding(4);
            c00594.setPadding(4);
            c00604.setPadding(4);
            c00614.setPadding(4);
            c00624.setPadding(4);
            c00634.setPadding(4);
            c00644.setPadding(4);
            c00654.setPadding(4);
            c00664.setPadding(4);
            c00694.setPadding(4);
            c00704.setPadding(4);
            c00734.setPadding(4);
            c00744.setPadding(4);
            c00774.setPadding(4);
            c00784.setPadding(4);
            c00814.setPadding(4);
            c101.setPadding(4);
            c104.setPadding(4);
            c105.setPadding(4);
            c108.setPadding(4);
            c109.setPadding(4);
            c112.setPadding(4);
            c113.setPadding(4);
            c116.setPadding(4);
            c117.setPadding(4);
            c120.setPadding(4);
            c121.setPadding(4);
            c124.setPadding(4);
            c125.setPadding(4);
            c128.setPadding(4);

            c00304.setBorder(Rectangle.LEFT);
            c00314.setBorder(Rectangle.NO_BORDER);
            c00324.setBorder(Rectangle.NO_BORDER);
            c00334.setBorder(Rectangle.RIGHT);
            c00344.setBorder(Rectangle.LEFT);
            c00354.setBorder(Rectangle.NO_BORDER);
            c00364.setBorder(Rectangle.NO_BORDER);
            c00374.setBorder(Rectangle.RIGHT);
            c00384.setBorder(Rectangle.LEFT);
            c00394.setBorder(Rectangle.NO_BORDER);
            c00404.setBorder(Rectangle.NO_BORDER);
            c00414.setBorder(Rectangle.RIGHT);
            c00424.setBorder(Rectangle.LEFT);
            c00434.setBorder(Rectangle.NO_BORDER);
            c00444.setBorder(Rectangle.NO_BORDER);
            c00454.setBorder(Rectangle.RIGHT);
            c00464.setBorder(Rectangle.LEFT);
            c00474.setBorder(Rectangle.NO_BORDER);
            c00484.setBorder(Rectangle.NO_BORDER);
            c00494.setBorder(Rectangle.RIGHT);
            c00504.setBorder(Rectangle.LEFT);
            c00514.setBorder(Rectangle.NO_BORDER);
            c00524.setBorder(Rectangle.NO_BORDER);
            c00534.setBorder(Rectangle.RIGHT);
            c00544.setBorder(Rectangle.LEFT);
            c00554.setBorder(Rectangle.NO_BORDER);
            c00564.setBorder(Rectangle.NO_BORDER);
            c00574.setBorder(Rectangle.RIGHT);
            c00584.setBorder(Rectangle.LEFT);
            c00594.setBorder(Rectangle.NO_BORDER);
            c00604.setBorder(Rectangle.NO_BORDER);
            c00614.setBorder(Rectangle.RIGHT);
            c00624.setBorder(Rectangle.LEFT);
            c00634.setBorder(Rectangle.NO_BORDER);
            c00644.setBorder(Rectangle.NO_BORDER);
            c00654.setBorder(Rectangle.RIGHT);
            c00664.setBorder(Rectangle.LEFT);
            c00674.setBorder(Rectangle.NO_BORDER);
            c00684.setBorder(Rectangle.NO_BORDER);
            c00694.setBorder(Rectangle.RIGHT);
            c00704.setBorder(Rectangle.LEFT);
            c00714.setBorder(Rectangle.NO_BORDER);
            c00724.setBorder(Rectangle.NO_BORDER);
            c00734.setBorder(Rectangle.RIGHT);
            c00744.setBorder(Rectangle.LEFT);
            c00754.setBorder(Rectangle.NO_BORDER);
            c00764.setBorder(Rectangle.NO_BORDER);
            c00774.setBorder(Rectangle.RIGHT);
            c00784.setBorder(Rectangle.LEFT);
            c00794.setBorder(Rectangle.NO_BORDER);
            c00804.setBorder(Rectangle.NO_BORDER);
            c00814.setBorder(Rectangle.RIGHT);
            c101.setBorder(Rectangle.LEFT);
            c102.setBorder(Rectangle.NO_BORDER);
            c103.setBorder(Rectangle.NO_BORDER);
            c104.setBorder(Rectangle.RIGHT);
            c105.setBorder(Rectangle.LEFT);
            c106.setBorder(Rectangle.NO_BORDER);
            c107.setBorder(Rectangle.NO_BORDER);
            c108.setBorder(Rectangle.RIGHT);
            c109.setBorder(Rectangle.LEFT);
            c110.setBorder(Rectangle.NO_BORDER);
            c111.setBorder(Rectangle.NO_BORDER);
            c112.setBorder(Rectangle.RIGHT);
            c113.setBorder(Rectangle.LEFT);
            c114.setBorder(Rectangle.NO_BORDER);
            c115.setBorder(Rectangle.NO_BORDER);
            c116.setBorder(Rectangle.RIGHT);
            c117.setBorder(Rectangle.LEFT);
            c118.setBorder(Rectangle.NO_BORDER);
            c119.setBorder(Rectangle.NO_BORDER);
            c120.setBorder(Rectangle.RIGHT);
            c121.setBorder(Rectangle.LEFT);
            c122.setBorder(Rectangle.NO_BORDER);
            c123.setBorder(Rectangle.NO_BORDER);
            c124.setBorder(Rectangle.RIGHT);
            c125.setBorder(Rectangle.LEFT);
            c126.setBorder(Rectangle.NO_BORDER);
            c127.setBorder(Rectangle.NO_BORDER);
            c128.setBorder(Rectangle.RIGHT);

            tables04.addCell(c00304);
            tables04.addCell(c00314);
            tables04.addCell(c00324);
            tables04.addCell(c00334);
            tables04.addCell(c00344);
            tables04.addCell(c00354);
            tables04.addCell(c00364);
            tables04.addCell(c00374);
            tables04.addCell(c00384);
            tables04.addCell(c00394);
            tables04.addCell(c00404);
            tables04.addCell(c00414);
            tables04.addCell(c00424);
            tables04.addCell(c00434);
            tables04.addCell(c00444);
            tables04.addCell(c00454);
            tables04.addCell(c00464);
            tables04.addCell(c00474);
            tables04.addCell(c00484);
            tables04.addCell(c00494);
            tables04.addCell(c00504);
            tables04.addCell(c00514);
            tables04.addCell(c00524);
            tables04.addCell(c00534);
            tables04.addCell(c00544);
            tables04.addCell(c00554);
            tables04.addCell(c00564);
            tables04.addCell(c00574);
            tables04.addCell(c00584);
            tables04.addCell(c00594);
            tables04.addCell(c00604);
            tables04.addCell(c00614);
            tables04.addCell(c00624);
            tables04.addCell(c00634);
            tables04.addCell(c00644);
            tables04.addCell(c00654);
            tables04.addCell(c00664);
            tables04.addCell(c00674);
            tables04.addCell(c00684);
            tables04.addCell(c00694);
            tables04.addCell(c00704);
            tables04.addCell(c00714);
            tables04.addCell(c00724);
            tables04.addCell(c00734);
            tables04.addCell(c00744);
            tables04.addCell(c00754);
            tables04.addCell(c00764);
            tables04.addCell(c00774);
            tables04.addCell(c00784);
            tables04.addCell(c00794);
            tables04.addCell(c00804);
            tables04.addCell(c00814);
            tables04.addCell(c101);
            tables04.addCell(c102);
            tables04.addCell(c103);
            tables04.addCell(c104);
            tables04.addCell(c105);
            tables04.addCell(c106);
            tables04.addCell(c107);
            tables04.addCell(c108);
            tables04.addCell(c109);
            tables04.addCell(c110);
            tables04.addCell(c111);
            tables04.addCell(c112);
            tables04.addCell(c113);
            tables04.addCell(c114);
            tables04.addCell(c115);
            tables04.addCell(c116);
            tables04.addCell(c117);
            tables04.addCell(c118);
            tables04.addCell(c119);
            tables04.addCell(c120);
            tables04.addCell(c121);
            tables04.addCell(c122);
            tables04.addCell(c123);
            tables04.addCell(c124);
            tables04.addCell(c125);
            tables04.addCell(c126);
            tables04.addCell(c127);
            tables04.addCell(c128);
            document.add(tables04);

            PdfPTable tables05 = new PdfPTable(1);
            tables05.setWidthPercentage(100);
            PdfPCell c501 = new PdfPCell(new Phrase("This is computer generated payslip, not require any signature.",normalFont));
            c501.setPadding(4);
            c501.setBorder(Rectangle.TOP);
            tables05.addCell(c501);
            document.add(tables05);

            document.close();
            FilePathResponse dto = new FilePathResponse();
            dto.setPath(HelperUtils.FILEPATH + "TdsPaySlip"+ timemilisec + ".pdf");
            dto.setFileName("TdsPaySlip" + timemilisec + ".pdf");
            dtoList.add(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("An error occurred while generating TdsPaySlip.pdf ", e);
        }
        return ResponseUtils.createSuccessResponse(dtoList, new TypeReference<List<FilePathResponse>>() {
        });
    }


    @Override
    public ApiResponse<List<UserDetailsReq>> getAllEmployee() {

        List<UserDetailsReq> responce = new ArrayList<UserDetailsReq>();
        List<EmployeeJoiningInfo> isActiveUser = employeeJoiningInfoRepo.findByIsActive("1");

        for(int i = 0; i < isActiveUser.size(); i++) {

            UserDetailsReq obj=new UserDetailsReq();
            obj.setEmpCode(isActiveUser.get(i).getEmpCode());
            obj.setEmpType(isActiveUser.get(i).getEmpType());
            obj.setFirstName(isActiveUser.get(i).getFirstName());
            obj.setMiddleName(isActiveUser.get(i).getMiddleName());
            obj.setLastName(isActiveUser.get(i).getLastName());
            obj.setGender(isActiveUser.get(i).getGender());
            obj.setMaritalStatus(isActiveUser.get(i).getMaritalStatus());
            obj.setBloodGroup(isActiveUser.get(i).getBloodGroup());
            obj.setDesignation(isActiveUser.get(i).getDesignation());
            obj.setCtc(isActiveUser.get(i).getCtc());
            obj.setContactNo(isActiveUser.get(i).getContactNo());
            obj.setEmgContactNo(isActiveUser.get(i).getEmgContactNo());
            obj.setEmail(isActiveUser.get(i).getEmail());
            obj.setShiftType(isActiveUser.get(i).getShiftType());
            obj.setJoinDate(isActiveUser.get(i).getJoinDate());
            obj.setProbationDate(isActiveUser.get(i).getProbationDate());
            obj.setResgnDate(isActiveUser.get(i).getResgnDate());
            obj.setReasonForLeaving(isActiveUser.get(i).getReasonForLeaving());
            obj.setLwd(isActiveUser.get(i).getLwd());

            List<EmpBankReq> banks = new ArrayList<EmpBankReq>();
            List<EmpBankInfo> bankdt = empBankInfoRepo.findByEmpCodeAndIsActive(isActiveUser.get(i).getEmpCode(),"1");
            for(int j = 0; j < bankdt.size(); j++) {
                EmpBankReq bnk=new EmpBankReq();
                bnk.setBankName(bankdt.get(j).getBankName());
                bnk.setAccNo(bankdt.get(j).getAccNo());
                bnk.setIfsc(bankdt.get(j).getIfsc());
                bnk.setBranch(bankdt.get(j).getBranch());
                bnk.setAddress(bankdt.get(j).getAddress());
                banks.add(bnk);
            }
            obj.setEmpBanks(banks);

            List<EmpAddressReq> addRes = new ArrayList<EmpAddressReq>();
            List<EmpAddressInfo> empadd = empAddressInfoRepo.findByEmpCodeAndIsActive(isActiveUser.get(i).getEmpCode(),"1");
            for(int k = 0; k < empadd.size(); k++) {
                EmpAddressReq emp=new EmpAddressReq();
                emp.setAddressType(empadd.get(k).getAddressType());
                emp.setCountry(empadd.get(k).getCountry());
                emp.setState(empadd.get(k).getState());
                emp.setDistrict(empadd.get(k).getDistrict());
                emp.setPinCode(empadd.get(k).getPinCode());
                emp.setAddressLine1(empadd.get(k).getAddressLine1());
                emp.setAddressLine2(empadd.get(k).getAddressLine2());
                addRes.add(emp);
            }
            obj.setEmpAddress(addRes);

            List<EmpEduInfoReq> edu = new ArrayList<EmpEduInfoReq>();
            List<EmpEducationalInfo> empEducation = empEducationalInfoRepo.findByEmpCodeAndIsActive(isActiveUser.get(i).getEmpCode(),"1");
            for(int k = 0; k < empEducation.size(); k++) {
                EmpEduInfoReq empEdu=new EmpEduInfoReq();
                empEdu.setQualification(empEducation.get(k).getQualification());
                empEdu.setCollage(empEducation.get(k).getCollage());
                empEdu.setUniversity(empEducation.get(k).getUniversity());
                empEdu.setCourse(empEducation.get(k).getCourse());
                empEdu.setPassingYr(empEducation.get(k).getPassingYr());
                empEdu.setDiv(empEducation.get(k).getDiv());
                empEdu.setPercentage(empEducation.get(k).getPercentage());
                edu.add(empEdu);
            }
            obj.setEmpEdu(edu);

            List<PersonalInfoReq> empPers = new ArrayList<PersonalInfoReq>();
            List<EmpPersonalInfo> empprsnlObj = empPersonalInfoRepo.findByEmpCodeAndIsActive(isActiveUser.get(i).getEmpCode(),"1");
            for(int k = 0; k < empEducation.size(); k++) {
                PersonalInfoReq empPersnl=new PersonalInfoReq();
                empPersnl.setMotherName(empprsnlObj.get(k).getMotherName());
                empPersnl.setFatherName(empprsnlObj.get(k).getFatherName());
                empPersnl.setAadhaar(empprsnlObj.get(k).getAadhaar());
                empPersnl.setPan(empprsnlObj.get(k).getPan());
                empPersnl.setUan(empprsnlObj.get(k).getUan());
                empPersnl.setPassport(empprsnlObj.get(k).getPassport());
                empPersnl.setPassportValidTill(empprsnlObj.get(k).getPassportValidTill());
                empPersnl.setVisa(empprsnlObj.get(k).getVisa());
                empPers.add(empPersnl);
            }
            obj.setEmpPer(empPers);

            List<EmpProfessionalInfoReq> prof = new ArrayList<EmpProfessionalInfoReq>();
            List<EmpProfessionalInfo> profObj = empProfessionalInfoRepo.findByEmpCodeAndIsActive(isActiveUser.get(i).getEmpCode(),"1");
            for(int k = 0; k < empEducation.size(); k++) {
                EmpProfessionalInfoReq profDetails=new EmpProfessionalInfoReq();
                profDetails.setCompanyName(profObj.get(k).getCompanyName());
                profDetails.setCompanyAddress(profObj.get(k).getCompanyAddress());
                profDetails.setCompanyContact(profObj.get(k).getCompanyContact());
                profDetails.setPreEmpId(profObj.get(k).getPreEmpId());
                profDetails.setDesignation(profObj.get(k).getDesignation());
                profDetails.setCtc(profObj.get(k).getCtc());
                profDetails.setFromDate(profObj.get(k).getFromDate());
                profDetails.setToDate(profObj.get(k).getToDate());
                prof.add(profDetails);
            }
            obj.setEmpProf(prof);


            responce.add(obj);
        }
        return ResponseUtils.createSuccessResponse(responce, new TypeReference<List<UserDetailsReq>>() {
        });
    }

    @Override
    public ApiResponse<List<UserDetailsReq>> getEmployee(String empCode) {

        if (empCode == null || empCode.isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "EMPLOYEE CODE CAN NOT BE BLANK");
        }
        List<UserDetailsReq> responce = new ArrayList<UserDetailsReq>();
        List<EmployeeJoiningInfo> isActiveUser = employeeJoiningInfoRepo.findByEmpCodeAndIsActive(empCode,"1");

        if (isActiveUser.size() == 0 || isActiveUser.isEmpty()) {
            throw new SDDException(HttpStatus.UNAUTHORIZED.value(), "DATA NOT FOUND");
        }

        for(int i = 0; i < isActiveUser.size(); i++) {

            UserDetailsReq obj=new UserDetailsReq();
            obj.setEmpCode(isActiveUser.get(i).getEmpCode());
            obj.setEmpType(isActiveUser.get(i).getEmpType());
            obj.setFirstName(isActiveUser.get(i).getFirstName());
            obj.setMiddleName(isActiveUser.get(i).getMiddleName());
            obj.setLastName(isActiveUser.get(i).getLastName());
            obj.setGender(isActiveUser.get(i).getGender());
            obj.setMaritalStatus(isActiveUser.get(i).getMaritalStatus());
            obj.setBloodGroup(isActiveUser.get(i).getBloodGroup());
            obj.setDesignation(isActiveUser.get(i).getDesignation());
            obj.setCtc(isActiveUser.get(i).getCtc());
            obj.setContactNo(isActiveUser.get(i).getContactNo());
            obj.setEmgContactNo(isActiveUser.get(i).getEmgContactNo());
            obj.setEmail(isActiveUser.get(i).getEmail());
            obj.setShiftType(isActiveUser.get(i).getShiftType());
            obj.setJoinDate(isActiveUser.get(i).getJoinDate());
            obj.setProbationDate(isActiveUser.get(i).getProbationDate());
            obj.setResgnDate(isActiveUser.get(i).getResgnDate());
            obj.setReasonForLeaving(isActiveUser.get(i).getReasonForLeaving());
            obj.setLwd(isActiveUser.get(i).getLwd());

            List<EmpBankReq> banks = new ArrayList<EmpBankReq>();
            List<EmpBankInfo> bankdt = empBankInfoRepo.findByEmpCodeAndIsActive(empCode,"1");
            for(int j = 0; j < bankdt.size(); j++) {
                EmpBankReq bnk=new EmpBankReq();
                bnk.setBankName(bankdt.get(j).getBankName());
                bnk.setAccNo(bankdt.get(j).getAccNo());
                bnk.setIfsc(bankdt.get(j).getIfsc());
                bnk.setBranch(bankdt.get(j).getBranch());
                bnk.setAddress(bankdt.get(j).getAddress());
                banks.add(bnk);
            }
            obj.setEmpBanks(banks);

            List<EmpAddressReq> addRes = new ArrayList<EmpAddressReq>();
            List<EmpAddressInfo> empadd = empAddressInfoRepo.findByEmpCodeAndIsActive(empCode,"1");
            for(int k = 0; k < empadd.size(); k++) {
                EmpAddressReq emp=new EmpAddressReq();
                emp.setAddressType(empadd.get(k).getAddressType());
                emp.setCountry(empadd.get(k).getCountry());
                emp.setState(empadd.get(k).getState());
                emp.setDistrict(empadd.get(k).getDistrict());
                emp.setPinCode(empadd.get(k).getPinCode());
                emp.setAddressLine1(empadd.get(k).getAddressLine1());
                emp.setAddressLine2(empadd.get(k).getAddressLine2());
                addRes.add(emp);
            }
            obj.setEmpAddress(addRes);

            List<EmpEduInfoReq> edu = new ArrayList<EmpEduInfoReq>();
            List<EmpEducationalInfo> empEducation = empEducationalInfoRepo.findByEmpCodeAndIsActive(empCode,"1");
            for(int k = 0; k < empEducation.size(); k++) {
                EmpEduInfoReq empEdu=new EmpEduInfoReq();
                empEdu.setQualification(empEducation.get(k).getQualification());
                empEdu.setCollage(empEducation.get(k).getCollage());
                empEdu.setUniversity(empEducation.get(k).getUniversity());
                empEdu.setCourse(empEducation.get(k).getCourse());
                empEdu.setPassingYr(empEducation.get(k).getPassingYr());
                empEdu.setDiv(empEducation.get(k).getDiv());
                empEdu.setPercentage(empEducation.get(k).getPercentage());
                edu.add(empEdu);
            }
            obj.setEmpEdu(edu);

            List<PersonalInfoReq> empPers = new ArrayList<PersonalInfoReq>();
            List<EmpPersonalInfo> empprsnlObj = empPersonalInfoRepo.findByEmpCodeAndIsActive(empCode,"1");
            for(int k = 0; k < empEducation.size(); k++) {
                PersonalInfoReq empPersnl=new PersonalInfoReq();
                empPersnl.setMotherName(empprsnlObj.get(k).getMotherName());
                empPersnl.setFatherName(empprsnlObj.get(k).getFatherName());
                empPersnl.setAadhaar(empprsnlObj.get(k).getAadhaar());
                empPersnl.setPan(empprsnlObj.get(k).getPan());
                empPersnl.setUan(empprsnlObj.get(k).getUan());
                empPersnl.setPassport(empprsnlObj.get(k).getPassport());
                empPersnl.setPassportValidTill(empprsnlObj.get(k).getPassportValidTill());
                empPersnl.setVisa(empprsnlObj.get(k).getVisa());
                empPers.add(empPersnl);
            }
            obj.setEmpPer(empPers);

            List<EmpProfessionalInfoReq> prof = new ArrayList<EmpProfessionalInfoReq>();
            List<EmpProfessionalInfo> profObj = empProfessionalInfoRepo.findByEmpCodeAndIsActive(empCode,"1");
            for(int k = 0; k < empEducation.size(); k++) {
                EmpProfessionalInfoReq profDetails=new EmpProfessionalInfoReq();
                profDetails.setCompanyName(profObj.get(k).getCompanyName());
                profDetails.setCompanyAddress(profObj.get(k).getCompanyAddress());
                profDetails.setCompanyContact(profObj.get(k).getCompanyContact());
                profDetails.setPreEmpId(profObj.get(k).getPreEmpId());
                profDetails.setDesignation(profObj.get(k).getDesignation());
                profDetails.setCtc(profObj.get(k).getCtc());
                profDetails.setFromDate(profObj.get(k).getFromDate());
                profDetails.setToDate(profObj.get(k).getToDate());
                prof.add(profDetails);
            }
            obj.setEmpProf(prof);


            responce.add(obj);
        }
        return ResponseUtils.createSuccessResponse(responce, new TypeReference<List<UserDetailsReq>>() {
        });
    }

//    @Transactional(rollbackFor = {Exception.class})
//    @Override
//    public ApiResponse<DefaultResponse> updateEmp(UserDetailsReq userDetailsReq) {
//
//        DefaultResponse defaultResponse = new DefaultResponse();
//        List<EmployeeDetails> isActiveUser = employeeDetailsRepo.findByEmpCode(userDetailsReq.getEmpCode());
//
//        for(int j = 0; j < isActiveUser.size(); j++) {
//            EmployeeDetails obj = isActiveUser.get(j);
//            obj.setEmpCode(userDetailsReq.getEmpCode());
//            obj.setEmpFirstName(userDetailsReq.getEmpFirstName());
//            obj.setEmpMiddleName(userDetailsReq.getEmpMiddleName());
//            obj.setEmpLastName(userDetailsReq.getEmpLastName());
//            obj.setContactNo(userDetailsReq.getContactNo());
//            obj.setEmgContactNo(userDetailsReq.getEmgContactNo());
//            obj.setEmail(userDetailsReq.getEmail());
//            obj.setAadhaar(userDetailsReq.getAadhaar());
//            obj.setPan(userDetailsReq.getPan());
//            obj.setUan(userDetailsReq.getUan());
//            obj.setDesignation(userDetailsReq.getDesignation());
//            obj.setServingNotice(userDetailsReq.getServingNotice());
//            obj.setCtc(userDetailsReq.getCtc());
//            obj.setRoleCode(userDetailsReq.getRole());
//            obj.setCreatedBy("");
//            obj.setJoinDate(userDetailsReq.getJoinDate());
//            obj.setResgnDate(userDetailsReq.getResgnDate());
//            obj.setLwd(userDetailsReq.getLwd());
//            obj.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
//            employeeDetailsRepo.save(obj);
//        }
//        List<BankDetails> userBank = bankDetailsRepo.findByEmpCode(userDetailsReq.getEmpCode());
//        for(int k = 0; k < userBank.size(); k++) {
//            BankDetails objBank = userBank.get(k);
//            objBank.setEmpCode(userDetailsReq.getEmpCode());
//            objBank.setBankName(userDetailsReq.getBankName());
//            objBank.setAccNo(userDetailsReq.getAccNo());
//            objBank.setIfsc(userDetailsReq.getIfsc());
//            objBank.setBranch(userDetailsReq.getBranch());
//            objBank.setAddress(userDetailsReq.getAddress());
//            objBank.setIsActive("1");
//            objBank.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
//            bankDetailsRepo.save(objBank);
//        }
//        List<EmpAddress> empAdd = empAddressRepo.findByEmpCode(userDetailsReq.getEmpCode());
//        for(int i = 0; i < empAdd.size(); i++) {
//            EmpAddress objAdd = empAdd.get(i);
//            objAdd.setAddressType(userDetailsReq.getEmpAddressReqList().get(i).getAddressType());
//            objAdd.setEmpCode(userDetailsReq.getEmpCode());
//            objAdd.setCountry(userDetailsReq.getEmpAddressReqList().get(i).getCountry());
//            objAdd.setState(userDetailsReq.getEmpAddressReqList().get(i).getState());
//            objAdd.setDistrict(userDetailsReq.getEmpAddressReqList().get(i).getDistrict());
//            objAdd.setPinCode(userDetailsReq.getEmpAddressReqList().get(i).getPinCode());
//            objAdd.setAddressLine1(userDetailsReq.getEmpAddressReqList().get(i).getAddressLine1());
//            objAdd.setAddressLine2(userDetailsReq.getEmpAddressReqList().get(i).getAddressLine2());
//            objAdd.setIsActive("1");
//            objAdd.setUpdatedOn(HelperUtils.getCurrentTimeStamp());
//            empAddressRepo.save(objAdd);
//        }
//        defaultResponse.setMsg("Employee Details Updated successfully");
//        return ResponseUtils.createSuccessResponse(defaultResponse, new TypeReference<DefaultResponse>() {
//        });
//    }


}

