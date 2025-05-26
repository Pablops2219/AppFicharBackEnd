package com.somllar.fichar.persistance.repository;

import com.somllar.fichar.persistance.entity.RoleEnum;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    List<UsuarioEntity> findByRolesRoleEnum(RoleEnum nombre);


    Optional<UsuarioEntity> findByUsername(String username);


    Optional<UsuarioEntity> findByEmail(String email);



}