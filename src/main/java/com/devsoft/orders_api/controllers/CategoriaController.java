package com.devsoft.orders_api.controllers;

import com.devsoft.orders_api.dto.CategoriaDTO;
import com.devsoft.orders_api.interfaces.ICategotiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CategoriaController {
    @Autowired
    private ICategotiaService categotiaService;

    @GetMapping("/categorias")
    public ResponseEntity <?> getAll(){
        List<CategoriaDTO> categoriaDTOList = categotiaService.findAll();
        return ResponseEntity.ok(categoriaDTOList);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        CategoriaDTO categoriaDTO = null;
        Map<String, Object> response = new HashMap<>();
        try{
            categoriaDTO = categotiaService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(categoriaDTO == null){
            response.put("message", "La categoria con ID: "
                    .concat(id.toString().concat( "No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CategoriaDTO>(categoriaDTO, HttpStatus.OK);
    }

    @PostMapping("/categorias")
    public ResponseEntity<?> save(@RequestBody CategoriaDTO dto){
        CategoriaDTO catPersisted = new CategoriaDTO();
        Map<String, Object> response = new HashMap<>();
        try {

            CategoriaDTO catExiste = categotiaService.findByNombre(dto.getNombre());
            if(catExiste != null && dto.getId() == null){
                response.put("message", "Ya existe una categoria con este nombre, digite otro");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CONFLICT);
            }
            catPersisted = categotiaService.save(dto);
            response.put("message", "Categoria registrada correctamente...!");
            response.put("categoria", catPersisted);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
        }catch(DataAccessException e){
            response.put("message", "Error al insertar el registro, intente de nuevo");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //endpoint para actualizar una Categoria
    @PutMapping("/categorias/{id}")
    public ResponseEntity<?> update(@RequestBody CategoriaDTO dto,
                                    @PathVariable Long id){
        CategoriaDTO catActual = categotiaService.findById(id);
          CategoriaDTO catUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if(catActual == null){
            response.put("message", "No se puede editar la categoria con IDA : "
                    .concat(id.toString().concat( "No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            CategoriaDTO catExiste = categotiaService.findByNombre(dto.getNombre());
            if(catExiste != null && !Objects.requireNonNull(catExiste).getId().equals(id)){
                response.put("message", "Ya existe una categoria cpn este nombreen la base de datos, digite otro ");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
            }
            catActual.setNombre(dto.getNombre());
            catUpdated = categotiaService.save(catActual);

        }catch (DataAccessException e){
            response.put("message", "Error al actualizar el registro, intente de nuevo");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Categoria actualizada correctamente...!");
        response.put("categoria", catUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }

    //endpoint para eliminar una Categoria
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        CategoriaDTO catActual = categotiaService.findById(id);
        if(catActual == null){
            response.put("message", "No se puede eliminar la categoria ID: "
                    .concat(id.toString().concat( "no existe en la base datos")));
            return  new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            categotiaService.delete(id);
        }catch (DataAccessException e){
            response.put("message", "No se puede eliminar la categoria, ya tiene menus asociados ");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Categoria eliminada...");
        return  new ResponseEntity<Map<String, Object>>(response, HttpStatus. OK);
    }
}
