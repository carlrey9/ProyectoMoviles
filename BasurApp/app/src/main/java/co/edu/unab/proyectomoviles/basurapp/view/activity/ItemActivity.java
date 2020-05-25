package co.edu.unab.proyectomoviles.basurapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import co.edu.unab.proyectomoviles.basurapp.R;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
    }

    public void onClick(View view) {

        Intent miIntent = null;

        switch (view.getId()){
            case R.id.btnRegresar:
                miIntent = new Intent(ItemActivity.this, MainActivity.class);
                break;
            case R.id.btnSiguiente:
                miIntent = new Intent(ItemActivity.this, RegistroActivity.class);
                break;
        }
    }
}
