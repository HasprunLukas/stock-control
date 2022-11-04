package com.personal.stockcontroltest.service;

import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.Allergen;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AllergenService {
    List<Allergen> getAllAllergens();
    Allergen getAllergenById(Long allergenId) throws ResourceNotFoundException;
    Allergen createAllergen(Allergen allergen) throws AlreadyExistsException;
    Allergen updateAllergen(Allergen allergen, Long allergenId) throws ResourceNotFoundException, AlreadyExistsException;
    void deleteAllergen(Long allergenId) throws ResourceNotFoundException;
}
