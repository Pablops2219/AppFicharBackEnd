package com.somllar.fichar.controller;

import com.somllar.fichar.persistance.entity.CasaEntity;
import com.somllar.fichar.service.CasaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/casas")
public class CasaController {

    @Autowired
    private CasaService casaService;

    @Operation(summary = "Crear una nueva casa")
    @PostMapping
    public CasaEntity saveCasa(@RequestBody CasaEntity casa) {
        return casaService.saveCasa(casa);
    }

    @Operation(summary = "Obtener todas las casas")
    @GetMapping
    public List<CasaEntity> getAllCasas() {
        return casaService.getAllCasas();
    }

    @Operation(summary = "Obtener casa por ID")
    @GetMapping("/{id}")
    public CasaEntity getCasaById(
            @Parameter(description = "ID de la casa a buscar") @PathVariable Long id) {
        return casaService.getCasaById(id);
    }

    @Operation(summary = "Eliminar una casa por ID")
    @DeleteMapping("/{id}")
    public void deleteCasa(
            @Parameter(description = "ID de la casa a eliminar") @PathVariable Long id) {
        casaService.deleteCasa(id);
    }
}
