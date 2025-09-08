package com.devsoft.orders_api.controllers;

import com.devsoft.orders_api.entities.Pago;
import com.devsoft.orders_api.services.PagoService;
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
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping("/pagos")
    public ResponseEntity<?> getAll(){
        List<Pago> pagos = pagoService.getAll();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/pagos/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Pago pago = null;
        Map<String, Object> response = new HashMap<>();
        try{
            pago = pagoService.findById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(pago == null){
            response.put("message", "El pago con ID: ".concat(id.toString())
                    .concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pago>(pago, HttpStatus.OK);
    }

    @PostMapping("/pagos")
    public ResponseEntity<?> save(@RequestBody Pago pago){
        Pago pagoPersisted = null;
        Map<String, Object> response = new HashMap<>();
        try{

            pagoPersisted = pagoService.registrarPago(pago);
            response.put("message","Pago registrado correctamente...!");
            response.put("pago", pagoPersisted);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
        }catch(DataAccessException e){
            response.put("message", "Error al insertar el registro, intente de nuevo");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}