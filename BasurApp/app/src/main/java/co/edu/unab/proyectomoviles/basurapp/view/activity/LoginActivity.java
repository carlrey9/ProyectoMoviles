package co.edu.unab.proyectomoviles.basurapp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Cliente;
import co.edu.unab.proyectomoviles.basurapp.model.repository.ClienteRepository;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnIngresar ;
    private Button btnRegistrarse;
    private Button btnOmitir;
    private List<Cliente> clientes;
    private SharedPreferences misPreferencias;
    private FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

    private FirebaseAuth firebaseAuth;

    private ClienteRepository clienteRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        misPreferencias = getSharedPreferences(getString(R.string.mis_datos), MODE_PRIVATE);
        boolean logueado = misPreferencias.getBoolean("logueado", false);

        if(logueado){
            Intent i = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(i);
            finish();
        }

        asociarElementos();

        verificarLogueo();

        iniciarSinLogin();

        irIniciarRegistro();

        iniciarLogin(logueado);

        clienteRepository = new ClienteRepository(LoginActivity.this);



    }



    public void asociarElementos(){
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnIngresar = findViewById(R.id.btn_ingresar);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
        btnOmitir = findViewById(R.id.btn_omitir);

        firebaseAuth = FirebaseAuth.getInstance();
    }



    public void iniciarSinLogin(){
        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(), "Bienvenido ", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void irIniciarRegistro(){
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "A registrarse ", Toast.LENGTH_LONG).show();
                Intent in = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(in);

            }
        });
    }

    public void verificarLogueo(){


    }

    public void iniciarLogin(final boolean logueado){


        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( edtUsername.getText().toString().isEmpty()  || edtPassword.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Debe completar los campos",Toast.LENGTH_LONG).show();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(edtUsername.getText().toString(), edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                                SharedPreferences.Editor miEditor = misPreferencias.edit();
                                miEditor.putBoolean("logueado", true);
                                miEditor.apply();

                                finish();
                            } else
                                Toast.makeText(getApplicationContext(), "verifique sus datos y vuelva a iniciar sesion", Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
        });
        /*
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clienteRepository.reconocerLogin(new CallBackFirestore<List<Cliente>>() {
                    @Override
                    public void correcto(List<Cliente> respuesta) {
                        clientes = respuesta;

                    }
                });

            }
        });

         */
    }




}
