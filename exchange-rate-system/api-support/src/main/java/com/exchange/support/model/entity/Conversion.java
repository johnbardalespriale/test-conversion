package com.exchange.support.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table("t_conversion")
public class Conversion {
    @Id
    private Integer id;
    private String username;
    private Integer idMonedaOrigen;
    private Integer idMonedaDestino;
    private BigDecimal montoInicial;
    private BigDecimal montoFinal;
    private BigDecimal tasaCambio;
    private LocalDate fechaProceso;
}