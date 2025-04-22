package com.somllar.fichar.repository;

import com.somllar.fichar.model.Casa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasaRepository extends JpaRepository<Casa, Long> {
}
