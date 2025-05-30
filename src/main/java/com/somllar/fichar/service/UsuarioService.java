package com.somllar.fichar.service;


import com.somllar.fichar.dto.ManagerCasaUpdateDTO;
import com.somllar.fichar.dto.ManagerDTO;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
import com.somllar.fichar.persistance.entity.UsuarioSinContraEntity;
import com.somllar.fichar.security.controller.dto.AuthImportUsuarioSinContra;
import com.somllar.fichar.security.controller.dto.AuthLoginRequest;
import com.somllar.fichar.security.controller.dto.AuthResponse;

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
    AuthResponse confirmarUsuario(AuthLoginRequest authLoginRequest);
    void importarUsuarios(List<AuthImportUsuarioSinContra> usuariosDTO);
    List<UsuarioSinContraEntity> getAllUsuariosSinContrasena();
}
