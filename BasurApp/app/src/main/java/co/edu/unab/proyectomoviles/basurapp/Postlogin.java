package co.edu.unab.proyectomoviles.basurapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Postlogin extends AppCompatActivity {

    SharedPreferences misPreferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postlogin);

        verificarLogin();
    }

    public void verificarLogin(){
        /* misPreferencias = getSharedPreferences(getString(R.string.mis_datos), MODE_PRIVATE);
        Boolean logueado =misPreferencias.getBoolean("logueado", false);
        if (!logueado){
            Intent i = new Intent(postlogin.this, LoginActivity.class);
            startActivity(i);
            finish();
        }

         */
    }
}
