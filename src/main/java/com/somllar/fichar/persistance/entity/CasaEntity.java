package com.somllar.fichar.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "casas")
public class CasaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "casa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ManagerCasaEntity> managerCasas;

    @OneToMany(mappedBy = "casa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<FichajeEntity> fichajes = new ArrayList<>();

}

