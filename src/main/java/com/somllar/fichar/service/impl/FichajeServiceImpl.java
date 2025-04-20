package com.somllar.fichar.service.impl;

import com.somllar.fichar.model.Fichaje;
import com.somllar.fichar.model.TipoFichaje;
import com.somllar.fichar.repository.FichajeRepository;
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
    public Fichaje saveFichaje(Fichaje fichaje) {
        if (fichaje.getHora() == null) {
            fichaje.setHora(LocalDateTime.now());
        }
        return fichajeRepository.save(fichaje);
    }

    @Override
    public List<Fichaje> getAllFichajes() {
        return fichajeRepository.findAll();
    }

    @Override
    public Fichaje getFichajeById(Long id) {
        return fichajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fichaje no encontrado con id: " + id));
    }

    @Override
    public List<Fichaje> getFichajesByEmpleado(Long empleadoId) {
        return fichajeRepository.findByEmpleadoId(empleadoId);
    }

    @Override
    public List<Fichaje> getFichajesByCasa(Long casaId) {
        return fichajeRepository.findByCasaId(casaId);
    }

    @Override
    public List<Fichaje> getFichajesByTipo(TipoFichaje tipo) {
        return fichajeRepository.findByTipo(tipo);
    }

    @Override
    public List<Fichaje> getFichajesByRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return fichajeRepository.findByHoraBetween(inicio, fin);
    }
    @Override
    public List<Fichaje> getFichajesByEmailUID(String emailUID) {
        return fichajeRepository.findByEmpleadoEmailUID(emailUID);
    }
}
