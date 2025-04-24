package com.somllar.fichar.persistance.repository;

import com.somllar.fichar.persistance.entity.FichajeEntity;
import com.somllar.fichar.persistance.entity.FichajeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface FichajeRepository extends JpaRepository<FichajeEntity, Long> {
    List<FichajeEntity> findByUsuarioId(Long usuarioId);
    List<FichajeEntity> findByCasaId(Long casaId);
    List<FichajeEntity> findByTipo(FichajeEnum tipo);

    List<FichajeEntity> findByHoraBetween(LocalDateTime inicio, LocalDateTime fin);

}
