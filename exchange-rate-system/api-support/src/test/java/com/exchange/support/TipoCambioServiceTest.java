package com.exchange.support;

import com.exchange.support.model.dto.TipoCambioRequest;
import com.exchange.support.model.dto.TipoCambioUpdateRequest;
import com.exchange.support.model.entity.Moneda;
import com.exchange.support.model.entity.TipoCambio;
import com.exchange.support.repository.MonedaRepository;
import com.exchange.support.repository.TipoCambioRepository;
import com.exchange.support.service.TipoCambioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoCambioServiceTest {

    @Mock
    private TipoCambioRepository tipoCambioRepository;

    @Mock
    private MonedaRepository monedaRepository;

    @InjectMocks
    private TipoCambioService tipoCambioService;

    private TipoCambio tipoCambio;
    private TipoCambioRequest tipoCambioRequest;
    private TipoCambioUpdateRequest tipoCambioUpdateRequest;
    private Moneda monedaOrigen;
    private Moneda monedaDestino;

    @BeforeEach
    void setUp() {
        monedaOrigen = new Moneda();
        monedaOrigen.setIdMoneda(1);
        monedaOrigen.setMoneda("USD");

        monedaDestino = new Moneda();
        monedaDestino.setIdMoneda(2);
        monedaDestino.setMoneda("PEN");

        tipoCambio = new TipoCambio();
        tipoCambio.setIdMonedaOrigen(1);
        tipoCambio.setIdMonedaDestino(2);
        tipoCambio.setTasaCambio(BigDecimal.valueOf(3.5));
        tipoCambio.setUserModi("admin");
        tipoCambio.setFechaProceso(LocalDate.now());

        tipoCambioRequest = new TipoCambioRequest();
        tipoCambioRequest.setOrigen("USD");
        tipoCambioRequest.setDestino("PEN");
        tipoCambioRequest.setTasaCambio(BigDecimal.valueOf(3.5));
        tipoCambioRequest.setUserModi("admin");

        tipoCambioUpdateRequest = new TipoCambioUpdateRequest();
        tipoCambioUpdateRequest.setTasaCambio(BigDecimal.valueOf(3.7));
        tipoCambioUpdateRequest.setUserModi("admin");
    }

    @Test
    void testGetTipoCambio() {
        when(monedaRepository.findByMoneda("USD")).thenReturn(Mono.just(monedaOrigen));
        when(monedaRepository.findByMoneda("PEN")).thenReturn(Mono.just(monedaDestino));
        when(tipoCambioRepository.findByOrigenAndDestino(1, 2)).thenReturn(Mono.just(tipoCambio));

        StepVerifier.create(tipoCambioService.getTipoCambio("USD", "PEN"))
                .expectNextMatches(response -> response.getTasaCambio().compareTo(BigDecimal.valueOf(3.5)) == 0)
                .verifyComplete();

        verify(monedaRepository).findByMoneda("USD");
        verify(monedaRepository).findByMoneda("PEN");
        verify(tipoCambioRepository).findByOrigenAndDestino(1, 2);
    }

    @Test
    void testSaveTipoCambio() {
        when(monedaRepository.findByMoneda("USD")).thenReturn(Mono.just(monedaOrigen));
        when(monedaRepository.findByMoneda("PEN")).thenReturn(Mono.just(monedaDestino));
        when(tipoCambioRepository.save(any(TipoCambio.class))).thenReturn(Mono.just(tipoCambio));

        StepVerifier.create(tipoCambioService.saveTipoCambio(tipoCambioRequest))
                .expectNext(tipoCambioRequest)
                .verifyComplete();

        verify(monedaRepository).findByMoneda("USD");
        verify(monedaRepository).findByMoneda("PEN");
        verify(tipoCambioRepository).save(any(TipoCambio.class));
    }

    @Test
    void testUpdateTipoCambio() {
        when(monedaRepository.findByMoneda("USD")).thenReturn(Mono.just(monedaOrigen));
        when(monedaRepository.findByMoneda("PEN")).thenReturn(Mono.just(monedaDestino));
        when(tipoCambioRepository.findByOrigenAndDestino(1, 2)).thenReturn(Mono.just(tipoCambio));
        when(tipoCambioRepository.updateTasaCambioByOrigenAndDestino(
                eq(BigDecimal.valueOf(3.7)), eq("admin"), any(LocalDate.class), eq(1), eq(2)))
                .thenReturn(Mono.empty());

        StepVerifier.create(tipoCambioService.updateTipoCambio("USD", "PEN", tipoCambioUpdateRequest))
                .expectNext(tipoCambioUpdateRequest)
                .verifyComplete();

        verify(monedaRepository).findByMoneda("USD");
        verify(monedaRepository).findByMoneda("PEN");
        verify(tipoCambioRepository).findByOrigenAndDestino(1, 2);
        verify(tipoCambioRepository).updateTasaCambioByOrigenAndDestino(
                eq(BigDecimal.valueOf(3.7)), eq("admin"), any(LocalDate.class), eq(1), eq(2));
    }
}
