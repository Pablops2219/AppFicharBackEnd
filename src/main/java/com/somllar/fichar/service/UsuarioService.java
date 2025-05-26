package com.somllar.fichar.service;


import com.somllar.fichar.dto.ManagerCasaUpdateDTO;
import com.somllar.fichar.dto.ManagerDTO;
import com.somllar.fichar.persistance.entity.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    UsuarioEntity saveUsuario(UsuarioEntity usuario);
    List<UsuarioEntity> getAllUsuarios();
    List<ManagerDTO> getAllManagers();
    void actualizarCasasDeManager(ManagerCasaUpdateDTO dto);
    UsuarioEntity getUsuarioById(Long id);

    UsuarioEntity getUsuarioByEmail(String email);
    UsuarioEntity updateUsuario(UsuarioEntity usuario, long id);
    void deleteUsuario(Long id);
}
