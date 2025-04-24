package com.somllar.fichar.service;

import com.somllar.fichar.persistance.entity.CasaEntity;

import java.util.List;

public interface CasaService {
    CasaEntity saveCasa(CasaEntity casa);
    List<CasaEntity> getAllCasas();
    CasaEntity getCasaById(Long id);
    void deleteCasa(Long id);
}
