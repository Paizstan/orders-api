package com.devsoft.orders_api.services;

import com.devsoft.orders_api.dto.MesaDTO;
import com.devsoft.orders_api.entities.Mesa;
import com.devsoft.orders_api.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    public List<Mesa> findAll(){
        return mesaRepository.findAll();
    }
}
