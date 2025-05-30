package com.somllar.fichar.security.controller.dto;

import lombok.Data;

@Data
public class AuthImportUsuarioSinContra {
    private String nombre;
    private String apellidos;
    private String email;
}
