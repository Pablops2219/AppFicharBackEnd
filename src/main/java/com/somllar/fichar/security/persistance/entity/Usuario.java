package com.somllar.fichar.security.persistance.entity;


import com.somllar.fichar.model.Fichaje;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre",nullable = false)
    private String firstName;

    @Column(name = "apellidos",nullable = false)
    private String lastName;

    private String username;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "contraseña",nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fichaje> fichajes;

    //Mierdas de la cuenta

    @Column(name = "esta_activada")
    private boolean isEnabled;

    @Column(name = "cuenta_no_vencida")
    private boolean accountNoExpired;

    @Column(name = "cuenta_no_bloqueada")
    private boolean accountNoLocked;

    @Column(name = "credenciales_no_vencidas")
    private boolean credentialNoExpired;
}