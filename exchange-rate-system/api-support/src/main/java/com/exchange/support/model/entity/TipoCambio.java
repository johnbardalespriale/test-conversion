package com.exchange.support.model.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table("t_tipo_cambio")
public class TipoCambio {
    private Integer idMonedaOrigen;
    private Integer idMonedaDestino;
    private BigDecimal tasaCambio;
    private String userModi;
    private LocalDate fechaProceso;
}