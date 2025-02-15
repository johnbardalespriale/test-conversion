package com.exchange.support.repository;

import com.exchange.support.model.entity.Moneda;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MonedaRepository extends ReactiveCrudRepository<Moneda, Integer> {
    Mono<Moneda> findByMoneda(String moneda);
}