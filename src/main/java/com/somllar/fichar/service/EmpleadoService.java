package com.somllar.fichar.service;

import com.somllar.fichar.model.Empleado;

import java.util.List;

public interface EmpleadoService {
    Empleado saveEmpleado(Empleado employee);
    List<Empleado> getAllEmpleado();
    Empleado getEmpleadoById(long id);

    Empleado getEmpleadoByEmailUID(String UID);
    Empleado updateEmpleado(Empleado employee, long id);
    void deleteEmpleado(long id);
}
