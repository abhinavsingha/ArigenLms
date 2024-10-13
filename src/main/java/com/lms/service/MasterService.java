package com.lms.service;


import com.lms.entities.DgMasInvestigation;
import com.lms.entities.MasBloodGroup;
import com.lms.entities.MasGender;
import com.lms.responce.ApiResponse;
import com.lms.responce.StateResponse;

import java.util.List;
import java.util.Optional;

public interface MasterService {
    ApiResponse<List<StateResponse>> getStateData(int countryId);

    ApiResponse<List<StateResponse>> getAllStateData();

    ApiResponse<Optional<StateResponse>> getStateDataByStateId(long stateId);

    ApiResponse<List<MasBloodGroup>> getAllBloodGroup();

    ApiResponse<Optional<MasBloodGroup>> getBloodGroupByBloodGroupId(long bloodGroupId);

    ApiResponse<List<MasGender>> getAllGender();

    ApiResponse<Optional<MasGender>> getGenderById(long genderId);

    ApiResponse<List<DgMasInvestigation>> getAllInvestigation();

    ApiResponse<List<DgMasInvestigation>> GetInvestigationNames(String search);
}
