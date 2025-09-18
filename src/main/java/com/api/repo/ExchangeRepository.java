package com.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.model.Exchange;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {

}
