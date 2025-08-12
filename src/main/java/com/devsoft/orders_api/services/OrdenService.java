package com.devsoft.orders_api.services;

import com.devsoft.orders_api.dto.DetalleOrdenDTO;
import com.devsoft.orders_api.dto.OrdenDTO;
import com.devsoft.orders_api.dto.UsuarioDTO;
import com.devsoft.orders_api.entities.*;
import com.devsoft.orders_api.interfaces.IOrdenService;
import com.devsoft.orders_api.repository.*;
import com.devsoft.orders_api.utils.EstadoOrden;
import com.devsoft.orders_api.utils.OrdenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenService implements IOrdenService {
    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    private MenuRepository menuRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrdenDTO> finAll() {
        return ordenRepository.findAll()
                .stream().map(OrdenMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenDTO> finByEstado(EstadoOrden estado) {
        return ordenRepository.findByEstado(estado)
                .stream().map(OrdenMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrdenDTO finById(Long id) {
        return ordenRepository.findById(id)
                .map(OrdenMapper::toDTO).orElse(null);
    }

    @Override
    @Transactional
    public OrdenDTO registerOrUpdate(OrdenDTO ordenDTO) {
        //validamos referencias objetos
        Optional<Cliente> clienteOpt =
        clienteRepository.findById(ordenDTO.getClienteDTO().getId());
        Optional<Mesa> mesaopt =
                mesaRepository.findById(ordenDTO.getMesaDTO().getId());
        Optional<Usuario> userOpt =
                usuarioRepository.findById(ordenDTO.getUsuarioDTO().getId());
        if(clienteOpt.isEmpty() || mesaopt.isEmpty() ||userOpt.isEmpty()
                || ordenDTO.getDetalle() == null  || ordenDTO.getDetalle().isEmpty()){
            return null;//gestionar en el controlador
        }
        Orden orden = null; //objeto a persistir
        if(ordenDTO.getId() == null){
            //logica para registrar una orden
            orden = new Orden();
            //seteando los atributos
            orden.setFecha(LocalDate.now());
            orden.setHora(LocalTime.now());
            orden.setEstado(EstadoOrden.CREADA);
            orden.setCorrelativo(generarCorrelativo());
            orden.setTotal(ordenDTO.getTotal());
            orden.setCliente(clienteOpt.get());
            orden.setMesa(mesaopt.get());
            orden.setUsuario(userOpt.get());
            orden.setDetalleOrden(new ArrayList<>());
        }else{
            //logica para actualizar datos de una orden

        }
        //agregando detalle de la orden
        for(DetalleOrdenDTO detalleDTO : ordenDTO.getDetalle()){
            Optional<Menu> menuopt = menuRepository.findById(detalleDTO.getMenuDTO().getId());
                    if(menuopt.isEmpty()) return null;
                    Menu menu = menuopt.get();
                    DetalleOrden detalleOrden = new DetalleOrden();
                    detalleOrden.setCantidad(detalleDTO.getCantidad());
                    detalleOrden.setPrecio(detalleDTO.getPrecio());
                    detalleOrden.setSubtotal(detalleDTO.getSubtotal());
                    detalleOrden.setMenu(menu);
                    detalleOrden.setOrden(orden);

        }
        return null;
    }

    @Override
    @Transactional
    public void anular(Long id) {

    }
    //metodo auxiliares del servicio
    private String generarCorrelativo() {
        return null;
    }


}
