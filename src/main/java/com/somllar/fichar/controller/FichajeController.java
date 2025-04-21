package com.somllar.fichar.controller;

import com.somllar.fichar.model.Fichaje;
import com.somllar.fichar.model.TipoFichaje;
import com.somllar.fichar.service.FichajeService;
import com.somllar.fichar.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/fichajes")
@CrossOrigin(origins = "*")
public class FichajeController {

    @Autowired
    private FichajeService fichajeService;
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Crear un nuevo fichaje.\nSi no se proporciona una hora, se asigna la hora actual automáticamente en el servicio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fichaje creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos del fichaje")
    })
    @PostMapping
    public Fichaje crearFichaje(@RequestBody Fichaje fichaje) {
        return fichajeService.saveFichaje(fichaje);
    }

    @Operation(summary = "Obtener todos los fichajes")
    @GetMapping
    public List<Fichaje> obtenerTodos() {
        return fichajeService.getAllFichajes();
    }

    @Operation(summary = "Obtener fichaje por ID")
    @GetMapping("/{id}")
    public Fichaje obtenerPorId(
            @Parameter(description = "ID del fichaje a buscar") @PathVariable Long id) {
        return fichajeService.getFichajeById(id);
    }

    @Operation(summary = "Obtener fichajes por ID de usuario")
    @GetMapping("/usuario/{usuarioId}")
    public List<Fichaje> getFichajesByUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        return fichajeService.getFichajesByUsuario(usuarioId);
    }

    @Operation(summary = "Obtener fichajes por ID de casa")
    @GetMapping("/casa/{casaId}")
    public List<Fichaje> getFichajesByCasa(
            @Parameter(description = "ID de la casa") @PathVariable Long casaId) {
        return fichajeService.getFichajesByCasa(casaId);
    }

    @Operation(summary = "Obtener fichajes por tipo (ENTRADA o SALIDA)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fichajes del tipo especificado"),
            @ApiResponse(responseCode = "400", description = "Tipo no válido")
    })
    @GetMapping("/tipo/{tipo}")
    public List<Fichaje> getFichajesByTipo(
            @Parameter(description = "Tipo de fichaje (ENTRADA o SALIDA)") @PathVariable String tipo) {
        TipoFichaje tipoEnum = TipoFichaje.valueOf(tipo.toUpperCase());
        return fichajeService.getFichajesByTipo(tipoEnum);
    }

    @Operation(summary = "Obtener fichajes entre fechas")
    @GetMapping("/fecha")
    public List<Fichaje> getFichajesByRangoFechas(
            @Parameter(description = "Fecha de inicio en formato ISO 8601")
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,

            @Parameter(description = "Fecha de fin en formato ISO 8601")
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return fichajeService.getFichajesByRangoFechas(inicio, fin);
    }

}
