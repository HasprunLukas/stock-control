package com.personal.stockcontroltest.service;

import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.StockType;
import com.personal.stockcontroltest.repository.StockTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockTypeServiceImpl implements StockTypeService {

    @Autowired
    StockTypeRepository stockTypeRepository;

    @Override
    public List<StockType> getAllStockTypes() {
        return stockTypeRepository.findAll();
    }

    @Override
    public StockType getStockTypeById(Long stockTypeId) throws ResourceNotFoundException {
        return stockTypeRepository.findById(stockTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock type with id: " + stockTypeId +" not found"));
    }

    @Override
    public StockType createStockType(StockType stockType) throws AlreadyExistsException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        StockType probe = new StockType();
        probe.setName(stockType.getName());
        if(stockTypeRepository.count(Example.of(probe, matcher)) > 0) {
            throw new AlreadyExistsException("Stock type with name: '" + stockType.getName() + "' already exists");
        }

        return stockTypeRepository.save(stockType);
    }

    @Override
    public StockType updateStockType(StockType stockType, Long stockTypeId) throws ResourceNotFoundException, AlreadyExistsException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        StockType stockTypeToUpdate = stockTypeRepository.findById(stockTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock type with id: " + stockTypeId + " not found"));
        if(stockTypeRepository.count(Example.of(stockType, matcher)) > 0 && !stockType.getName().equals(stockTypeToUpdate.getName())) {
            throw new AlreadyExistsException("Stock type with name: '" + stockType.getName() + "' already exists");
        }
        if(stockType.getName() != null && !stockType.getName().trim().isEmpty()) {
            stockTypeToUpdate.setName(stockType.getName());
        }

        return stockTypeRepository.save(stockTypeToUpdate);
    }

    @Override
    public void deleteStockType(Long stockTypeId) throws ResourceNotFoundException {
        StockType stockTypeToDelete = stockTypeRepository.findById(stockTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock type with id: " + stockTypeId + " not found"));

        stockTypeRepository.delete(stockTypeToDelete);
    }
}
