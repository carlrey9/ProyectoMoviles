package co.edu.unab.proyectomoviles.basurapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

public class Registro extends AppCompatActivity {

    private EditText edtNombreRegistro;
    private EditText edtApellidoRegistro;
    private EditText edtCelularRegistro;
    private EditText edtEdadRegistro;
    private EditText edtUserNameRegistro;
    private EditText edtPasswordRegistro;
    private Button btnRegistrarRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        asociarElementos();

        clickRegistrar();

    }

    public void asociarElementos(){
        edtNombreRegistro = findViewById(R.id.edt_nombre_registro);
        edtApellidoRegistro = findViewById(R.id.edt_apellido_registro);
        edtCelularRegistro = findViewById(R.id.edt_celular_registro);
        edtEdadRegistro = findViewById(R.id.edt_edad_registro);
        edtUserNameRegistro = findViewById(R.id.edt_crear_username);
        edtPasswordRegistro = findViewById(R.id.edt_crear_password);

        btnRegistrarRegistro = findViewById(R.id.btn_registrar_registro);
    }

    public void clickRegistrar(){

        btnRegistrarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNombreRegistro.getText().toString().isEmpty() ||
                        edtApellidoRegistro.getText().toString().isEmpty() ||
                        edtCelularRegistro.getText().toString().isEmpty() ||
                        edtEdadRegistro.getText().toString().isEmpty()||
                        edtUserNameRegistro.getText().toString().isEmpty() ||
                        edtPasswordRegistro.getText().toString().isEmpty())
                {
                    Toast.makeText(Registro.this, "Debe completar todos los campos ", Toast.LENGTH_LONG).show();

                }else{
                    Cliente nuevoCliente = new Cliente(edtNombreRegistro.getText().toString(),
                            edtApellidoRegistro.getText().toString(),
                            Double.parseDouble(edtCelularRegistro.getText().toString()),
                            Integer.parseInt(edtEdadRegistro.getText().toString()),
                            edtUserNameRegistro.getText().toString(),edtPasswordRegistro.getText().toString());

                    FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
                    firestoreDB.collection("usuarios_cliente").add(nuevoCliente).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(Registro.this, "registrado", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });


                }

            }
        });





    }
}
