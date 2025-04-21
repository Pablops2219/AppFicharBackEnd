package com.somllar.fichar.service;

import com.somllar.fichar.model.Fichaje;
import com.somllar.fichar.model.TipoFichaje;

import java.time.LocalDateTime;
import java.util.List;

public interface FichajeService {
    Fichaje saveFichaje(Fichaje fichaje);
    List<Fichaje> getAllFichajes();
    Fichaje getFichajeById(Long id);

    List<Fichaje> getFichajesByUsuario(Long usuarioId);
    List<Fichaje> getFichajesByCasa(Long casaId);
    List<Fichaje> getFichajesByTipo(TipoFichaje tipo);

    List<Fichaje> getFichajesByRangoFechas(LocalDateTime inicio, LocalDateTime fin);


}
