package com.somllar.fichar.dto;

import lombok.Data;

import java.util.List;
@Data
public class UsuarioPatchDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> roles;
}
