package com.somllar.fichar.model;

import com.somllar.fichar.security.persistance.entity.UsuarioEntity;
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
public class Fichaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "casa_id", nullable = false)
    private Casa casa;

    private LocalDateTime hora;

    @Enumerated(EnumType.STRING)
    private TipoFichaje tipo;

}
