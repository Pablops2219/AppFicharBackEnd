package com.somllar.fichar.repository;

import com.somllar.fichar.model.Empleado;
import com.somllar.fichar.model.Fichaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {
    Optional<Empleado> findByEmailUID(String emailUID);
}
