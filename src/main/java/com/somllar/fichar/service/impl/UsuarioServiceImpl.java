package com.somllar.fichar.service.impl;

import com.somllar.fichar.dto.ManagerCasaUpdateDTO;
import com.somllar.fichar.dto.ManagerDTO;
import com.somllar.fichar.persistance.entity.CasaEntity;
import com.somllar.fichar.persistance.entity.ManagerCasaEntity;
import com.somllar.fichar.persistance.entity.RoleEnum;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
import com.somllar.fichar.persistance.repository.CasaRepository;
import com.somllar.fichar.persistance.repository.ManagerCasaRepository;
import com.somllar.fichar.persistance.repository.RolesRepository;
import com.somllar.fichar.persistance.repository.UsuarioRepository;
import com.somllar.fichar.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.somllar.fichar.persistance.entity.UsuarioEntity.generarUsername;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ManagerCasaRepository managerCasaRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private CasaRepository casaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioEntity saveUsuario(UsuarioEntity usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setUsername(generarUsername(usuario));
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
    public UsuarioEntity getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrada con email " + email));
    }

    @Override
    public UsuarioEntity updateUsuario(UsuarioEntity usuario, long id) {
        UsuarioEntity existingUsuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        existingUsuario.setFirstName(usuario.getFirstName());
        existingUsuario.setLastName(usuario.getLastName());
        existingUsuario.setUsername(usuario.getUsername());
        existingUsuario.setEmail(usuario.getEmail());

        // Solo codificamos la contraseña si ha cambiado
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            existingUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        existingUsuario.getRoles().clear();
        existingUsuario.getRoles().addAll(usuario.getRoles());

        existingUsuario.setEnabled(usuario.isEnabled());
        existingUsuario.setAccountNoExpired(usuario.isAccountNoExpired());
        existingUsuario.setAccountNoLocked(usuario.isAccountNoLocked());
        existingUsuario.setCredentialNoExpired(usuario.isCredentialNoExpired());

        return usuarioRepository.save(existingUsuario);
    }

    @Override
    public List<ManagerDTO> getAllManagers() {
        return usuarioRepository.findByRolesRoleEnum(RoleEnum.MANAGER)
                .stream()
                .map(usuario -> {
                    ManagerDTO dto = new ManagerDTO();
                    dto.setId(usuario.getId());
                    dto.setFirstName(usuario.getFirstName());
                    dto.setLastName(usuario.getLastName());
                    List<ManagerCasaEntity> relaciones = managerCasaRepository.findByManager(usuario);
                    List<CasaEntity> casasVigiladas = new ArrayList<>();

                    for (ManagerCasaEntity relacione : relaciones) {
                        casasVigiladas.add(relacione.getCasa());
                    }

                    dto.setCasasVigiladas(casasVigiladas);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void actualizarCasasDeManager(ManagerCasaUpdateDTO dto) {
        UsuarioEntity manager = usuarioRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager no encontrado"));

        boolean esManager = manager.getRoles().stream()
                .anyMatch(rol -> rol.getRoleEnum() == RoleEnum.MANAGER);
        if (!esManager) {
            throw new RuntimeException("Este usuario no tiene rol MANAGER");
        }

        // Eliminar asociaciones anteriores
        managerCasaRepository.deleteByManager(manager);

        // Crear nuevas asociaciones
        List<CasaEntity> casas = casaRepository.findAllById(dto.getCasaIds());
        List<ManagerCasaEntity> nuevasRelaciones = casas.stream()
                .map(casa -> ManagerCasaEntity.builder()
                        .manager(manager)
                        .casa(casa)
                        .build())
                .toList();

        managerCasaRepository.saveAll(nuevasRelaciones);
    }



    @Override
    public void deleteUsuario(Long id) {

        usuarioRepository.deleteById(id);
    }
}
