package com.dam.example_DDL_DML.Universidad.Dto;

import java.util.Date;

public class Estudiante {

    private int id;
    private String dni;
    private Date fecha_nacimiento;
    private boolean estado;
    private Date fecha_matriculacion;
    private Date ultima_actualizacion;
    private String enviado;

    public Estudiante() {
    }

    public Estudiante(int id, String dni, Date fecha_nacimiento, boolean estado, Date fecha_matriculacion, Date ultima_actualizacion, String enviado) {
        this.id = id;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.estado = estado;
        this.fecha_matriculacion = fecha_matriculacion;
        this.ultima_actualizacion = ultima_actualizacion;
        this.enviado = enviado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFecha_matriculacion() {
        return fecha_matriculacion;
    }

    public void setFecha_matriculacion(Date fecha_matriculacion) {
        this.fecha_matriculacion = fecha_matriculacion;
    }

    public Date getUltima_actualizacion() {
        return ultima_actualizacion;
    }

    public void setUltima_actualizacion(Date ultima_actualizacion) {
        this.ultima_actualizacion = ultima_actualizacion;
    }

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }
}
