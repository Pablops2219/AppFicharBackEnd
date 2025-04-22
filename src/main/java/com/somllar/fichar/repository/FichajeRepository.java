package com.somllar.fichar.repository;

import com.somllar.fichar.model.Fichaje;
import com.somllar.fichar.model.TipoFichaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface FichajeRepository extends JpaRepository<Fichaje, Long> {
    List<Fichaje> findByUsuarioId(Long usuarioId);
    List<Fichaje> findByCasaId(Long casaId);
    List<Fichaje> findByTipo(TipoFichaje tipo);

    List<Fichaje> findByHoraBetween(LocalDateTime inicio, LocalDateTime fin);

}
