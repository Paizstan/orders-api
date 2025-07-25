package com.devsoft.orders_api.interfaces;

import com.devsoft.orders_api.dto.CategoriaDTO;

import java.util.List;

public interface ICategotiaService {
    List<CategoriaDTO> findAll();
    CategoriaDTO findBiId(Long id);
    CategoriaDTO save(CategoriaDTO categoriaDTO);
    CategoriaDTO findByNombre(String nombre);
    void delete(Long id);
}
