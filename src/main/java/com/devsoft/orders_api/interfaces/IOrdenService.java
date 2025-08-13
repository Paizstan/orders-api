package com.devsoft.orders_api.interfaces;

import com.devsoft.orders_api.dto.OrdenDTO;
import com.devsoft.orders_api.entities.Orden;
import com.devsoft.orders_api.utils.EstadoOrden;

import java.util.List;

public interface IOrdenService {
    List<OrdenDTO> finAll();
    List<   OrdenDTO> findByEstado(EstadoOrden estado);
    OrdenDTO findById(Long id);
    OrdenDTO registerOrUpdate(OrdenDTO ordenDTO);
    void anular(Long id);
    OrdenDTO changeState(OrdenDTO dto);

}
