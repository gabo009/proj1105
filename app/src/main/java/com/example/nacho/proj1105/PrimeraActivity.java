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

    private AutoCompleteTextView atv_Rut;
    private EditText et_Nombre;
    private EditText et_Apellido;
    private EditText et_FecNac;
    private EditText et_Credito;
    private Button btn_Guardar;
    private Button btn_Eliminar;
    //private Button btn_Servicios;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Cliente selec;
    private ArrayAdapter<Cliente> adapatdor;
    private ArrayList<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera);

        atv_Rut = (AutoCompleteTextView) findViewById(R.id.atv_Rut);
        et_Nombre = (EditText) findViewById(R.id.et_Nombre);
        et_Apellido = (EditText) findViewById(R.id.et_Apellido);
        et_FecNac = (EditText) findViewById(R.id.et_FecNac);
        et_Credito = (EditText) findViewById(R.id.et_Credito);
        btn_Guardar = (Button) findViewById(R.id.btn_Guardar);
        btn_Eliminar = (Button) findViewById(R.id.btn_Eliminar);

        btn_Guardar.setEnabled(true);
        btn_Eliminar.setEnabled(false);

        atv_Rut.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                btn_Guardar.setEnabled(true);
                btn_Eliminar.setEnabled(false);
                return false;
            }
        });

        atv_Rut.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                adapatdor = new ArrayAdapter<Cliente>(PrimeraActivity.this, android.R.layout.simple_list_item_1,BaseDatos.misClientes);
                atv_Rut.setAdapter(adapatdor);
                atv_Rut.setThreshold(1);
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

        if (v.getId() == R.id.btn_Guardar) {
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
            if (!errores) {
                try {
                    BaseDatos.guardarCliente(atv_Rut.getText().toString(), et_Nombre.getText().toString(), et_Apellido.getText().toString(), et_FecNac.getText().toString());
                } catch (ParseException e) {
                    et_FecNac.setError("Error en formato de fecha");
                }
                Toast.makeText(this, "Cliente Guardado", Toast.LENGTH_SHORT).show();

            }
        } else if (v.getId() == R.id.btn_Eliminar) {

            BaseDatos.borrarCliente(selec.getRut());
            Log.d("TAG_", selec.getRut());


        } else if (v.getId() == R.id.btn_Servicios) {
            for (Cliente cli : BaseDatos.misClientes) {
                Log.d("TAG_", cli.getRut());
            }
        }
    }
}