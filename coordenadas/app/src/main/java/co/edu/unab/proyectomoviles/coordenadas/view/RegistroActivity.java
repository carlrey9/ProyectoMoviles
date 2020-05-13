package co.edu.unab.proyectomoviles.coordenadas.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.unab.proyectomoviles.coordenadas.R;
import co.edu.unab.proyectomoviles.coordenadas.model.db.local.BaseDatos;
import co.edu.unab.proyectomoviles.coordenadas.model.db.local.CamionDAO;
import co.edu.unab.proyectomoviles.coordenadas.model.entity.Camion;

public class RegistroActivity extends AppCompatActivity {

    private EditText
            editTextCapcidad,
            editTextMarca,
            editTextModelo,
            editTextPlaca,
            editTextSerial;
    private Button btnRegistrar;
    private Camion camion;
    public static boolean bandera = false;
    private CamionDAO camionDAO;
    private BaseDatos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        instanciar();
        inicializar();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camion = new Camion(Integer.parseInt(editTextCapcidad.getText().toString()), //se llena el obj mediante el constructor
                        editTextMarca.getText().toString(),
                        editTextModelo.getText().toString(),
                        editTextPlaca.getText().toString(),
                        Integer.parseInt(editTextSerial.getText().toString()));
                camionDAO.agregar(camion); //se agrega a la base de datos local

                startActivity(new Intent(RegistroActivity.this, MainActivity.class));
                finish(); //termina la actividad
            }
        });

    }

    private void instanciar() {
        editTextCapcidad = findViewById(R.id.textView_capacidad);
        editTextMarca = findViewById(R.id.textView_marca);
        editTextModelo = findViewById(R.id.textView_modelo);
        editTextPlaca = findViewById(R.id.textView_placa);
        editTextSerial = findViewById(R.id.textView_serial);
        btnRegistrar = findViewById(R.id.btnRegistrar);
    }

    private void inicializar(){
        db = BaseDatos.obtenerInstancia(this);
        camionDAO = db.camionDAO();
    }
}
