package com.somllar.fichar.controller;

import com.somllar.fichar.security.persistance.entity.UsuarioEntity;
import com.somllar.fichar.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UsuarioEntity> saveUsuario(@RequestBody UsuarioEntity usuario) {
        return new ResponseEntity<>(
                usuarioService.saveUsuario(usuario), HttpStatus.CREATED
        );
    }

    @Operation(summary = "Obtener todos los usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UsuarioEntity> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @Operation(summary = "Obtener usuario por ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioById(
            @Parameter(description = "ID del usuario a buscar") @PathVariable("id") long usuarioID) {
        return new ResponseEntity<>(usuarioService.getUsuarioById(usuarioID), HttpStatus.OK);
    }

    @Operation(summary = "Actualizar un usuario")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<UsuarioEntity> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar") @PathVariable("id") long id,
            @RequestBody UsuarioEntity usuario) {
        return new ResponseEntity<>(usuarioService.updateUsuario(usuario, id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un usuario por ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar") @PathVariable("id") long id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
