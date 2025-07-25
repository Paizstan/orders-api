package com.devsoft.orders_api.repository;

import com.devsoft.orders_api.dto.CategoriaDTO;
import com.devsoft.orders_api.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    //Optional<Categoria> findByNombreIgnoreCase(String nombre);
    //este metodo servira para no insertar registros duplicados

    CategoriaDTO findByNombre(String nombre);
}
