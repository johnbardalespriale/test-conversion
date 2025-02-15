package com.exchange.support.service;

import com.exchange.support.model.entity.Conversion;
import com.exchange.support.repository.ConversionRepository;
import com.exchange.support.repository.MonedaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ConversionService {
    private final ConversionRepository conversionRepository;
    private final TipoCambioService tipoCambioService;
    private final MonedaRepository monedaRepository;

    public Mono<BigDecimal> convertir(String origen, String destino, BigDecimal monto, String username) {
        return tipoCambioService.getTipoCambio(origen, destino)
                .flatMap(tipoCambio -> {
                    BigDecimal montoFinal = monto.multiply(tipoCambio.getTasaCambio());
                    return monedaRepository.findByMoneda(origen)
                            .flatMap(monedaOrigen -> monedaRepository.findByMoneda(destino)
                                    .flatMap(monedaDestino -> {
                                        Conversion conversion = new Conversion();
                                        conversion.setUsername(username);
                                        conversion.setIdMonedaOrigen(monedaOrigen.getIdMoneda());
                                        conversion.setIdMonedaDestino(monedaDestino.getIdMoneda());
                                        conversion.setMontoInicial(monto);
                                        conversion.setMontoFinal(montoFinal);
                                        conversion.setTasaCambio(tipoCambio.getTasaCambio());
                                        conversion.setFechaProceso(LocalDate.now());

                                        return conversionRepository.save(conversion)
                                                .thenReturn(montoFinal);
                                    }));
                });
    }
}