package com.devsoft.orders_api.utils;

import com.devsoft.orders_api.dto.MenuDTO;
import com.devsoft.orders_api.entities.Menu;

public class MenuMapper {
    public static MenuDTO toDTO(Menu menu){
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menu.getId());
        menuDTO.setNombre(menu.getNombre());
        menuDTO.setDescripcion(menu.getDescripcion());
        menuDTO.setTipo(menu.getTipo());
        return menuDTO;

    }

}
