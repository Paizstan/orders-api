package com.devsoft.orders_api.repository;


import com.devsoft.orders_api.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByNombre(String nombre);
}
