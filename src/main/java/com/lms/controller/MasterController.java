package com.lms.controller;

import com.lms.entities.DgMasInvestigation;
import com.lms.entities.MasBloodGroup;
import com.lms.entities.MasGender;
import com.lms.responce.ApiResponse;
import com.lms.responce.StateResponse;
import com.lms.service.MasterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "${angular.corss.url}")
@RestController
@Tag(name = "masterController",description = "This controller only for use Get Master Data.")
@RequestMapping("/masterController")
@Slf4j
public class MasterController {

    @Autowired
    private MasterService masterService;

    /**
     * Master data for State
     */
    @GetMapping("/getStateDataByCountryId/{countryId}")
    public ResponseEntity<ApiResponse<List<StateResponse>>> getStateData(@PathVariable("countryId") int countryId) {
        log.info("Received request to fetch state data for countryId: {}", countryId);

        ApiResponse<List<StateResponse>> response = masterService.getStateData(countryId);

        log.debug("Response for getStateDataByCountryId: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllStateData")
    public ResponseEntity<ApiResponse<List<StateResponse>>> getAllStateData() {
        log.info("getAllStateData method called");

        ApiResponse<List<StateResponse>> response = masterService.getAllStateData();

        log.debug("Response for getAllStateData: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getStateDataByStateId/{stateId}")
    public ResponseEntity<ApiResponse<Optional<StateResponse>>> getStateDataByStateId(@PathVariable("stateId") int stateId) {
        log.info("Received request to fetch state data for stateId: {}", stateId);

        ApiResponse<Optional<StateResponse>> response = masterService.getStateDataByStateId(stateId);

        log.debug("Response for getStateDataByStateId: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Master data for BloodGroup
     */
    @GetMapping("/getAllBloodGroup")
    public ResponseEntity<ApiResponse<List<MasBloodGroup>>> getAllBloodGroup() {
        log.info("getAllBloodGroup method called");

        ApiResponse<List<MasBloodGroup>> response = masterService.getAllBloodGroup();

        log.debug("Response for getAllBloodGroup: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBloodGroupById/{bloodGroupId}")
    public ResponseEntity<ApiResponse<Optional<MasBloodGroup>>> getBloodGroupByBloodGroupId(@PathVariable("bloodGroupId") int bloodGroupId) {
        log.info("Received request to fetch blood group with bloodGroupId: {}", bloodGroupId);

        ApiResponse<Optional<MasBloodGroup>> response = masterService.getBloodGroupByBloodGroupId(bloodGroupId);

        log.debug("Response for getBloodGroupById: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Master data for Gender
     */
    @GetMapping("/getAllGender")
    public ResponseEntity<ApiResponse<List<MasGender>>> getAllGender() {
        log.info("getAllGender method called");

        ApiResponse<List<MasGender>> response = masterService.getAllGender();

        log.debug("Response for getAllGender: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getGenderById/{genderId}")
    public ResponseEntity<ApiResponse<Optional<MasGender>>> getGenderById(@PathVariable("genderId") int genderId) {
        log.info("Received request to fetch gender with genderId: {}", genderId);

        ApiResponse<Optional<MasGender>> response = masterService.getGenderById(genderId);

        log.debug("Response for getGenderById: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/getAllInvestigation")
    public ResponseEntity<ApiResponse<List<DgMasInvestigation>>> getAllInvestigation() {
        ApiResponse<List<DgMasInvestigation>> response = masterService.getAllInvestigation();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getInvestigationnames/{search}")
    public ResponseEntity<ApiResponse<List<DgMasInvestigation>>> GetInvestigationNames(String search) {
        ApiResponse<List<DgMasInvestigation>> response = masterService.GetInvestigationNames(search);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
