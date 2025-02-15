package com.exchange.support.service;

import com.exchange.support.model.dto.TipoCambioRequest;
import com.exchange.support.model.dto.TipoCambioUpdateRequest;
import com.exchange.support.model.entity.TipoCambio;
import com.exchange.support.model.entity.TipoCambioId;
import com.exchange.support.repository.MonedaRepository;
import com.exchange.support.repository.TipoCambioRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TipoCambioService {
    private final TipoCambioRepository tipoCambioRepository;
    private final MonedaRepository monedaRepository;

    public Mono<TipoCambioRequest> getTipoCambio(String origen, String destino) {
        return monedaRepository.findByMoneda(origen)
                        .flatMap(monedaOrigen -> monedaRepository.findByMoneda(destino)
                                .flatMap(monedaDestino -> tipoCambioRepository.findByOrigenAndDestino(monedaOrigen.getIdMoneda(), monedaDestino.getIdMoneda())
                                        .map(tipoCambioEntity -> {
                                            TipoCambioRequest tipoCambioRequest = new TipoCambioRequest();
                                            tipoCambioRequest.setOrigen(origen);
                                            tipoCambioRequest.setDestino(destino);
                                            tipoCambioRequest.setTasaCambio(tipoCambioEntity.getTasaCambio());
                                            tipoCambioRequest.setUserModi(tipoCambioEntity.getUserModi());
                                            return tipoCambioRequest;
                                        })));
    }

    public Mono<TipoCambioRequest> saveTipoCambio(TipoCambioRequest tipoCambio) {
        return monedaRepository.findByMoneda(tipoCambio.getOrigen())
                .flatMap(monedaOrigen -> monedaRepository.findByMoneda(tipoCambio.getDestino())
                        .flatMap(monedaDestino -> {
                            TipoCambio tipoCambioEntity = new TipoCambio();
                            tipoCambioEntity.setIdMonedaOrigen(monedaOrigen.getIdMoneda());
                            tipoCambioEntity.setIdMonedaDestino(monedaDestino.getIdMoneda());
                            tipoCambioEntity.setFechaProceso(LocalDate.now());
                            tipoCambioEntity.setTasaCambio(tipoCambio.getTasaCambio());
                            tipoCambioEntity.setUserModi(tipoCambio.getUserModi());

                            return tipoCambioRepository.save(tipoCambioEntity);
                        })).map(tipoCambioEntitySaved -> tipoCambio);
    }

    public Mono<TipoCambioUpdateRequest> updateTipoCambio(String origen, String destino, TipoCambioUpdateRequest tipoCambio) {
        return monedaRepository.findByMoneda(origen)
                .flatMap(monedaOrigen -> monedaRepository.findByMoneda(destino)
                        .flatMap(monedaDestino -> tipoCambioRepository.findByOrigenAndDestino(monedaOrigen.getIdMoneda(), monedaDestino.getIdMoneda())
                                .flatMap(existingTipoCambio -> tipoCambioRepository.updateTasaCambioByOrigenAndDestino(tipoCambio.getTasaCambio(), tipoCambio.getUserModi(),
                            LocalDate.now(), existingTipoCambio.getIdMonedaOrigen(), existingTipoCambio.getIdMonedaDestino()))
                        )).thenReturn(tipoCambio);
    }

}