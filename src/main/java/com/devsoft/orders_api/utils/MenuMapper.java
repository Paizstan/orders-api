package com.devsoft.orders_api.utils;

import com.devsoft.orders_api.dto.CategoriaDTO;
import com.devsoft.orders_api.dto.MenuDTO;
import com.devsoft.orders_api.entities.Menu;

public class MenuMapper {
    
    //metodo para convertir una entidd Menu a MenuDTO
    public static MenuDTO toDTO(Menu menu){
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menu.getId());
        menuDTO.setNombre(menu.getNombre());
        menuDTO.setDescripcion(menu.getDescripcion());
        menuDTO.setTipo(menu.getTipo());
        menuDTO.setPrecioUnitario(menu.getPrecioUnitario());
        menuDTO.setUrlImagen(menu.getUrlImagen());
        menuDTO.setDisponible(menu.isDisponible());
        menuDTO.setCategoriaDTO(new CategoriaDTO(menu.getCategoria().getId(),
                menu.getCategoria().getNombre()));
        return menuDTO;
    }
}
