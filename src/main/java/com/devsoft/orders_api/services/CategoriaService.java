package com.devsoft.orders_api.services;

import com.devsoft.orders_api.dto.CategoriaDTO;
import com.devsoft.orders_api.entities.Categoria;
import com.devsoft.orders_api.interfaces.ICategotiaService;
import com.devsoft.orders_api.repository.CategoriaRepository;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CategoriaService implements ICategotiaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<Categoria> getAll(){
        return categoriaRepository.findAll();
    }

    @Override
    public CategoriaDTO findBiId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encuentra una categoria con el Id: " + id));
        return convertToDTO(categoria);
    }

    @Override
    public CategoriaDTO save(CategoriaDTO dto) {
        Categoria catNueva = new Categoria();
        if(dto.getId() == null){
            catNueva.setNombre(dto.getNombre());
        }else{
            //Actualizacion
         catNueva.setId(dto.getId());
         catNueva.setNombre(dto.getNombre());
        }
                categoriaRepository.save(catNueva);
        return convertToDTO(categoriaRepository.save(catNueva));
    }

    @Override
    public CategoriaDTO findByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }

    @Override
    public void delete(Long id) {
        categoriaRepository.deleteById(id);

    }
    //metodo para convertir la entidad Categoria a DTO
    private CategoriaDTO convertToDTO(Categoria categoria){
        return new CategoriaDTO(categoria.getId(), categoria.getNombre());
    }
}
