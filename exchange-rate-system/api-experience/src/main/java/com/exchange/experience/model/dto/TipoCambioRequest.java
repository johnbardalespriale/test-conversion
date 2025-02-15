package com.exchange.experience.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TipoCambioRequest {
    private String origen;
    private String destino;
    private BigDecimal tasaCambio;
}