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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
        Menu menu;
        if(dto.getId() == null){
            //se va agregar un registro
            menu = new Menu();
            //verificamos si viene una imagen
            if(imageFile !=null && imageFile.isEmpty()){
                //le anteponemos un rumero aleatorio al archivo original de la imagen
                //para que no se repita nombre de archivos de imagen
                String nombreArchivo = UUID.randomUUID().toString()+"_"+imageFile.getOriginalFilename();
                //definimos la ruta donde se cargara el archivo fisico
                Path rutaArchivo = Paths.get(uploadDir, nombreArchivo);
                //copiamos el archivo a la carpeta del servidor
                Files.copy(imageFile.getInputStream(), rutaArchivo);
                menu.setUrlImagen(nombreArchivo);
            }

        }else{
            //se va actualizar un registro
            Menu menuActual =
            menu = menuRepository.findById(dto.getId()).orElse(null);


        }
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
