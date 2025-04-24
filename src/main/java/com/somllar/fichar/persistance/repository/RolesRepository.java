package com.somllar.fichar.persistance.repository;

import com.somllar.fichar.persistance.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {

    List<RolesEntity> findByRoleEnumIn(List<String> roleNames);
}
