package com.exchange.experience.service;

import com.exchange.experience.exception.CustomException;
import com.exchange.experience.model.dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final GoRestService goRestService;
    private final WebClient.Builder webClientBuilder;

    @Value("${support.api.url}")
    private String supportUrl;

    private WebClient supportWebClient() {
        return webClientBuilder.baseUrl(supportUrl).build();
    }

    public Mono<Object> getTipoCambio(Long userId, String origen, String destino) {
        return validateUser(userId)
                .flatMap(user -> supportWebClient()
                        .get()
                        .uri("/tipo-cambio?origen={origen}&destino={destino}",
                                origen, destino)
                        .retrieve()
                        .bodyToMono(Object.class)
                        .onErrorResume(error -> Mono.error(
                                new CustomException("Error al obtener tipo de cambio", "EXCHANGE_RATE_ERROR")
                        ))
                );
    }

    public Mono<Object> createTipoCambio(Long userId, TipoCambioRequest request) {
        return validateUser(userId)
                .flatMap(user -> {
                    SupportTipoCambioRequest supportRequest = new SupportTipoCambioRequest();
                    supportRequest.setOrigen(request.getOrigen());
                    supportRequest.setDestino(request.getDestino());
                    supportRequest.setTasaCambio(request.getTasaCambio());
                    supportRequest.setUserModi(user.getName());

                    return supportWebClient()
                            .post()
                            .uri("/tipo-cambio")
                            .bodyValue(supportRequest)
                            .retrieve()
                            .bodyToMono(Object.class)
                            .onErrorResume(error -> Mono.error(
                                    new CustomException("Error al crear tipo de cambio", "CREATE_EXCHANGE_ERROR")
                            ));
                });
    }

    public Mono<Object> updateTipoCambio(Long userId, String origen, String destino,
                                         TipoCambioUpdateRequest request) {
        return validateUser(userId)
                .flatMap(user -> {
                    SupportTipoCambioUpdateRequest supportRequest = new SupportTipoCambioUpdateRequest();
                    supportRequest.setTasaCambio(request.getTasaCambio());
                    supportRequest.setUserModi(user.getName());

                    return supportWebClient()
                            .patch()
                            .uri("/tipo-cambio/origen/{origen}/destino/{destino}",
                                    origen, destino)
                            .bodyValue(supportRequest)
                            .retrieve()
                            .bodyToMono(Object.class)
                            .onErrorResume(error -> Mono.error(
                                    new CustomException("Error al actualizar tipo de cambio", "UPDATE_EXCHANGE_ERROR")
                            ));
                });
    }

    public Mono<Object> realizarConversion(Long userId, ConversionRequest request) {
        return validateUser(userId)
                .flatMap(user -> {
                    SupportConversionRequest supportRequest = new SupportConversionRequest();
                    supportRequest.setOrigen(request.getOrigen());
                    supportRequest.setDestino(request.getDestino());
                    supportRequest.setMonto(request.getMonto());
                    supportRequest.setUsername(user.getName());

                    return supportWebClient()
                            .post()
                            .uri("/conversion")
                            .bodyValue(supportRequest)
                            .retrieve()
                            .bodyToMono(Object.class)
                            .onErrorResume(error -> Mono.error(
                                    new CustomException("Error al realizar la conversión", "CONVERSION_ERROR")
                            ));
                });
    }

    private Mono<GoRestUser> validateUser(Long userId) {
        return goRestService.getUser(userId)
                .filter(user -> "active".equals(user.getStatus()))
                .switchIfEmpty(Mono.error(
                        new CustomException("Usuario no válido o inactivo", "INVALID_USER")
                ));
    }
}

@Data
class SupportTipoCambioRequest {
    private String origen;
    private String destino;
    private BigDecimal tasaCambio;
    private String userModi;
}

@Data
class SupportTipoCambioUpdateRequest {
    private BigDecimal tasaCambio;
    private String userModi;
}

@Data
class SupportConversionRequest {
    private String origen;
    private String destino;
    private BigDecimal monto;
    private String username;
}