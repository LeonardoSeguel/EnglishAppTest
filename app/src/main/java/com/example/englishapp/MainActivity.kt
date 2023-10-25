package com.example.englishapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException

class MainActivity : AppCompatActivity() {

    // Declara una variable TextView para mostrar texto desde un archivo
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa la variable TextView
        textView = findViewById(R.id.contentTextView)

        // Busca el boton en el diseño y fija un listener de click
        val button: Button = findViewById(R.id.openFileButton)
        button.setOnClickListener {
            // Crea un Intent para abrir el selector de archivos
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                // Se asegura de que el archivo se puede abrir
                addCategory(Intent.CATEGORY_OPENABLE)
                // Fija el tipo de archivo (.txt)
                type = "text/plain"
            }
            // Inicia la actividad del selector de archivos con un código de solicitud
            startActivityForResult(intent, 1)
        }
    }
    // Esta función se activa cuando la actividad del selector de archivos devuelve un resultado
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Comprueba si el código de solicitud coincide y el resultado es OK
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Obtiene la Uri del archivo seleccionado
            data?.data?.also { uri ->
                // Carga el texto del archivo seleccionado a través de su Uri
                loadTextFromFile(uri)
            }
        }
    }

    // Función para leer texto de un archivo especificado por una Uri
    private fun loadTextFromFile(uri: Uri) {
        try {
            // Abre un InputStream para leer el archivo
            val inputStream = contentResolver.openInputStream(uri)
            // Crea un BufferedReader para leer texto desde el InputStream
            val reader = BufferedReader(inputStream!!.reader())
            // Lee tod0 el contenido y lo establece en el TextView
            textView.text = reader.readText()
            // Cierra el lector
            reader.close()
        } catch (e: IOException) {
            // Maneja las excepciones
            e.printStackTrace()
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show()
        }
    }
}
