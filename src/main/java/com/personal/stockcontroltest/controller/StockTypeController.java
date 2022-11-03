package com.personal.stockcontroltest.controller;


import com.personal.stockcontroltest.exception.AlreadyExistsException;
import com.personal.stockcontroltest.exception.ResourceNotFoundException;
import com.personal.stockcontroltest.model.StockType;
import com.personal.stockcontroltest.service.StockTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stock_type")
public class StockTypeController {

    @Autowired
    StockTypeService stockTypeService;

    @GetMapping
    public ResponseEntity<List<StockType>> getAll() {
        List<StockType> stockTypes = new ArrayList<>(stockTypeService.getAllStockTypes());
        if(stockTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(stockTypes, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StockType> getById(@PathVariable(value = "id") Long stockTypeId) throws ResourceNotFoundException {
        return new ResponseEntity<>(stockTypeService.getStockTypeById(stockTypeId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StockType> createStockType(@Valid @RequestBody StockType stockType) throws AlreadyExistsException {
        return new ResponseEntity<>(stockTypeService.createStockType(stockType), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<StockType> updateStockType(@PathVariable(value = "id") Long stockTypeId,
                                                     @Valid @RequestBody StockType stockType) throws ResourceNotFoundException, AlreadyExistsException {
        return new ResponseEntity<>(stockTypeService.updateStockType(stockType, stockTypeId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStockType(@PathVariable(value = "id") Long stockTypeId) throws ResourceNotFoundException {
        stockTypeService.deleteStockType(stockTypeId);

        return new ResponseEntity<>("Stock type with id " + stockTypeId + " successfully deleted", HttpStatus.OK);
    }
}