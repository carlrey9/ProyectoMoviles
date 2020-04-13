package co.edu.unab.proyectomoviles.basurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnIngresar ;
    Button btnRegistrarse;
    Button btnOmitir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asociarElementos();

        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), postlogin.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(), "Bienvenido Pendejo", Toast.LENGTH_LONG).show();
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), registro.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(), "A registrarse weee", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void asociarElementos(){
        btnIngresar = findViewById(R.id.btn_ingresar);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
        btnOmitir = findViewById(R.id.btn_omitir);
    }
}
