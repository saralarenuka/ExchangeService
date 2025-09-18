package com.api.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.api.exception.ExchangeNotAvailableException;
import com.api.model.Exchange;
import com.api.repo.ExchangeRepository;
import com.api.service.ExchangeServiceImpl;

public class ExchangeServiceImplTest {

	@InjectMocks
	private ExchangeServiceImpl exchangeService;

	@Mock
	private ExchangeRepository repo;

	@Mock
	private RestTemplate restTemplate;

	private Exchange exchange;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		exchange = new Exchange();
		exchange.setId(1);
		exchange.setName("NYSE");
		exchange.setCountry("USA");
		exchange.setDescription("New York Stock Exchange");
	}

	@Test
	void testSaveExchangeDetails() {
		when(repo.save(exchange)).thenReturn(exchange);

		Exchange saved = exchangeService.saveExchangeDetails(exchange);

		assertNotNull(saved);
		assertEquals(1, saved.getId());
		verify(repo, times(1)).save(exchange);
	}

	@Test
	void testGetOneExchangeDetails_Found() {
		when(repo.findById(1)).thenReturn(Optional.of(exchange));

		Exchange result = exchangeService.getOneExchangeDetails(1);

		assertNotNull(result);
		assertEquals("NYSE", result.getName());
		verify(repo, times(1)).findById(1);
	}

	@Test
	void testGetOneExchangeDetails_NotFound() {
		when(repo.findById(99)).thenReturn(Optional.empty());

		assertThrows(ExchangeNotAvailableException.class,
				() -> exchangeService.getOneExchangeDetails(99));
		verify(repo, times(1)).findById(99);
	}

	@Test
	void testGetAllExchangeDetails() {
		List<Exchange> list = Arrays.asList(exchange, new Exchange());
		when(repo.findAll()).thenReturn(list);

		List<Exchange> result = exchangeService.getAllExchangeDetails();

		assertEquals(2, result.size());
		verify(repo, times(1)).findAll();
	}

	@Test
	void testDeleteOneExchange() {
		doNothing().when(repo).deleteById(1);

		exchangeService.deleteOneExchange(1);

		verify(repo, times(1)).deleteById(1);
	}

	@Test
	void testDeleteAllExchangeDetails() {
		doNothing().when(repo).deleteAll();

		exchangeService.deleteAllExchangeDetails();

		verify(repo, times(1)).deleteAll();
	}

	@Test
	void testUpdateExchangeDetails_Found() {
		when(repo.findById(1)).thenReturn(Optional.of(exchange));
		when(repo.save(any(Exchange.class))).thenReturn(exchange);

		Exchange update = new Exchange();
		update.setId(1);
		update.setName("BSE");
		update.setCountry("India");
		update.setDescription("Bombay Stock Exchange");

		exchangeService.updateExchangeDetails(update);

		assertEquals("BSE", exchange.getName());
		assertEquals("India", exchange.getCountry());
		verify(repo, times(1)).save(exchange);
	}

	@Test
	void testUpdateExchangeDetails_NotFound() {
		when(repo.findById(2)).thenReturn(Optional.empty());

		Exchange update = new Exchange();
		update.setId(2);

		assertThrows(ExchangeNotAvailableException.class,
				() -> exchangeService.updateExchangeDetails(update));
		verify(repo, times(1)).findById(2);
	}

	@Test
	void testGetStockForExchange() {
		String url = "http://localhost:8081/stock/getById/1";
		Object mockResponse = new Object();

		when(restTemplate.getForObject(url, Object.class)).thenReturn(mockResponse);

		Object result = exchangeService.getStockForExchange(1);

		assertNotNull(result);
		verify(restTemplate, times(1)).getForObject(url, Object.class);
	}

	@Test
	void testGetUserForExchange() {
		String url = "http://localhost:8080/user/get/10";
		Object mockResponse = new Object();

		when(restTemplate.getForObject(url, Object.class)).thenReturn(mockResponse);

		Object result = exchangeService.getUserForExchange(10);

		assertNotNull(result);
		verify(restTemplate, times(1)).getForObject(url, Object.class);
	}
}
