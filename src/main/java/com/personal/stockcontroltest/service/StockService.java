package com.personal.stockcontroltest.service;

import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockService {
    List<Stock> getAllStock();
    Stock getStockById(Long stockId) throws ResourceNotFoundException;
    Stock createStock(Stock stock) throws AlreadyExistsException, ResourceNotFoundException;
    Stock updateStock(Stock stock, Long stockId) throws AlreadyExistsException, ResourceNotFoundException;
    void deleteStock(Long stockId) throws ResourceNotFoundException;
}
