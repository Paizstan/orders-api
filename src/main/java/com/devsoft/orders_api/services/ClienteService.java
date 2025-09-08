package com.devsoft.orders_api.services;

import com.devsoft.orders_api.entities.Cliente;
import com.devsoft.orders_api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Cliente> findAll(){
        return  clienteRepository.findAll();
    }
}