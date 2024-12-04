package com.example.sharedprefsfileexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText campoNombre, campoCorreo, campoMensaje;
    private Button botonGuardar, botonCargar;
    private ImageView imagen;
    private SharedPreferences sharedPreferences;

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY_NOMBRE = "nombre";
    private static final String FILE_NAME = "datos_usuario.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoNombre = findViewById(R.id.campo_nombre);
        campoCorreo = findViewById(R.id.campo_correo);
        campoMensaje = findViewById(R.id.campo_mensaje);
        botonGuardar = findViewById(R.id.boton_guardar);
        botonCargar = findViewById(R.id.boton_cargar);
        imagen = findViewById(R.id.imagen);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        botonGuardar.setOnClickListener(v -> guardarDatos());
        botonCargar.setOnClickListener(v -> cargarDatos());
    }

    private void guardarDatos() {
        String nombre = campoNombre.getText().toString().trim();
        String correo = campoCorreo.getText().toString().trim();
        String mensaje = campoMensaje.getText().toString().trim();

        // Validación de campos vacíos (implementación añadida aquí)
        if (nombre.isEmpty() || correo.isEmpty() || mensaje.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar nombre con SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NOMBRE, nombre);
        editor.apply();

        // Guardar todos los datos con FileOutputStream
        String datos = "Nombre: " + nombre + "\nCorreo: " + correo + "\nMensaje: " + mensaje;
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(datos.getBytes());
            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, VerDatosActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatos() {
        // Cargar nombre con SharedPreferences
        String nombre = sharedPreferences.getString(KEY_NOMBRE, "No disponible");
        campoNombre.setText(nombre);

        // Cargar todos los datos del archivo
        StringBuilder datosArchivo = new StringBuilder();
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            int character;
            while ((character = fis.read()) != -1) {
                datosArchivo.append((char) character);
            }
            Toast.makeText(this, "Datos cargados correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
        }

        campoMensaje.setText(datosArchivo.toString());
    }
}
