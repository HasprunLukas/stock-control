package com.personal.stockcontroltest.repository;

import com.personal.stockcontroltest.model.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlaceTypeRepository extends JpaRepository<PlaceType, Long> {
}
