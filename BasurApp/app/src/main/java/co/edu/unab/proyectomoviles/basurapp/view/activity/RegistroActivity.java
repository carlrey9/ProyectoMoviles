package co.edu.unab.proyectomoviles.basurapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.model.model.entity.Cliente;

public class RegistroActivity extends AppCompatActivity {
    private EditText edtNombreRegistro;
    private EditText edtApellidoRegistro;
    private EditText edtCelularRegistro;
    private EditText edtEdadRegistro;
    private EditText edtUserNameRegistro;
    private EditText edtPasswordRegistro;
    private Button btnRegistrarRegistro;

    private String nombre;
    private String apellido;
    private Double celular;
    private int edad;
    private String username;
    private String password;

    DatabaseReference dbrealtime;
    FirebaseAuth firebaseAuth;
    private String id;
    private Map <String, Object> map;


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

        dbrealtime = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        map = new HashMap<>();

    }

    public void clickRegistrar(){

        btnRegistrarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = edtNombreRegistro.getText().toString();
                apellido = edtApellidoRegistro.getText().toString();
                celular = Double.parseDouble(edtCelularRegistro.getText().toString());
                edad = Integer.parseInt(edtEdadRegistro.getText().toString());
                username = edtUserNameRegistro.getText().toString();
                password = edtPasswordRegistro.getText().toString();

                //condicion de llenar campos
                if(nombre.isEmpty() ||
                        apellido.isEmpty() ||
                        String.valueOf(celular).isEmpty() ||
                        String.valueOf(edad).isEmpty()||
                        username.isEmpty() ||
                        password.isEmpty())
                {
                    Toast.makeText(RegistroActivity.this, "Debe completar todos los campos ", Toast.LENGTH_LONG).show();

                }else{
                    if (password.length() <6 ){
                        Toast.makeText(RegistroActivity.this,"La contraseña debe tener al menos 6 caracteres",Toast.LENGTH_LONG).show();
                    }else{
                        //registro en firestore en coleccion usuario_cliente
                        registroFiretore();

                        //registro en authentication con correo y contraseña
                        registroAuthentication();
                    }


                }

            }
        });





    }

    public void registroFiretore(){
        //registro en firestore en coleccion usuario_cliente
        Cliente nuevoCliente = new Cliente(nombre, apellido, celular, edad,
                username,password);

        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
        firestoreDB.collection("usuarios_cliente").add(nuevoCliente).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(RegistroActivity.this, "registrado", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    public void registroAuthentication(){
        firebaseAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){


                    Cliente nuevoCliente = new Cliente(nombre, apellido, celular, edad,
                            username,password);
                    map.put("nuevoCliente", nuevoCliente);
                    id = firebaseAuth.getCurrentUser().getUid();

                    dbrealtime.child("usuario_cliente").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                Toast.makeText(RegistroActivity.this,"Usuario registrado",Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }else{
                    Toast.makeText(RegistroActivity.this, "No se pudo realizar el registro del usuario",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

