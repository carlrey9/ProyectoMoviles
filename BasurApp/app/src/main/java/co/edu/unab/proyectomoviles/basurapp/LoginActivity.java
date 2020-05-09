package co.edu.unab.proyectomoviles.basurapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnIngresar ;
    private Button btnRegistrarse;
    private Button btnOmitir;
    private ArrayList<Cliente> clientes;
    //private SharedPreferences misPreferencias;
    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asociarElementos();

        iniciarSinLogin();

        irIniciarRegistro();

        iniciarLogin();



    }

    public void asociarElementos(){
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnIngresar = findViewById(R.id.btn_ingresar);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
        btnOmitir = findViewById(R.id.btn_omitir);
    }

    public void iniciarSinLogin(){
        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Postlogin.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(), "Bienvenido ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void irIniciarRegistro(){
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Registro.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(), "A registrarse ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void iniciarLogin(){
        /*
        misPreferencias = getSharedPreferences(getString(R.string.mis_datos), MODE_PRIVATE);

        boolean logueado = misPreferencias.getBoolean("logueado", false);
        if(logueado){
            Intent i = new Intent(LoginActivity.this,postlogin.class);
            startActivity(i);
            finish();
        }

         */
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestoreDB.collection("usuarios_cliente").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            clientes = new ArrayList<>();

                            for (QueryDocumentSnapshot documento: task.getResult()){
                                Cliente elCliente = documento.toObject(Cliente.class);
                                elCliente.setId(documento.getId());
                                clientes.add(elCliente);

                                if  (edtUsername.getText().toString() == elCliente.getUserName() && edtPassword.getText().toString() == elCliente.getPassword()){
                                    Toast.makeText(LoginActivity.this,"Bienbenido "+elCliente.getP_nombre(),Toast.LENGTH_LONG).show();
                                    Log.d("Listo","encontrado en base de datos");
                                }

                            }
                        }else{
                            Log.d("Error","no se conectó con la base de datos");
                        }
                    }
                });
            }
        });



    }


}
