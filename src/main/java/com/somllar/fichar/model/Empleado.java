package com.somllar.fichar.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empleados")

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombre",nullable = false)
    private String firstName;
    @Column(name = "apellidos",nullable = false)
    private String lastName;
    @Column(name = "email_uid",nullable = false)
    private String emailUID;

    @Transient
    private String password;

    @Transient
    private String email;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fichaje> fichajes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailUID() {
        return emailUID;
    }

    public void setEmailUID(String emailUID) {
        this.emailUID = emailUID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Fichaje> getFichajes() {
        return fichajes;
    }

    public void setFichajes(List<Fichaje> fichajes) {
        this.fichajes = fichajes;
    }
}
