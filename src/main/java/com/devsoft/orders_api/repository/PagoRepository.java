package com.devsoft.orders_api.repository;

import com.devsoft.orders_api.dto.IngresoDTO;
import com.devsoft.orders_api.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    //metodo para obtener la data del reporte a generar
    @Query("SELECT new com.devsoft.orders_api.dto.IngresoDTO(" +
            "o.id, o.correlativo, p.fechaPago, o.total, c.nombre, m.numero, u.nombre, SUM(p.monto)) " +
            "FROM Pago p " +
            "JOIN p.orden o " +
            "JOIN o.cliente c " +
            "JOIN o.mesa m " +
            "JOIN o.usuario u " +
            "WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY o.id, o.correlativo, p.fechaPago, o.total, c.nombre, m.numero, u.nombre " +
            "ORDER BY p.fechaPago ASC")
    List<IngresoDTO> obtenerIngresosRangoFechas(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
}