package com.somllar.fichar.service;

import com.somllar.fichar.persistance.entity.FichajeEntity;
import com.somllar.fichar.persistance.entity.FichajeEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface FichajeService {
    FichajeEntity saveFichaje(FichajeEntity fichaje);
    List<FichajeEntity> getAllFichajes();
    FichajeEntity getFichajeById(Long id);

    List<FichajeEntity> getFichajesByUsuario(Long usuarioId);
    List<FichajeEntity> getFichajesByCasa(Long casaId);
    List<FichajeEntity> getFichajesByTipo(FichajeEnum tipo);

    List<FichajeEntity> getFichajesByRangoFechas(LocalDateTime inicio, LocalDateTime fin);


}
