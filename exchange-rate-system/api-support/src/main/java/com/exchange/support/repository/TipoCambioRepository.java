package com.exchange.support.repository;

import com.exchange.support.model.entity.TipoCambio;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TipoCambioRepository extends ReactiveCrudRepository<TipoCambio, Integer> {
    @Query("SELECT * FROM t_tipo_cambio WHERE id_moneda_origen = :origen AND id_moneda_destino = :destino")
    Mono<TipoCambio> findByOrigenAndDestino(Integer origen, Integer destino);

    @Query("UPDATE t_tipo_cambio SET tasa_cambio = :tasaCambio, user_modi = :userModi, fecha_proceso = :fechaProceso WHERE id_moneda_origen = :origen AND id_moneda_destino = :destino")
    Mono<Void> updateTasaCambioByOrigenAndDestino(BigDecimal tasaCambio, String userModi, LocalDate fechaProceso, Integer origen, Integer destino);
}