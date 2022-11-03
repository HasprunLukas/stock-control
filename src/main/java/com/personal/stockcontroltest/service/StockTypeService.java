package com.personal.stockcontroltest.service;

import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.StockType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockTypeService {
    List<StockType> getAllStockTypes();
    StockType getStockTypeById(Long stockTypeId) throws ResourceNotFoundException;
    StockType createStockType(StockType stockType) throws AlreadyExistsException;
    StockType updateStockType(StockType stockType, Long stockTypeId) throws ResourceNotFoundException, AlreadyExistsException;
    void deleteStockType(Long stockTypeId) throws ResourceNotFoundException;
}
