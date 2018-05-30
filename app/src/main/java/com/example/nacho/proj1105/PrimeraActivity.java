package com.example.nacho.proj1105;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.proj1105.basedatos.BaseDatos;
import com.example.nacho.proj1105.modelos.Cliente;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PrimeraActivity extends AppCompatActivity {
    
    //declarar variables
    private AutoCompleteTextView atv_Rut;
    private EditText et_Nombre;
    private EditText et_Apellido;
    private EditText et_FecNac;
    private EditText et_Credito;
    private Button btn_Guardar;
    private Button btn_Eliminar;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Cliente selec;
    private ArrayAdapter<Cliente> adapatdor;
    private ArrayList<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera);
        
        //inicializacion de los widgets
        atv_Rut = (AutoCompleteTextView) findViewById(R.id.atv_Rut);
        et_Nombre = (EditText) findViewById(R.id.et_Nombre);
        et_Apellido = (EditText) findViewById(R.id.et_Apellido);
        et_FecNac = (EditText) findViewById(R.id.et_FecNac);
        et_Credito = (EditText) findViewById(R.id.et_Credito);
        btn_Guardar = (Button) findViewById(R.id.btn_Guardar);
        btn_Eliminar = (Button) findViewById(R.id.btn_Eliminar);
        
        //setear el boton guardar como disponible y el eliminar como no disponible al abrir la aplicacion
        btn_Guardar.setEnabled(true);
        btn_Eliminar.setEnabled(false);
        
        //setear el boton guardar como disponible y el eliminar como no disponible al escribir algo en el rut
        atv_Rut.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                btn_Guardar.setEnabled(true);
                btn_Eliminar.setEnabled(false);
                return false;
            }
        });
        
        //actualizar el array list del auto complete al hacer fijar el cursor en el rut
        atv_Rut.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                adapatdor = new ArrayAdapter<Cliente>(PrimeraActivity.this, android.R.layout.simple_list_item_1,BaseDatos.misClientes);
                atv_Rut.setAdapter(adapatdor);
                atv_Rut.setThreshold(1);
                
                //llenar los datos y setear el boton guardar como no disponible y el eliminar como disponible al seleccionar un rut del autocomplete
                atv_Rut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selec = (Cliente) atv_Rut.getAdapter().getItem((int) id);
                        et_Nombre.setText(selec.getNombre());
                        et_Apellido.setText(selec.getApellido());
                        et_FecNac.setText(sdf.format(selec.getFechanac()));
                        btn_Guardar.setEnabled(false);
                        btn_Eliminar.setEnabled(true);
                    }
                });
            }
        });

    }
    
    protected void onClic (View v) {
        //boton guardar
        if (v.getId() == R.id.btn_Guardar) {
            
            //verificar que los datos esten ingresados y si no setear un error
            boolean errores = false;
            if (atv_Rut.getText().toString().isEmpty()) {
                atv_Rut.setError("Debe ingresar Rut");
                errores = true;
            }
            if (et_Nombre.getText().toString().isEmpty()) {
                et_Nombre.setError("Debe ingresar Nombre");
                errores = true;
            }
            if (et_Apellido.getText().toString().isEmpty()) {
                et_Apellido.setError("Debe ingresar Apellido");
                errores = true;
            }
            if (et_FecNac.getText().toString().isEmpty()) {
                et_FecNac.setError("Debe ingresar Fecha de Nacimiento");
                errores = true;
            }
            
            //si no hay ningun error guardar un cliente en el ArrayList de la clase BaseDatos
            if (!errores) {
                try {
                    BaseDatos.guardarCliente(atv_Rut.getText().toString(), et_Nombre.getText().toString(), et_Apellido.getText().toString(), et_FecNac.getText().toString());
                } catch (ParseException e) {
                    et_FecNac.setError("Error en formato de fecha");
                }
                Toast.makeText(this, "Cliente Guardado", Toast.LENGTH_SHORT).show();

            }
            
        //boton eliminar
        } else if (v.getId() == R.id.btn_Eliminar) {

            //eliminar del ArrayList de BaseDatos co el rut del Autocomplete de mas arriba
            BaseDatos.borrarCliente(selec.getRut());
            Log.d("TAG_", selec.getRut());

        //boton servicios
        } else if (v.getId() == R.id.btn_Servicios) {
            
            //esto lo hice para comprobar si me eliminaba y guardaba los clientes pero aqui deveria cambiarte a la otra activity
            for (Cliente cli : BaseDatos.misClientes) {
                Log.d("TAG_", cli.getRut());
            }
        }
    }
}
