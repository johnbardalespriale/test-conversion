package com.exchange.support.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("t_moneda")
public class Moneda {
    @Id
    private Integer idMoneda;
    private String moneda;
}