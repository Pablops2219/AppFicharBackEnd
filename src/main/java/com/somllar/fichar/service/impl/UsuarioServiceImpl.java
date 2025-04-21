package com.somllar.fichar.service.impl;

import com.somllar.fichar.security.persistance.entity.Usuario;
import com.somllar.fichar.security.persistance.repository.UsuarioRepository;
import com.somllar.fichar.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrada con id " + id));
    }

    @Override
    public Usuario updateUsuario(Usuario usuario, long id) {
        Usuario existingUsuario = usuarioRepository.findById(id).orElseThrow(
                ()-> new RuntimeException()
        );
        existingUsuario.setFirstName(usuario.getFirstName());
        existingUsuario.setLastName(usuario.getLastName());
        existingUsuario.setUsername(usuario.getFirstName().substring(0,3) + usuario.getLastName().substring(0,3));
        existingUsuario.setEmail(usuario.getEmail());
        existingUsuario.setPassword(usuario.getPassword());
        existingUsuario.setEnabled(usuario.isEnabled());
        existingUsuario.setAccountNoExpired(usuario.isAccountNoExpired());
        existingUsuario.setAccountNoLocked(usuario.isAccountNoLocked());
        existingUsuario.setAccountNoExpired(usuario.isAccountNoExpired());
        // save
        usuarioRepository.save(existingUsuario);
        return existingUsuario;
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
