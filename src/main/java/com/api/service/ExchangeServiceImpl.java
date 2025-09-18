package com.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.exception.ExchangeNotAvailableException;
import com.api.model.Exchange;
import com.api.repo.ExchangeRepository;

@Service
public class ExchangeServiceImpl implements ExchangeService {

	private static final Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);
	
	@Autowired
	private ExchangeRepository repo;
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Exchange saveExchangeDetails(Exchange exchange) {
		logger.info("Saving exchange : {}", exchange);
		Exchange savedexchange = repo.save(exchange);
		 logger.info("User saved successfully with ID: {}", savedexchange.getId());
		return savedexchange;
	}

	@Override
	public Exchange getOneExchangeDetails(Integer id) {
		logger.info("Fetching Exchange with ID: {}", id);
		return repo.findById(id).orElseThrow(() -> {
                logger.error("Exchange with ID {} not found", id);
                return new ExchangeNotAvailableException("User with ID " + id + " not found in Database");
            });
	}

	@Override
	public List<Exchange> getAllExchangeDetails() {
		logger.info("Fetching all users from database");
        List<Exchange> exchange = repo.findAll();
        logger.debug("Number of Exchange fetched: {}", exchange.size());
        return exchange;
	}

	@Override
	public void deleteOneExchange(Integer id) {
		 logger.info("Deleting Exchange with ID: {}", id);
	        repo.deleteById(id);
	        logger.info("Exchange deleted successfully with ID: {}", id);
	}

	@Override
	public void deleteAllExchangeDetails() {
		 logger.warn("Deleting all Exchange records!!");
	        repo.deleteAll();
	        logger.info("All Exchanges deleted successfully");
	}

	@Override
	public void updateExchangeDetails(Exchange exchange) {
		logger.info("Updating user with ID: {}", exchange.getId());
		Exchange exchangeExists = repo.findById(exchange.getId())
                              .orElseThrow(() -> {
                                  logger.error("User not found with ID: {}", exchange.getId());
                                  return new ExchangeNotAvailableException("Record not available in DB with ID " + exchange.getId());
                              });
		exchangeExists.setName(exchange.getName());
		exchangeExists.setCountry(exchange.getCountry());
		exchangeExists.setDescription(exchange.getDescription());
        repo.save(exchangeExists);
        logger.info("exchange updated successfully with ID: {}", exchange.getId());
	}

	@Override
    public Object getStockForExchange(Integer sid) {
        String stockServiceUrl = "http://localhost:8081/stock/getById/" + sid;
         logger.info("Fetching stock for exchange from URL: {}", stockServiceUrl);
          return restTemplate.getForObject(stockServiceUrl, Object.class);
    }
 
    @Override
    public Object getUserForExchange(Integer uid) {
        String userServiceUrl = "http://localhost:8080/user/get/" + uid;
        logger.info("Fetching user for exchange from URL: {}", userServiceUrl);
         return restTemplate.getForObject(userServiceUrl, Object.class);
    }
}
