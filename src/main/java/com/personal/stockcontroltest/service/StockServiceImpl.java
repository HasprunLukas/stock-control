package com.personal.stockcontroltest.service;

import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.Stock;
import com.personal.stockcontroltest.repository.PlaceTypeRepository;
import com.personal.stockcontroltest.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    PlaceTypeRepository placeTypeRepository;

    @Override
    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockById(Long stockId) throws ResourceNotFoundException {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock with id: " + stockId +" not found"));
    }

    @Override
    public Stock createStock(Stock stock) throws AlreadyExistsException, ResourceNotFoundException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        Stock probe = new Stock();
        probe.setName(stock.getName());
        if(stockRepository.count(Example.of(probe, matcher)) > 0) {
            throw new AlreadyExistsException("Stock with name: '" + stock.getName() + "' already exists");
        }

        placeTypeRepository.findById(stock.getPlaceType().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Place type with id: " + stock.getPlaceType().getId() + " not found"));

        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStock(Stock stock, Long stockId) throws AlreadyExistsException, ResourceNotFoundException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "checkup_date", "placeType");
        Stock stockToUpdate = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock with id: " + stockId + " not found"));
        if(stockRepository.count(Example.of(stock, matcher)) > 0 && !stock.getName().equals(stockToUpdate.getName())) {
            throw new AlreadyExistsException("Stock with name: '" + stock.getName() + "' already exists");
        }

        placeTypeRepository.findById(stock.getPlaceType().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Place type with id: " + stock.getPlaceType().getId() + " not found"));

        if(stock.getName() != null && !stock.getName().trim().isEmpty()) {
            stockToUpdate.setName(stock.getName());
        }
        if(stock.getCheckup_date() != null) {
            stockToUpdate.setCheckup_date(stock.getCheckup_date());
        }
        stockToUpdate.setPlaceType(stock.getPlaceType());

        return stockRepository.save(stockToUpdate);
    }

    @Override
    public void deleteStock(Long stockId) throws ResourceNotFoundException {
        Stock stockToDelete = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock with id: " + stockId + " not found"));

        stockRepository.delete(stockToDelete);
    }
}
