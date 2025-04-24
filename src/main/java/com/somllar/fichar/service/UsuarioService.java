package com.somllar.fichar.service;


import com.somllar.fichar.persistance.entity.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    UsuarioEntity saveUsuario(UsuarioEntity usuario);
    List<UsuarioEntity> getAllUsuarios();
    UsuarioEntity getUsuarioById(Long id);

    UsuarioEntity getUsuarioByEmail(String email);
    UsuarioEntity updateUsuario(UsuarioEntity usuario, long id);
    void deleteUsuario(Long id);
}
