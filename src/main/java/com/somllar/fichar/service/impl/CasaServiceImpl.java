package com.somllar.fichar.service.impl;

import com.somllar.fichar.persistance.entity.CasaEntity;
import com.somllar.fichar.persistance.repository.CasaRepository;
import com.somllar.fichar.service.CasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasaServiceImpl implements CasaService {

    @Autowired
    private CasaRepository casaRepository;

    @Override
    public CasaEntity saveCasa(CasaEntity casa) {
        return casaRepository.save(casa);
    }

    @Override
    public List<CasaEntity> getAllCasas() {
        return casaRepository.findAll();
    }

    @Override
    public CasaEntity getCasaById(Long id) {
        return casaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Casa no encontrada con id " + id));
    }

    @Override
    public void deleteCasa(Long id) {
        casaRepository.deleteById(id);
    }
}
