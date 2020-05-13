package co.edu.unab.proyectomoviles.coordenadas.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.GeoPoint;

import co.edu.unab.proyectomoviles.coordenadas.R;
import co.edu.unab.proyectomoviles.coordenadas.model.CamionRepository;
import co.edu.unab.proyectomoviles.coordenadas.model.db.local.BaseDatos;
import co.edu.unab.proyectomoviles.coordenadas.model.db.local.CamionDAO;
import co.edu.unab.proyectomoviles.coordenadas.model.db.network.CallBackFirebase;
import co.edu.unab.proyectomoviles.coordenadas.model.entity.Camion;

public class RegistroActivity extends AppCompatActivity {

    private EditText
            editTextCapcidad,
            editTextMarca,
            editTextModelo,
            editTextPlaca,
            editTextSerial;
    private Button btnRegistrar;
    private CamionRepository camionRepositorio;
    private Camion camion;
    public static boolean bandera = false;
    private CamionDAO camionDAO;
    private BaseDatos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        instanciar();
        camionRepositorio = new CamionRepository();
        db = BaseDatos.obtenerInstancia(this);
        camionDAO = db.camionDAO();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                camion = new Camion(Integer.parseInt(editTextCapcidad.getText().toString()),
                        editTextMarca.getText().toString(),
                        editTextModelo.getText().toString(),
                        editTextPlaca.getText().toString(),
                        Integer.parseInt(editTextSerial.getText().toString()));
                Log.d("prueba", "camion:" + camion.getPlaca());
                camionDAO.agregar(camion);

                /*camionRepositorio.agregar(camion, new CallBackFirebase<Camion>() {
                    @Override
                    public void correcto(Camion respuesta) {

                    }
                });*/

                //if (!bandera) {
                    startActivity(new Intent(RegistroActivity.this, MainActivity.class));
                    finish();
                //} else Toast.makeText(getApplicationContext(), "no se pudo registrar, vuelva a intentarlo", Toast.LENGTH_LONG).show();
            }

        });

        //startActivity(new Intent(RegistroActivity.this, MainActivity.class));
        //finish();
    }

    private void instanciar() {
        editTextCapcidad = findViewById(R.id.textView_capacidad);
        editTextMarca = findViewById(R.id.textView_marca);
        editTextModelo = findViewById(R.id.textView_modelo);
        editTextPlaca = findViewById(R.id.textView_placa);
        editTextSerial = findViewById(R.id.textView_serial);
        btnRegistrar = findViewById(R.id.btnRegistrar);
    }
}
