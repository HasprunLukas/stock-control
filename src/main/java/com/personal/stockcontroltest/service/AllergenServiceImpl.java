package com.personal.stockcontroltest.service;

import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.Allergen;
import com.personal.stockcontroltest.repository.AllergenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllergenServiceImpl implements AllergenService {

    @Autowired
    AllergenRepository allergenRepository;

    @Override
    public List<Allergen> getAllAllergens() {
        return allergenRepository.findAll();
    }

    @Override
    public Allergen getAllergenById(Long allergenId) throws ResourceNotFoundException {
        return allergenRepository.findById(allergenId)
                .orElseThrow(() -> new ResourceNotFoundException("Allergen with id: " + allergenId +" not found"));
    }

    @Override
    public Allergen createAllergen(Allergen allergen) throws AlreadyExistsException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        Allergen probe = new Allergen();
        probe.setName(allergen.getName());
        if(allergenRepository.count(Example.of(probe, matcher)) > 0) {
            throw new AlreadyExistsException("Allergen with name: '" + allergen.getName() + "' already exists");
        }

        return allergenRepository.save(allergen);
    }

    @Override
    public Allergen updateAllergen(Allergen allergen, Long allergenId) throws ResourceNotFoundException, AlreadyExistsException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        Allergen allergenToUpdate = allergenRepository.findById(allergenId)
                .orElseThrow(() -> new ResourceNotFoundException("Allergen with id: " + allergenId + " not found"));
        if(allergenRepository.count(Example.of(allergen, matcher)) > 0 && !allergen.getName().equals(allergenToUpdate.getName())) {
            throw new AlreadyExistsException("Allergen with name: '" + allergen.getName() + "' already exists");
        }
        if(allergen.getName() != null && !allergen.getName().trim().isEmpty()) {
            allergenToUpdate.setName(allergen.getName());
        }

        return allergenRepository.save(allergenToUpdate);
    }

    @Override
    public void deleteAllergen(Long allergenId) throws ResourceNotFoundException {
        Allergen allergenToDelete = allergenRepository.findById(allergenId)
                .orElseThrow(() -> new ResourceNotFoundException("Allergen with id: " + allergenId + " not found"));

        allergenRepository.delete(allergenToDelete);
    }
}
