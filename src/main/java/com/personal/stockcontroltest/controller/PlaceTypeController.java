package com.personal.stockcontroltest.controller;


import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.PlaceType;
import com.personal.stockcontroltest.service.PlaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/place_type")
public class PlaceTypeController {

    @Autowired
    PlaceTypeService placeTypeService;

    @GetMapping
    public ResponseEntity<List<PlaceType>> getAll() {
        List<PlaceType> placeTypes = new ArrayList<>(placeTypeService.getAllPlaceTypes());
        if(placeTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(placeTypes, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlaceType> getById(@PathVariable(value = "id") Long placeTypeId) throws ResourceNotFoundException {
        return new ResponseEntity<>(placeTypeService.getPlaceTypeById(placeTypeId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlaceType> createPlaceType(@Valid @RequestBody PlaceType placeType) throws AlreadyExistsException {
        return new ResponseEntity<>(placeTypeService.createPlaceType(placeType), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<PlaceType> updatePlaceType(@PathVariable(value = "id") Long placeTypeId,
                                                     @Valid @RequestBody PlaceType placeType) throws ResourceNotFoundException, AlreadyExistsException {
        return new ResponseEntity<>(placeTypeService.updatePlaceType(placeType, placeTypeId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePlaceType(@PathVariable(value = "id") Long placeTypeId) throws ResourceNotFoundException {
        placeTypeService.deletePlaceType(placeTypeId);

        return new ResponseEntity<>("Place type with id " + placeTypeId + " successfully deleted", HttpStatus.OK);
    }
}
