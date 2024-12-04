package com.example.sharedprefsfileexample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.IOException;

public class VerDatosActivity extends AppCompatActivity {

    private TextView textoDatos;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY_NOMBRE = "nombre";
    private static final String FILE_NAME = "datos_usuario.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verdatosactivity);

        textoDatos = findViewById(R.id.texto_datos);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        mostrarDatos();
    }

    private void mostrarDatos() {
        String nombre = sharedPreferences.getString(KEY_NOMBRE, "No disponible");

        StringBuilder datosArchivo = new StringBuilder();
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            int character;
            while ((character = fis.read()) != -1) {
                datosArchivo.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al leer el archivo", Toast.LENGTH_SHORT).show();
        }

        String datosCompletos = "Nombre: " + nombre + "\n" + datosArchivo.toString();
        textoDatos.setText(datosCompletos);
    }
}
