package com.devsoft.orders_api.repository;

import com.devsoft.orders_api.entities.Orden;
import com.devsoft.orders_api.utils.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    //metodo para obtener listado de orden por estado
    List<Orden> findByEstado(EstadoOrden estado);

}
