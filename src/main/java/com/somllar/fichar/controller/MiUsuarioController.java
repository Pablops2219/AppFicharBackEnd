package com.somllar.fichar.controller;

import com.somllar.fichar.persistance.entity.FichajeEntity;
import com.somllar.fichar.persistance.entity.FichajeEnum;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
import com.somllar.fichar.security.controller.dto.FichajeRequest;
import com.somllar.fichar.service.CasaService;
import com.somllar.fichar.service.FichajeService;
import com.somllar.fichar.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableWebSecurity
@EnableMethodSecurity
@RequestMapping("/api/miusuario")
public class MiUsuarioController {

    @Autowired
    private FichajeService fichajeService;
    @Autowired
    private UsuarioService usuarioService;;
    @Autowired
    private CasaService casaService;

    @Operation(summary = "Crea un fichaje en el momento a partir del token enviado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fichaje creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos del fichaje")
    })
    @PostMapping("/fichar")
    public FichajeEntity fichar(Authentication authentication, @RequestBody FichajeRequest fichajeRequest) {
        String email = authentication.getName();
        UsuarioEntity usuario = usuarioService.getUsuarioByEmail(email);
        FichajeEntity fichaje = new FichajeEntity();
        fichaje.setUsuario(usuario);
        fichaje.setCasa(casaService.getCasaById(fichajeRequest.getCasaid()));
        fichaje.setTipo(fichajeRequest.getTipo());
        return fichajeService.saveFichaje(fichaje);
    }

    @Operation(summary = "Obtener mis fichajes")
    @GetMapping("/misfichajes")
    public List<FichajeEntity> obtenerMisFichajes(Authentication authentication) {
        String email = authentication.getName();
        UsuarioEntity usuario = usuarioService.getUsuarioByEmail(email);
        return fichajeService.getFichajesByUsuario(usuario.getId());
    }

}
