package com.devsoft.orders_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class IngresoDTO {
    private Long ordenId;
    private String correlativo;
    private LocalDate fechaPago;
    private BigDecimal totalOrden;
    private String clienteNombre;
    private Integer mesaNumero;
    private String meseroNombre;
    private BigDecimal sumaPagos;
}
