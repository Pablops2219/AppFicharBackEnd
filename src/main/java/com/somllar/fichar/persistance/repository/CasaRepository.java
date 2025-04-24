package com.somllar.fichar.persistance.repository;

import com.somllar.fichar.persistance.entity.CasaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasaRepository extends JpaRepository<CasaEntity, Long> {
}
