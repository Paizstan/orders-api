package com.devsoft.orders_api.controllers;

import com.devsoft.orders_api.dto.ClienteDTO;
import com.devsoft.orders_api.entities.Cliente;
import com.devsoft.orders_api.repository.ClienteRepository;
import com.devsoft.orders_api.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public ResponseEntity<?> getAll(){
        List<ClienteDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);

    }

}
