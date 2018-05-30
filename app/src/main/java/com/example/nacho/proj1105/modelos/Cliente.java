package com.example.nacho.proj1105.modelos;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;

public class Cliente {
    private String rut;
    private String nombre;
    private String apellido;
    private Date fechanac;
    private int credito;
    private ArrayList<Servicio> servicios = new ArrayList<Servicio>();

    public Cliente(String rut, String nombre, String apellido, Date fechanac) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechanac = fechanac;
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Date getFechanac() {
        return fechanac;
    }

    public int getCredito() {
        return credito;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechanac(Date fechanac) {
        this.fechanac = fechanac;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }

    public boolean addServicio(Servicio servicio) {
        return this.servicios.add(servicio);
    }
    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public boolean delServicio(int codigo) {
        int x;
        for (x = 0; x < this.servicios.size(); ++x){
            if (servicios.get(x).getCodigo() == codigo){
                servicios.remove(x);
                return true;
            }
        }

        return false;
    }
    @Override
    public String toString() {
        return rut;
    }
}
