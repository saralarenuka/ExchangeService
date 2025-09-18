package com.api.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.api.exception.ExchangeNotAvailableException;
import com.api.exception.GlobalExceptionHandler;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }
/*
    @Test
    void handleUserNotFound_ShouldReturn404() {
    	ExchangeNotAvailableException ex = new ExchangeNotAvailableException("Exchange not found with ID: 10");

        ResponseEntity<String> response = handler.ResourceNotFoundException(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Exchange not found with ID: 10", response.getBody());
    }

    @Test
    void handleUserNameNotFound_ShouldReturn404() {
    	ExchangeNotAvailableException ex = new ExchangeNotAvailableException("Exchange not found with ID: 1");

        ResponseEntity<String> response = handler.ResourceNotFoundException(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found with username: alice", response.getBody());
    }*/
}
