package com.somllar.fichar.controller;

import com.somllar.fichar.persistance.entity.CasaEntity;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
import com.somllar.fichar.service.CasaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER')")
    @Operation(summary = "Actualiza parcialmente una casa")
    public ResponseEntity<CasaEntity> patchCasa(
            @PathVariable Long id,
            @RequestBody CasaEntity cambios) {

        CasaEntity existente = casaService.getCasaById(id);

        if (cambios.getNombre() != null) {
            existente.setNombre(cambios.getNombre());
        }

        CasaEntity actualizado = casaService.saveCasa(existente);
        return ResponseEntity.ok(actualizado);
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
