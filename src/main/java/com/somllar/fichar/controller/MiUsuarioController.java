package com.somllar.fichar.controller;

import com.somllar.fichar.persistance.entity.FichajeEntity;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
import com.somllar.fichar.service.FichajeService;
import com.somllar.fichar.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "Obtener mis fichajes")
    @GetMapping("/misfichajes")
    public List<FichajeEntity> obtenerMisFichajes(Authentication authentication) {
        String email = authentication.getName();
        UsuarioEntity usuario = usuarioService.getUsuarioByEmail(email);
        return fichajeService.getFichajesByUsuario(usuario.getId());
    }

}
