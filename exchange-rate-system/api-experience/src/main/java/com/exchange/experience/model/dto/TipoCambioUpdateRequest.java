package com.exchange.experience.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TipoCambioUpdateRequest {
    private BigDecimal tasaCambio;
}