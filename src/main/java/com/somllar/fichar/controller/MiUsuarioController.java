package com.somllar.fichar.controller;

import com.somllar.fichar.persistance.entity.*;
import com.somllar.fichar.persistance.repository.ManagerCasaRepository;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableWebSecurity
@EnableMethodSecurity
@RequestMapping("/api/miusuario")
public class MiUsuarioController {

    @Autowired
    private FichajeService fichajeService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CasaService casaService;

    @Autowired
    private ManagerCasaRepository managerCasaRepository;

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

    @Operation(summary = "Obtener las casas en las que soy manager")
    @GetMapping("/manager-casas")
    public List<CasaEntity> obtenerCasasManager(Authentication authentication) {
        String email = authentication.getName();
        UsuarioEntity usuario = usuarioService.getUsuarioByEmail(email);

        List<ManagerCasaEntity> relaciones = managerCasaRepository.findByManager(usuario);

        return relaciones.stream()
                .map(ManagerCasaEntity::getCasa)
                .toList();
    }

    @Operation(summary = "Obtener los fichajes de las casas en las que soy manager")
    @GetMapping("/manager-fichajes")
    public List<FichajeEntity> obtenerFichajesManager(Authentication authentication) {
        String email = authentication.getName();
        UsuarioEntity usuario = usuarioService.getUsuarioByEmail(email);

        List<ManagerCasaEntity> relaciones = managerCasaRepository.findByManager(usuario);

        List<CasaEntity> casas = relaciones.stream()
                .map(ManagerCasaEntity::getCasa)
                .toList();

        List<FichajeEntity> fichajes = new ArrayList<>();

        System.out.println("A ver " + usuario + relaciones + casas);

        for (CasaEntity casa : casas) {
            fichajes.addAll(fichajeService.getFichajesByCasa(casa.getId()));
        }

        return fichajes;
    }
}
