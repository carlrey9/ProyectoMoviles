package co.edu.unab.proyectomoviles.coordenadas.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.edu.unab.proyectomoviles.coordenadas.R;
import co.edu.unab.proyectomoviles.coordenadas.model.db.local.BaseDatos;
import co.edu.unab.proyectomoviles.coordenadas.model.db.local.CamionDAO;
import co.edu.unab.proyectomoviles.coordenadas.model.entity.Camion;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPass;
    private FirebaseAuth firebaseAuth;
    private Camion camion;
    private CamionDAO camionDAO;
    private BaseDatos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        instanciar();
        inicializar();
        if (camion != null) startActivity(new Intent(getApplicationContext(), MainActivity.class)); //verifica si ya se realizo un registro

    }

    public void entrar(View v){
        firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "verifique sus datos y vuelva a iniciar sesion", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void instanciar() {
        editTextPass = findViewById(R.id.textView_password);
        editTextEmail = findViewById(R.id.textView_email);
    }

    private void inicializar() {
        db = BaseDatos.obtenerInstancia(this);
        camionDAO = db.camionDAO();
        camion = new Camion();
        camion = camionDAO.obtener();
        firebaseAuth = FirebaseAuth.getInstance();
    }

}
