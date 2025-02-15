package com.exchange.experience.controller;

import com.exchange.experience.model.dto.ConversionRequest;
import com.exchange.experience.model.dto.TipoCambioRequest;
import com.exchange.experience.model.dto.TipoCambioUpdateRequest;
import com.exchange.experience.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

@RestController
@RequestMapping("/tipo-cambio")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/user/{userId}")
    public Mono<Object> getTipoCambio(
            @PathVariable Long userId,
            @RequestParam String origen,
            @RequestParam String destino) {
        return exchangeRateService.getTipoCambio(userId, origen, destino);
    }

    @PostMapping("/user/{userId}")
    public Mono<Object> createTipoCambio(
            @PathVariable Long userId,
            @RequestBody TipoCambioRequest request) {
        return exchangeRateService.createTipoCambio(userId, request);
    }

    @PatchMapping("/user/{userId}/origen/{origen}/destino/{destino}")
    public Mono<Object> updateTipoCambio(
            @PathVariable Long userId,
            @PathVariable String origen,
            @PathVariable String destino,
            @RequestBody TipoCambioUpdateRequest request) {
        return exchangeRateService.updateTipoCambio(userId, origen, destino, request);
    }

    @PostMapping("/conversion/user/{userId}")
    public Mono<Object> realizarConversion(
            @PathVariable Long userId,
            @RequestBody ConversionRequest request) {
        return exchangeRateService.realizarConversion(userId, request);
    }
}