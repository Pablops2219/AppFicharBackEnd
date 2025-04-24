package com.somllar.fichar.controller;

import com.somllar.fichar.persistance.entity.UsuarioEntity;
import com.somllar.fichar.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableWebSecurity
@EnableMethodSecurity
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioEntity> saveUsuario(@RequestBody UsuarioEntity usuario) {
        return new ResponseEntity<>(
                usuarioService.saveUsuario(usuario), HttpStatus.CREATED
        );
    }

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public List<UsuarioEntity> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @Operation(summary = "Obtener usuario por ID")
    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UsuarioEntity> getUsuarioById(
            @Parameter(description = "ID del usuario a buscar") @PathVariable("id") long usuarioID) {
        return new ResponseEntity<>(usuarioService.getUsuarioById(usuarioID), HttpStatus.OK);
    }

    @Operation(summary = "Actualizar un usuario")
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER')")
    @PutMapping("{id}")
    public ResponseEntity<UsuarioEntity> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar") @PathVariable("id") long id,
            @RequestBody UsuarioEntity usuario) {
        return new ResponseEntity<>(usuarioService.updateUsuario(usuario, id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un usuario por ID")
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar") @PathVariable("id") long id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
