package co.edu.unab.proyectomoviles.basurapp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import co.edu.unab.proyectomoviles.basurapp.R;

public class HomeActivity extends AppCompatActivity {

    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        asociarElementos();

        cerrarSesion();
    }

    public void asociarElementos(){
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);
    }

    public void cerrarSesion (){
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences misPreferencias = getSharedPreferences(getString(R.string.mis_datos), MODE_PRIVATE);
                SharedPreferences.Editor miEditor = misPreferencias.edit();
                miEditor.clear();
                miEditor.apply();
                Intent in = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        });

    }
}
