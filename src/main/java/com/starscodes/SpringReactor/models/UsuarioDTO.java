package com.starscodes.SpringReactor.models;

import lombok.Data;

@Data
public class UsuarioDTO {

    private String nombre;
    private String apellido;

    public UsuarioDTO(String nombre, String apellido) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
