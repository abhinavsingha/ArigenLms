package com.lms.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lms.entities.MasBloodGroup;
import com.lms.entities.MasGender;
import com.lms.entities.MasState;
import com.lms.entities.repo.MasBloodGroupRepository;
import com.lms.entities.repo.MasGenderRepository;
import com.lms.entities.repo.MasStateRepository;
import com.lms.helperUtil.ResponseUtils;
import com.lms.responce.ApiResponse;
import com.lms.responce.StateResponse;
import com.lms.service.MasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class MasterServiceImpl implements MasterService {

    private static final Logger logger = LoggerFactory.getLogger(MasterServiceImpl.class);

    @Autowired
    MasStateRepository masStateRepository;

    @Autowired
    MasGenderRepository masGenderRepository;

    @Autowired
    MasBloodGroupRepository masBloodGroupRepository;

    @Override
    public ApiResponse<List<StateResponse>> getStateData(int countryId) {
        logger.info("Fetching states for countryId {}", countryId);

        List<MasState> stateListByCountry = masStateRepository.findByCountryId(countryId);
        if (stateListByCountry.isEmpty()) {
            logger.error("No states found for countryId {}", countryId);
            return ResponseUtils.createNotFoundResponse("No states found for countryId " + countryId, HttpStatus.NOT_FOUND.value());
        }

        List<StateResponse> stateResponses = new ArrayList<>();
        for (MasState state : stateListByCountry) {
            StateResponse stateRes = new StateResponse();
            stateRes.setStateCode(state.getStateCode());
            stateRes.setId(state.getId());
            stateRes.setStateName(state.getStateName());
            stateRes.setHicCode(state.getHicCode());
            stateRes.setStatus(state.getStatus());
            stateRes.setLastChgBy(state.getLastChgBy());
            stateRes.setLastChgTime(state.getLastChgTime());
            stateRes.setLastChgDate(state.getLastChgDate());
            stateResponses.add(stateRes);
        }

        logger.debug("States found for countryId {}: {}", countryId, stateResponses);
        return ResponseUtils.createSuccessResponse(stateResponses, new TypeReference<List<StateResponse>>() {
        });
    }

    @Override
    public ApiResponse<List<StateResponse>> getAllStateData() {
        logger.info("Fetching all states");

        List<MasState> stateList = masStateRepository.findAll();
        if (stateList.isEmpty()) {
            logger.error("No states found");
            return ResponseUtils.createNotFoundResponse("No states found", HttpStatus.NOT_FOUND.value());
        }

        List<StateResponse> stateResponses = new ArrayList<>();
        for (MasState state : stateList) {
            StateResponse stateRes = new StateResponse();
            stateRes.setStateCode(state.getStateCode());
            stateRes.setId(state.getId());
            stateRes.setStateName(state.getStateName());
            stateRes.setHicCode(state.getHicCode());
            stateRes.setStatus(state.getStatus());
            stateRes.setLastChgBy(state.getLastChgBy());
            stateRes.setLastChgTime(state.getLastChgTime());
            stateRes.setLastChgDate(state.getLastChgDate());
            stateResponses.add(stateRes);
        }

        logger.debug("All states fetched: {}", stateResponses);
        return ResponseUtils.createSuccessResponse(stateResponses, new TypeReference<List<StateResponse>>() {
        });
    }

    @Override
    public ApiResponse<Optional<StateResponse>> getStateDataByStateId(int stateId) {
        logger.info("Fetching state with ID {}", stateId);

        Optional<MasState> stateById = masStateRepository.findById(String.valueOf(stateId));
        if (stateById.isEmpty()) {
            logger.error("State with ID {} not found", stateId);
            return ResponseUtils.createNotFoundResponse("State with ID " + stateId + " not found", HttpStatus.NOT_FOUND.value());
        }

        MasState state = stateById.get();
        StateResponse stateRes = new StateResponse();
        stateRes.setStateCode(state.getStateCode());
        stateRes.setId(state.getId());
        stateRes.setStateName(state.getStateName());
        stateRes.setHicCode(state.getHicCode());
        stateRes.setStatus(state.getStatus());
        stateRes.setLastChgBy(state.getLastChgBy());
        stateRes.setLastChgTime(state.getLastChgTime());
        stateRes.setLastChgDate(state.getLastChgDate());

        logger.debug("State found: {}", stateRes);
        return ResponseUtils.createSuccessResponse(Optional.of(stateRes), new TypeReference<Optional<StateResponse>>() {
        });
    }

    @Override
    public ApiResponse<List<MasBloodGroup>> getAllBloodGroup() {
        logger.info("Fetching all blood groups");

        List<MasBloodGroup> bloodGroups = masBloodGroupRepository.findAll();
        if (bloodGroups.isEmpty()) {
            logger.error("No blood groups found");
            return ResponseUtils.createNotFoundResponse("No blood groups found", HttpStatus.NOT_FOUND.value());
        }

        logger.debug("All blood groups fetched: {}", bloodGroups);
        return ResponseUtils.createSuccessResponse(bloodGroups, new TypeReference<List<MasBloodGroup>>() {
        });
    }

    @Override
    public ApiResponse<Optional<MasBloodGroup>> getBloodGroupByBloodGroupId(int bloodGroupId) {
        logger.info("Fetching blood group with ID {}", bloodGroupId);

        Optional<MasBloodGroup> bloodGroup = masBloodGroupRepository.findById(String.valueOf(bloodGroupId));
        if (bloodGroup.isEmpty()) {
            logger.error("Blood group with ID {} not found", bloodGroupId);
            return ResponseUtils.createNotFoundResponse("Blood group with ID " + bloodGroupId + " not found", HttpStatus.NOT_FOUND.value());
        }

        logger.debug("Blood group found: {}", bloodGroup.get());
        return ResponseUtils.createSuccessResponse(bloodGroup, new TypeReference<Optional<MasBloodGroup>>() {
        });
    }

    @Override
    public ApiResponse<List<MasGender>> getAllGender() {
        logger.info("Fetching all genders");

        List<MasGender> genders = masGenderRepository.findAll();
        if (genders.isEmpty()) {
            logger.error("No genders found");
            return ResponseUtils.createNotFoundResponse("No genders found", HttpStatus.NOT_FOUND.value());
        }

        logger.debug("All genders fetched: {}", genders);
        return ResponseUtils.createSuccessResponse(genders, new TypeReference<List<MasGender>>() {
        });
    }

    @Override
    public ApiResponse<Optional<MasGender>> getGenderById(int genderId) {
        logger.info("Fetching gender with ID {}", genderId);

        Optional<MasGender> gender = masGenderRepository.findById(String.valueOf(genderId));
        if (gender.isEmpty()) {
            logger.error("Gender with ID {} not found", genderId);
            return ResponseUtils.createNotFoundResponse("Gender with ID " + genderId + " not found", HttpStatus.NOT_FOUND.value());
        }

        logger.debug("Gender found: {}", gender.get());
        return ResponseUtils.createSuccessResponse(gender, new TypeReference<Optional<MasGender>>() {
        });
    }
}
