package com.somllar.fichar.service.impl;

import com.google.firebase.auth.*;
import com.somllar.fichar.model.Empleado;
import com.somllar.fichar.repository.EmpleadoRepository;
import com.somllar.fichar.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    //save employee in database
    @Override
    public Empleado saveEmpleado(Empleado empleado){
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(empleado.getEmail())
                    .setPassword(empleado.getPassword());

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("✅ Usuario Firebase creado con UID: " + userRecord.getUid());

            empleado.setEmailUID(userRecord.getUid());

        } catch (Exception e) {
            throw new RuntimeException("❌ Error al crear usuario en Firebase: " + e.getMessage());
        }

        return empleadoRepository.save(empleado);
    }

    //get all employee form database
    @Override
    public List<Empleado> getAllEmpleado() {
        return empleadoRepository.findAll();
    }

    //get employee using id
    @Override
    public Empleado getEmpleadoById(long id) {
        Optional<Empleado> empleado =  empleadoRepository.findById(id);
        if(empleado.isPresent()){
            return empleado.get();
        }else {
            throw new RuntimeException();
        }
    }

    //update employee
    @Override
    public Empleado updateEmpleado(Empleado empleado, long id) {
        Empleado existingEmpleado = empleadoRepository.findById(id).orElseThrow(
                ()-> new RuntimeException()
        );
        existingEmpleado.setFirstName(empleado.getFirstName());
        existingEmpleado.setLastName(empleado.getLastName());
        existingEmpleado.setEmailUID(empleado.getEmailUID());
        // save
        empleadoRepository.save(existingEmpleado);
        return existingEmpleado;
    }

    //delete employee
    @Override
    public void deleteEmpleado(long id) {
        //check
        empleadoRepository.findById(id).orElseThrow(()-> new RuntimeException());

        try {
            FirebaseAuth.getInstance().deleteUser(getEmpleadoById(id).getEmailUID());
            System.out.println("✅ Usuario " + getEmpleadoById(id).getFirstName() + " borrado de Firebase");
        } catch (Exception e) {
            throw new RuntimeException("❌ Error al borrar al usuario en Firebase: " + e.getMessage());
        }

        //delete
        empleadoRepository.deleteById(id);
    }

    //get employee using id
    @Override
    public Empleado getEmpleadoByEmailUID(String UID) {
        Optional<Empleado> empleado =  empleadoRepository.findByEmailUID(UID);
        if(empleado.isPresent()){
            return empleado.get();
        }else {
            throw new RuntimeException();
        }
    }
}
