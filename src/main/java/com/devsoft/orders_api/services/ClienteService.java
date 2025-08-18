package com.devsoft.orders_api.services;

import com.devsoft.orders_api.dto.ClienteDTO;
import com.devsoft.orders_api.entities.Cliente;
import com.devsoft.orders_api.interfaces.IClienteService;
import com.devsoft.orders_api.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //
    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findById(Long id) {
        return clienteRepository.findById(id).map(this::toDTO);
    }

    //
    @Override
    @Transactional
    public ClienteDTO create(ClienteDTO dto) {
        Cliente entity = toEntity(dto);
        entity.setId(null); // asegurar inserción
        Cliente saved = clienteRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente entity = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con id " + id));

        // Actualiza únicamente campos permitidos
        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());

        // Si tu entidad usa 'correo' en lugar de 'email', este setter debería existir como setCorreo(...)
        try {
            entity.getClass().getMethod("setEmail", String.class).invoke(entity, dto.getEmail());
        } catch (ReflectiveOperationException e) {
            // fallback a 'setCorreo' si no existe 'setEmail'
            try {
                entity.getClass().getMethod("setCorreo", String.class).invoke(entity, dto.getEmail());
            } catch (ReflectiveOperationException ignored) {}
        }

        // Manejo de tipoCliente (si en la entidad es Enum)
        setTipoClienteOnEntity(entity, dto.getTipoCliente());

        Cliente saved = clienteRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente no encontrado con id " + id);
        }
        clienteRepository.deleteById(id);
    }

    // -------- MAPEOS --------
    private ClienteDTO toDTO(Cliente c) {
        if (c == null) return null;

        String email = null;
        try {
            var m = c.getClass().getMethod("getEmail");
            email = (String) m.invoke(c);
        } catch (ReflectiveOperationException e) {
            try {
                var m = c.getClass().getMethod("getCorreo");
                email = (String) m.invoke(c);
            } catch (ReflectiveOperationException ignored) {}
        }

        String tipo = getTipoClienteFromEntity(c);

        return ClienteDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .direccion(c.getDireccion())
                .telefono(c.getTelefono())
                .email(email)
                .tipoCliente(tipo)
                .build();
    }

    private Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        Cliente c = new Cliente();
        c.setId(dto.getId());
        c.setNombre(dto.getNombre());
        c.setDireccion(dto.getDireccion());
        c.setTelefono(dto.getTelefono());

        // setEmail o setCorreo
        try {
            c.getClass().getMethod("setEmail", String.class).invoke(c, dto.getEmail());
        } catch (ReflectiveOperationException e) {
            try {
                c.getClass().getMethod("setCorreo", String.class).invoke(c, dto.getEmail());
            } catch (ReflectiveOperationException ignored) {}
        }

        setTipoClienteOnEntity(c, dto.getTipoCliente());
        return c;
    }

    // ----- helpers para 'tipoCliente' -----
    /**
     * Lee tipoCliente desde la entidad. Si es Enum, devuelve name(); si es String, lo devuelve tal cual.
     */
    private String getTipoClienteFromEntity(Cliente c) {
        try {
            // Si la entidad tiene getTipoCliente() que devuelve Enum
            Object enumOrString = c.getClass().getMethod("getTipoCliente").invoke(c);
            if (enumOrString == null) return null;
            if (enumOrString instanceof Enum<?> en) return en.name();
            return String.valueOf(enumOrString);
        } catch (ReflectiveOperationException e) {
            // Intentar getTipo() como alternativa
            try {
                Object enumOrString = c.getClass().getMethod("getTipo").invoke(c);
                if (enumOrString == null) return null;
                if (enumOrString instanceof Enum<?> en) return en.name();
                return String.valueOf(enumOrString);
            } catch (ReflectiveOperationException ignored) {
                return null;
            }
        }
    }

    /**
     * Setea tipoCliente en la entidad. Si es Enum, hace valueOf con el String del DTO.
     */
    private void setTipoClienteOnEntity(Cliente c, String tipoClienteStr) {
        if (tipoClienteStr == null || tipoClienteStr.isBlank()) return;

        // Primero intentar setTipoCliente(Enum) o setTipo(Enum)
        try {
            var getter = c.getClass().getMethod("getTipoCliente");
            Class<?> returnType = getter.getReturnType();
            if (returnType.isEnum()) {
                @SuppressWarnings({"rawtypes", "unchecked"})
                Enum enumVal = Enum.valueOf((Class<Enum>) returnType, tipoClienteStr.toUpperCase());
                c.getClass().getMethod("setTipoCliente", returnType).invoke(c, enumVal);
                return;
            }
        } catch (ReflectiveOperationException ignored) {}

        try {
            var getter = c.getClass().getMethod("getTipo");
            Class<?> returnType = getter.getReturnType();
            if (returnType.isEnum()) {
                @SuppressWarnings({"rawtypes", "unchecked"})
                Enum enumVal = Enum.valueOf((Class<Enum>) returnType, tipoClienteStr.toUpperCase());
                c.getClass().getMethod("setTipo", returnType).invoke(c, enumVal);
                return;
            }
        } catch (ReflectiveOperationException ignored) {}

        // Si no es Enum, intentar como String
        try {
            c.getClass().getMethod("setTipoCliente", String.class).invoke(c, tipoClienteStr);
        } catch (ReflectiveOperationException e) {
            try {
                c.getClass().getMethod("setTipo", String.class).invoke(c, tipoClienteStr);
            } catch (ReflectiveOperationException ignored) {}
        }
    }
}
