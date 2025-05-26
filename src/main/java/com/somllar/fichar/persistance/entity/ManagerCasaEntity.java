package com.somllar.fichar.persistance.entity;



import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

@Entity
@Table(name = "manager_casas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerCasaEntity {

    @Id
    @Name("id_relacion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El manager (usuario con rol MANAGER)
    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private UsuarioEntity manager;

    // La casa que gestiona
    @ManyToOne
    @JoinColumn(name = "casa_id", nullable = false)
    private CasaEntity casa;
}
