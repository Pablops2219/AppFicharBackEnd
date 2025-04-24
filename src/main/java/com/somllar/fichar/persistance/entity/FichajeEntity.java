package com.somllar.fichar.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fichajes")
public class FichajeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "casa_id", nullable = false)
    private CasaEntity casa;

    private LocalDateTime hora;

    @Enumerated(EnumType.STRING)
    private FichajeEnum tipo;

}
