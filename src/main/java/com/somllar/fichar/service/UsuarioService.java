package com.somllar.fichar.service;


import com.somllar.fichar.security.persistance.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario saveUsuario(Usuario usuario);
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Long id);
    Usuario updateUsuario(Usuario usuario, long id);
    void deleteUsuario(Long id);
}
