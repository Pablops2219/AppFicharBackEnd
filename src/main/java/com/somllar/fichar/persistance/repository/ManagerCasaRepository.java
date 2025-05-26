package com.somllar.fichar.persistance.repository;


import com.somllar.fichar.persistance.entity.ManagerCasaEntity;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerCasaRepository extends JpaRepository<ManagerCasaEntity, Long> {
    List<ManagerCasaEntity> findByManager(UsuarioEntity manager);

    List<ManagerCasaEntity> deleteByManager(UsuarioEntity manager);

}
