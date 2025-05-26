package com.somllar.fichar.persistance.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre",nullable = false)
    private String firstName;

    @Column(name = "apellidos",nullable = false)
    private String lastName;

    @Column(name = "usuario")
    private String username;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "contraseña",nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "roles_de_usuarios", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<RolesEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<FichajeEntity> fichajes;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ManagerCasaEntity> managerCasas = new ArrayList<>();


    //Mierdas de la cuenta

    @Column(name = "esta_activada")
    private boolean isEnabled;

    @Column(name = "cuenta_no_vencida")
    private boolean accountNoExpired;

    @Column(name = "cuenta_no_bloqueada")
    private boolean accountNoLocked;

    @Column(name = "credenciales_no_vencidas")
    private boolean credentialNoExpired;

    public static String generarUsername(UsuarioEntity usuario) {
        String nombre = usuario.getFirstName();
        String apellido = usuario.getLastName();
        String parteNombre = nombre.length() >= 3 ? nombre.substring(0, 3) : nombre;
        String parteApellido = apellido.length() >= 3 ? apellido.substring(0, 3) : apellido;
        return (parteNombre + parteApellido).toLowerCase();
    }
}