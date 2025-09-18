package com.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.Exchange;
import com.api.service.ExchangeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@EnableMethodSecurity
@RequestMapping("/exchange")
public class ExchangeController {

	private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);
	
	@Autowired
	private ExchangeService service;
	
	@PostMapping("/save")
	@Operation(summary = "Adding new the Exchange stock details ")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addingExchangeStock(@RequestBody Exchange exchanges){
		  logger.info("Request received to add exchange stock: {}", exchanges);
		Exchange saveExchangeDetails = service.saveExchangeDetails(exchanges);
		String msg = "Exchange stock saved succesfully";
		logger.info("Exchange added successfully with ID: {}", saveExchangeDetails.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(msg);
	}
	
	@GetMapping("/get/{id}")
	@Operation(summary = "Fetching one exchangestock details based on ID ")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Exchange> fetchExchangeDetails(@PathVariable("id") Integer id) {
		logger.info("Request received to fetch exchange with ID: {}", id);
		Exchange exchange = service.getOneExchangeDetails(id);  
		logger.info("exchange fetched successfully: {}", exchange);
		return ResponseEntity.ok(exchange);
	}

	@GetMapping("/getAllExchanges")
	@Operation(summary = "Fetching all the exchange stock details ")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Exchange>> fetchAllExchangeDetails(){
		logger.info("Request received to fetch all Exchange");
		List<Exchange> exchange = service.getAllExchangeDetails();
		String msg = "Fected all Exchange succesfully";
		logger.debug("Number of exchanges fetched: {}", exchange.size());
		return ResponseEntity.ok(exchange);
	}

	@PutMapping("/updateExchange")
	@Operation(summary = "Updating the exchange stock details ")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateExchangeDetails(@RequestBody Exchange exchange) {
		logger.info("Request received to update exchange: {}", exchange);
		service.updateExchangeDetails(exchange);
		logger.info("Exchange updated successfully with ID: {}", exchange.getId());
		return ResponseEntity.ok("Exchange updated successfully.");
	}
	
	@DeleteMapping("/delete/{id}")
	@Operation(summary = "Deleting one exchange stock details ")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteOneExchange(@PathVariable("id") Integer id){
		 logger.info("Request received to delete Exchange with ID: {}", id);
		service.deleteOneExchange(id);
		String msg = "Exchange with "+id+" deleted succesfully";
		 logger.info("Exchange deleted successfully with ID: {}", id);
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	@DeleteMapping("/deleteAll")
	@Operation(summary = "Deleting all exchange stock details ")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteAllExchanges() {
		 logger.warn("Request received to delete all Exchanges");
	   service.deleteAllExchangeDetails();
	    String msg = "All Exchanges deleted successfully..";
	    logger.info("All Exchanges deleted successfully");
	    return ResponseEntity.ok(msg);
	}

	@GetMapping("/{id}/stock/{sid}")
	@Operation(summary = "Fetching the details based on stockID ")
	@PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getStockForExchange(@PathVariable Integer id, @PathVariable Integer sid) {
        logger.info("Received request to fetch stock with ID: {} for exchange ID: {}", sid, id);
        Object stock = service.getStockForExchange(sid);
        return ResponseEntity.ok(stock); // 200 OK
    }

    @GetMapping("/{id}/user/{uid}")
    @Operation(summary = "Fetching the details based on userID ")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getUserForExchange(@PathVariable Integer id, @PathVariable Integer uid) {
        logger.info("Received request to fetch user with ID: {} for exchange ID: {}", uid, id);
        Object user = service.getUserForExchange(uid);
        return ResponseEntity.ok(user); // 200 OK
    }
	
}
