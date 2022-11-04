package com.personal.stockcontroltest.controller;


import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.Allergen;
import com.personal.stockcontroltest.service.AllergenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/allergen")
public class AllergenController {

    @Autowired
    AllergenService allergenService;

    @GetMapping
    public ResponseEntity<List<Allergen>> getAll() {
        List<Allergen> allergens = new ArrayList<>(allergenService.getAllAllergens());
        if(allergens.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(allergens, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Allergen> getById(@PathVariable(value = "id") Long allergenId) throws ResourceNotFoundException {
        return new ResponseEntity<>(allergenService.getAllergenById(allergenId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Allergen> createAllergen(@Valid @RequestBody Allergen allergen) throws AlreadyExistsException {
        return new ResponseEntity<>(allergenService.createAllergen(allergen), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Allergen> updateAllergen(@PathVariable(value = "id") Long allergenId,
                                                     @Valid @RequestBody Allergen allergen) throws ResourceNotFoundException, AlreadyExistsException {
        return new ResponseEntity<>(allergenService.updateAllergen(allergen, allergenId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAllergen(@PathVariable(value = "id") Long allergenId) throws ResourceNotFoundException {
        allergenService.deleteAllergen(allergenId);

        return new ResponseEntity<>("Allergen with id " + allergenId + " successfully deleted", HttpStatus.OK);
    }
}
