package com.exchange.support.controller;

import com.exchange.support.service.ConversionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

@RestController
@RequestMapping("/conversion")
@RequiredArgsConstructor
public class ConversionController {
    private final ConversionService conversionService;

    @PostMapping
    public Mono<ConversionResponse> convertir(@RequestBody ConversionRequest request) {
        return conversionService.convertir(
                request.getOrigen(),
                request.getDestino(),
                request.getMonto(),
                request.getUsername()
        ).map(montoConversion -> {
            ConversionResponse response = new ConversionResponse();
            response.setMontoConversion(montoConversion);
            return response;
        });
    }
}

@Data
class ConversionRequest {
    private String origen;
    private String destino;
    private BigDecimal monto;
    private String username;
}

@Data
class ConversionResponse {
    private BigDecimal montoConversion;
}