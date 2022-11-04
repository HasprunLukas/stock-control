package com.personal.stockcontroltest.repository;

import com.personal.stockcontroltest.model.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long> {
}
