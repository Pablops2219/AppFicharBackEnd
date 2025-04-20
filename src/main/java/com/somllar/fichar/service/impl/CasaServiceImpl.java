package com.somllar.fichar.service.impl;

import com.somllar.fichar.model.Casa;
import com.somllar.fichar.repository.CasaRepository;
import com.somllar.fichar.service.CasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasaServiceImpl implements CasaService {

    @Autowired
    private CasaRepository casaRepository;

    @Override
    public Casa saveCasa(Casa casa) {
        return casaRepository.save(casa);
    }

    @Override
    public List<Casa> getAllCasas() {
        return casaRepository.findAll();
    }

    @Override
    public Casa getCasaById(Long id) {
        return casaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Casa no encontrada con id " + id));
    }

    @Override
    public void deleteCasa(Long id) {
        casaRepository.deleteById(id);
    }
}
