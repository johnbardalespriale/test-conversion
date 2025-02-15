package com.exchange.support.controller;

import com.exchange.support.model.dto.TipoCambioRequest;
import com.exchange.support.model.dto.TipoCambioUpdateRequest;
import com.exchange.support.service.TipoCambioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tipo-cambio")
@RequiredArgsConstructor
public class TipoCambioController {
    private final TipoCambioService tipoCambioService;

    @GetMapping
    public Mono<TipoCambioRequest> getTipoCambio(
            @RequestParam String origen,
            @RequestParam String destino
    ) {
        return tipoCambioService.getTipoCambio(origen, destino);
    }

    @PostMapping
    public Mono<TipoCambioRequest> createTipoCambio(@RequestBody TipoCambioRequest tipoCambio) {
        return tipoCambioService.saveTipoCambio(tipoCambio);
    }

    @PatchMapping("/origen/{origen}/destino/{destino}")
    public Mono<TipoCambioUpdateRequest> updateTipoCambio(
            @PathVariable String origen,
            @PathVariable String destino,
            @RequestBody TipoCambioUpdateRequest tipoCambio
    ) {
        return tipoCambioService.updateTipoCambio(origen, destino, tipoCambio);
    }
}