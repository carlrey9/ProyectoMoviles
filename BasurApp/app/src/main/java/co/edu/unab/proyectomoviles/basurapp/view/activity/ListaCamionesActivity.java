package co.edu.unab.proyectomoviles.basurapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import co.edu.unab.proyectomoviles.basurapp.R;

public class ListaCamionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_camiones);
    }

    public void onClick(View view) {

        Intent miIntent = null;

        switch (view.getId()){
            case R.id.btnRegresar:
                miIntent = new Intent(ListaCamionesActivity.this, HomeActivity.class);
                break;
             case R.id
        }
        startActivity(miIntent);
    }
}
