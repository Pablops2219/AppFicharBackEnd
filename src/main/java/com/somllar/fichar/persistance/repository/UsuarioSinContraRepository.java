package com.somllar.fichar.persistance.repository;

import com.somllar.fichar.persistance.entity.FichajeEntity;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
import com.somllar.fichar.persistance.entity.UsuarioSinContraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioSinContraRepository  extends JpaRepository<UsuarioSinContraEntity, Long> {
    Optional<UsuarioSinContraEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
