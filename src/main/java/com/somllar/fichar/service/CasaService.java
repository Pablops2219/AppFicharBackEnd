package com.somllar.fichar.service;

import com.somllar.fichar.model.Casa;

import java.util.List;

public interface CasaService {
    Casa saveCasa(Casa casa);
    List<Casa> getAllCasas();
    Casa getCasaById(Long id);
    void deleteCasa(Long id);
}
