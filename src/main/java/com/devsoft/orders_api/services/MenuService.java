package com.devsoft.orders_api.services;

import com.devsoft.orders_api.dto.MenuDTO;
import com.devsoft.orders_api.entities.Menu;
import com.devsoft.orders_api.interfaces.IMenuService;
import com.devsoft.orders_api.repository.MenuRepository;
import com.devsoft.orders_api.utils.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MenuService implements IMenuService {
    @Autowired
    private MenuRepository menuRepository;

    //obtenemos el valor
    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public List<MenuDTO> findAll() {
        return menuRepository.findAll()
                .stream().map(MenuMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDTO finById(Long id) {
        return menuRepository.findById(id)
                .map(MenuMapper::toDTO).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDTO finByNombre(String nombre) {
        return menuRepository.findByNombre(nombre)
                .map(MenuMapper::toDTO).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDTO save(MenuDTO dto, MultipartFile imageFile) throws IOException {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
