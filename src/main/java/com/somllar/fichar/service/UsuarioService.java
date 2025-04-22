package com.somllar.fichar.service;


import com.somllar.fichar.security.persistance.entity.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    UsuarioEntity saveUsuario(UsuarioEntity usuario);
    List<UsuarioEntity> getAllUsuarios();
    UsuarioEntity getUsuarioById(Long id);
    UsuarioEntity updateUsuario(UsuarioEntity usuario, long id);
    void deleteUsuario(Long id);
}
