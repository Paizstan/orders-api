package com.devsoft.orders_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menus", schema = "public", catalog = "orders")
public class Menu implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;
    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
    @Column(name ="tipo", nullable = false, length = 1)
    private String tipo;
    @Column(name = "precio_unitario", nullable = false, precision = 8, scale = 2)
    private BigDecimal precioUnitario;
    @Column(name = "url_imagen", nullable = true, length = 100)
    private String urlImagen;
    @Column(name = "activo", columnDefinition = "boolean default true")
    private boolean isDisponible;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id", nullable = false)
    private Categoria categoria;

}
