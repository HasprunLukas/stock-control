package com.personal.stockcontroltest.controller;

import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.Stock;
import com.personal.stockcontroltest.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAll() {
        List<Stock> stocks = new ArrayList<>(stockService.getAllStock());
        if(stocks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Stock> getById(@PathVariable(value = "id") Long stockId) throws ResourceNotFoundException {
        return new ResponseEntity<>(stockService.getStockById(stockId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@Valid @RequestBody Stock stock) throws AlreadyExistsException, ResourceNotFoundException {
        return new ResponseEntity<>(stockService.createStock(stock), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable(value = "id") Long stockId,
                                             @Valid @RequestBody Stock stock) throws AlreadyExistsException, ResourceNotFoundException {
        return new ResponseEntity<>(stockService.updateStock(stock, stockId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStock(@PathVariable(value = "id") Long stockId) throws ResourceNotFoundException {
        stockService.deleteStock(stockId);

        return new ResponseEntity<>("Stock with id " + stockId + " successfully deleted", HttpStatus.OK);
    }
}
