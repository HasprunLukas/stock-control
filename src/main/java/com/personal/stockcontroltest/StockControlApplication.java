package com.personal.stockcontroltest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockControlApplication {
    static Logger logger = LoggerFactory.getLogger(StockControlApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StockControlApplication.class, args);
        logger.info("SpringBootApplication started on localhost:8080");
    }

}
