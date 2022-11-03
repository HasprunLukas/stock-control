package com.personal.stockcontroltest.repository;

import com.personal.stockcontroltest.model.StockType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTypeRepository extends JpaRepository<StockType, Long> {
}
