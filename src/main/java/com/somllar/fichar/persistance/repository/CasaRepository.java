package com.somllar.fichar.persistance.repository;

import com.somllar.fichar.persistance.entity.CasaEntity;
import com.somllar.fichar.persistance.entity.FichajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasaRepository extends JpaRepository<CasaEntity, Long> {

}
