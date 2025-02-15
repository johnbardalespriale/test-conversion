package com.exchange.experience.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ConversionRequest {
    private String origen;
    private String destino;
    private BigDecimal monto;
}