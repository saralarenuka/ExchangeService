package com.api.service;

import java.util.List;

import com.api.model.Exchange;

public interface ExchangeService {

	public Exchange saveExchangeDetails(Exchange exchange) ;
	public Exchange getOneExchangeDetails(Integer id);
	public List<Exchange> getAllExchangeDetails();
	public void deleteOneExchange(Integer id);
	public void deleteAllExchangeDetails();
	public void updateExchangeDetails(Exchange stock);
	
	Object getStockForExchange(Integer sid);
    Object getUserForExchange(Integer uid);
}
