package com.somllar.fichar.controller;

import com.somllar.fichar.model.Empleado;
import com.somllar.fichar.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Operation(summary = "Crear un nuevo empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Empleado> saveEmployee(@RequestBody Empleado empleado) {
        return new ResponseEntity<>(
                empleadoService.saveEmpleado(empleado), HttpStatus.CREATED
        );
    }

    @Operation(summary = "Obtener todos los empleados")
    @GetMapping
    public List<Empleado> getAllEmpleado() {
        return empleadoService.getAllEmpleado();
    }

    @Operation(summary = "Obtener empleado por ID")
    @GetMapping("{id}")
    public ResponseEntity<Empleado> getEmpleadoById(
            @Parameter(description = "ID del empleado a buscar") @PathVariable("id") long empleadoID) {
        return new ResponseEntity<>(empleadoService.getEmpleadoById(empleadoID), HttpStatus.OK);
    }

    @Operation(summary = "Actualizar un empleado")
    @PutMapping("{id}")
    public ResponseEntity<Empleado> updateEmpleado(
            @Parameter(description = "ID del empleado a actualizar") @PathVariable("id") long id,
            @RequestBody Empleado empleado) {
        return new ResponseEntity<>(empleadoService.updateEmpleado(empleado, id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un empleado por ID")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmpleado(
            @Parameter(description = "ID del empleado a eliminar") @PathVariable("id") long id) {
        empleadoService.deleteEmpleado(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Obtener empleado por emailUID")
    @GetMapping("/emailUID/{emailUID}")
    public ResponseEntity<Empleado> getEmpleadoByEmailUID(
            @Parameter(description = "UID de la cuenta del empleado a buscar") @PathVariable("emailUID") String emailUID) {
        Empleado empleado = empleadoService.getEmpleadoByEmailUID(emailUID);

        if (empleado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 si no se encuentra el empleado
        }

        return new ResponseEntity<>(empleado, HttpStatus.OK); // Retorna el empleado con estado 200
    }

}
