package com.somllar.fichar.service.impl;

import com.somllar.fichar.persistance.entity.FichajeEntity;
import com.somllar.fichar.persistance.entity.FichajeEnum;
import com.somllar.fichar.persistance.repository.FichajeRepository;
import com.somllar.fichar.service.FichajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FichajeServiceImpl implements FichajeService {

    @Autowired
    private FichajeRepository fichajeRepository;

    @Override
    public FichajeEntity saveFichaje(FichajeEntity fichaje) {
        if (fichaje.getHora() == null) {
            fichaje.setHora(LocalDateTime.now());
        }
        return fichajeRepository.save(fichaje);
    }

    @Override
    public List<FichajeEntity> getAllFichajes() {
        return fichajeRepository.findAll();
    }

    @Override
    public FichajeEntity getFichajeById(Long id) {
        return fichajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fichaje no encontrado con id: " + id));
    }

    @Override
    public List<FichajeEntity> getFichajesByUsuario(Long usuarioId) {
        return fichajeRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<FichajeEntity> getFichajesByCasa(Long casaId) {
        return fichajeRepository.findByCasaId(casaId);
    }

    @Override
    public List<FichajeEntity> getFichajesByTipo(FichajeEnum tipo) {
        return fichajeRepository.findByTipo(tipo);
    }

    @Override
    public List<FichajeEntity> getFichajesByRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return fichajeRepository.findByHoraBetween(inicio, fin);
    }
}
