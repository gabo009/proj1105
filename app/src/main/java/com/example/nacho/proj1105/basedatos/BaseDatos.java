package com.example.nacho.proj1105.basedatos;

import com.example.nacho.proj1105.modelos.Cliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BaseDatos {

    public static ArrayList<Cliente> misClientes = new ArrayList<>();

    public static Cliente buscarClienteporRut(String rut){
        for (Cliente cli: misClientes){
            if (cli.getRut().equals(rut)){
                return cli;
            }
        }
        return null;
    }
    public static boolean borrarCliente(String rut) {
        int x;
        for (x = 0; x < misClientes.size(); ++x){
            if (misClientes.get(x).getRut().equals(rut)){
                misClientes.remove(x);
                return true;
            }
        }

        return false;
    }
    public static boolean guardarCliente(String rut, String nombre, String apellido, Date fechanac){
        return misClientes.add(new Cliente(rut, nombre, apellido, fechanac));
    }
    public static boolean guardarCliente(String rut, String nombre, String apellido, String fechanac) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = new Date();
        fecha.setTime(sdf.parse(fechanac).getTime());
        return misClientes.add(new Cliente(rut, nombre, apellido, fecha));
    }
}
