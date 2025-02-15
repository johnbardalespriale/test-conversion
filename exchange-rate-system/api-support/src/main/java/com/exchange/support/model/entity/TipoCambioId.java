package com.exchange.support.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TipoCambioId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idMonedaOrigen;
    private Integer idMonedaDestino;

}
