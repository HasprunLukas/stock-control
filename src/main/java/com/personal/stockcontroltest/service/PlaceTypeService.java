package com.personal.stockcontroltest.service;

import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.PlaceType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceTypeService {
    List<PlaceType> getAllPlaceTypes();
    PlaceType getPlaceTypeById(Long placeTypeId) throws ResourceNotFoundException;
    PlaceType createPlaceType(PlaceType placeType) throws AlreadyExistsException;
    PlaceType updatePlaceType(PlaceType placeType, Long placeTypeId) throws ResourceNotFoundException, AlreadyExistsException;
    void deletePlaceType(Long placeTypeId) throws ResourceNotFoundException;
}
