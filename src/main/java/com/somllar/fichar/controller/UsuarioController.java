package com.somllar.fichar.controller;

import com.somllar.fichar.dto.ManagerCasaUpdateDTO;
import com.somllar.fichar.dto.ManagerDTO;
import com.somllar.fichar.dto.UsuarioPatchDTO;
import com.somllar.fichar.persistance.entity.RolesEntity;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
import com.somllar.fichar.persistance.repository.RolesRepository;
import com.somllar.fichar.security.controller.dto.AuthCreateUserRequest;
import com.somllar.fichar.security.controller.dto.AuthResponse;
import com.somllar.fichar.service.UsuarioService;
import com.somllar.fichar.service.impl.UserDetailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@EnableWebSecurity
@EnableMethodSecurity
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER')")
    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest, false), HttpStatus.CREATED);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesRepository rolesRepository;


    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER')")
    @Operation(summary = "Actualiza parcialmente un usuario, incluyendo roles")
    public ResponseEntity<UsuarioEntity> patchUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioPatchDTO cambios) {

        UsuarioEntity existente = usuarioService.getUsuarioById(id);

        if (cambios.getEmail() != null) {
            existente.setEmail(cambios.getEmail());
        }
        if (cambios.getFirstName() != null) {
            existente.setFirstName(cambios.getFirstName());
        }
        if (cambios.getLastName() != null) {
            existente.setLastName(cambios.getLastName());
        }
        if (cambios.getPassword() != null && !cambios.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(cambios.getPassword())); // Encripta si hace falta
        }

        if (cambios.getRoles() != null && !cambios.getRoles().isEmpty()) {
            List<String> roleNames = cambios.getRoles();

            List<RolesEntity> rolesEncontrados = rolesRepository.findByRoleEnumIn(roleNames);

            if (rolesEncontrados.size() != roleNames.size()) {
                return ResponseEntity.badRequest().build();
            }

            existente.setRoles(new HashSet<>(rolesEncontrados));
        }
        UsuarioEntity actualizado = usuarioService.saveUsuario(existente);
        return ResponseEntity.ok(actualizado);
    }




    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public List<UsuarioEntity> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }


    @Operation(summary = "Obtener todos los managers")
    @GetMapping("/managers")
    public List<ManagerDTO> getAllManagers() {
        return usuarioService.getAllManagers();
    }

    @Operation(summary = "Actualizar las casas de un manager")
    @PutMapping("/managers")
    public ResponseEntity<Void> actualizarCasasManager(@RequestBody ManagerCasaUpdateDTO dto) {
        usuarioService.actualizarCasasDeManager(dto);
        return ResponseEntity.noContent().build();
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
