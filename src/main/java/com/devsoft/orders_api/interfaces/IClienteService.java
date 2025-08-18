package com.devsoft.orders_api.interfaces;

import com.devsoft.orders_api.dto.ClienteDTO;
import java.util.List;
import java.util.Optional;

public interface IClienteService {
    List<ClienteDTO> findAll();
    Optional<ClienteDTO> findById(Long id);
    ClienteDTO create(ClienteDTO dto);
    ClienteDTO update(Long id, ClienteDTO dto);
    void delete(Long id);
}
