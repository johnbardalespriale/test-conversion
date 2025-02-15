package com.exchange.support.repository;

import com.exchange.support.model.entity.Conversion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ConversionRepository extends ReactiveCrudRepository<Conversion, Integer> {
}