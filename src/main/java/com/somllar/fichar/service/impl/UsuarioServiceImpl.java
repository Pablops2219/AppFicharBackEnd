package com.somllar.fichar.service.impl;

import com.somllar.fichar.security.persistance.entity.UsuarioEntity;
import com.somllar.fichar.security.persistance.repository.UsuarioRepository;
import com.somllar.fichar.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioEntity saveUsuario(UsuarioEntity usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<UsuarioEntity> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioEntity getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrada con id " + id));
    }

    @Override
    public UsuarioEntity updateUsuario(UsuarioEntity usuario, long id) {
        UsuarioEntity existingUsuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        existingUsuario.setFirstName(usuario.getFirstName());
        existingUsuario.setLastName(usuario.getLastName());
        existingUsuario.setUsername(generarUsername(usuario.getFirstName(), usuario.getLastName()));
        existingUsuario.setEmail(usuario.getEmail());
        existingUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        existingUsuario.setEnabled(usuario.isEnabled());
        existingUsuario.setAccountNoExpired(usuario.isAccountNoExpired());
        existingUsuario.setAccountNoLocked(usuario.isAccountNoLocked());
        existingUsuario.setCredentialNoExpired(usuario.isCredentialNoExpired());

        return usuarioRepository.save(existingUsuario);
    }

    private String generarUsername(String nombre, String apellido) {
        String parteNombre = nombre.length() >= 3 ? nombre.substring(0, 3) : nombre;
        String parteApellido = apellido.length() >= 3 ? apellido.substring(0, 3) : apellido;
        return parteNombre + parteApellido;
    }


    @Override
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
