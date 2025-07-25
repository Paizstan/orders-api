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

    @PostMapping("/categorias")
    public ResponseEntity<?> save(@RequestBody CategoriaDTO dto){
        CategoriaDTO catPersisted = new CategoriaDTO();
        Map<String, Object> response = new HashMap<>();
        try {

            /*CategoriaDTO catExiste = categotiaService.findByNombre(dto.getNombre());
            if(catExiste != null && dto.getId() == null){
                response.put("message", "Ya existe una categoria con este nombre, digite otro");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CONFLICT);
            }*/
            catPersisted = categotiaService.save(dto);
            response.put("message", "Categoria registrada correctamente");
            response.put("categoria", catPersisted);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
        }catch(DataAccessException e){
            response.put("message", "Error al insertar el registro, intente de nuevo");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
