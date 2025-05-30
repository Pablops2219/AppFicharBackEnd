package com.somllar.fichar.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios_sin_contra")
public class UsuarioSinContraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre",nullable = false)
    private String nombre;

    @Column(name = "apellidos",nullable = false)
    private String apellidos;

    @Column(name = "email",nullable = false)
    private String email;
}
