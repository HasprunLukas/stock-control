package com.personal.stockcontroltest.service;

import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.PlaceType;
import com.personal.stockcontroltest.repository.PlaceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceTypeServiceImpl implements PlaceTypeService {

    @Autowired
    PlaceTypeRepository placeTypeRepository;

    @Override
    public List<PlaceType> getAllPlaceTypes() {
        return placeTypeRepository.findAll();
    }

    @Override
    public PlaceType getPlaceTypeById(Long placeTypeId) throws ResourceNotFoundException {
        return placeTypeRepository.findById(placeTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place type with id: " + placeTypeId +" not found"));
    }

    @Override
    public PlaceType createPlaceType(PlaceType placeType) throws AlreadyExistsException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        PlaceType probe = new PlaceType();
        probe.setName(placeType.getName());
        if(placeTypeRepository.count(Example.of(probe, matcher)) > 0) {
            throw new AlreadyExistsException("Place type with name: '" + placeType.getName() + "' already exists");
        }

        return placeTypeRepository.save(placeType);
    }

    @Override
    public PlaceType updatePlaceType(PlaceType placeType, Long placeTypeId) throws ResourceNotFoundException, AlreadyExistsException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        PlaceType placeTypeToUpdate = placeTypeRepository.findById(placeTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place type with id: " + placeTypeId + " not found"));
        if(placeTypeRepository.count(Example.of(placeType, matcher)) > 0 && !placeType.getName().equals(placeTypeToUpdate.getName())) {
            throw new AlreadyExistsException("Place type with name: '" + placeType.getName() + "' already exists");
        }
        if(placeType.getName() != null && !placeType.getName().trim().isEmpty()) {
            placeTypeToUpdate.setName(placeType.getName());
        }

        return placeTypeRepository.save(placeTypeToUpdate);
    }

    @Override
    public void deletePlaceType(Long placeTypeId) throws ResourceNotFoundException {
        PlaceType placeTypeToDelete = placeTypeRepository.findById(placeTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place type with id: " + placeTypeId + " not found"));

        placeTypeRepository.delete(placeTypeToDelete);
    }
}
