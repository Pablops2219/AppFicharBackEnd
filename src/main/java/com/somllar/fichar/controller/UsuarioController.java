package com.somllar.fichar.controller;

import com.somllar.fichar.security.persistance.entity.Usuario;
import com.somllar.fichar.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(
                usuarioService.saveUsuario(usuario), HttpStatus.CREATED
        );
    }

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @Operation(summary = "Obtener usuario por ID")
    @GetMapping("{id}")
    public ResponseEntity<Usuario> getUsuarioById(
            @Parameter(description = "ID del usuario a buscar") @PathVariable("id") long usuarioID) {
        return new ResponseEntity<>(usuarioService.getUsuarioById(usuarioID), HttpStatus.OK);
    }

    @Operation(summary = "Actualizar un usuario")
    @PutMapping("{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar") @PathVariable("id") long id,
            @RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.updateUsuario(usuario, id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un usuario por ID")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar") @PathVariable("id") long id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
