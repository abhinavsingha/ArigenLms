package com.lms.service;


import com.lms.entities.MasBloodGroup;
import com.lms.entities.MasGender;
import com.lms.responce.ApiResponse;
import com.lms.responce.StateResponse;

import java.util.List;
import java.util.Optional;

public interface MasterService {
    ApiResponse<List<StateResponse>> getStateData(int countryId);

    ApiResponse<List<StateResponse>> getAllStateData();

    ApiResponse<Optional<StateResponse>> getStateDataByStateId(int stateId);

    ApiResponse<List<MasBloodGroup>> getAllBloodGroup();

    ApiResponse<Optional<MasBloodGroup>> getBloodGroupByBloodGroupId(int bloodGroupId);

    ApiResponse<List<MasGender>> getAllGender();

    ApiResponse<Optional<MasGender>> getGenderById(int genderId);
}
