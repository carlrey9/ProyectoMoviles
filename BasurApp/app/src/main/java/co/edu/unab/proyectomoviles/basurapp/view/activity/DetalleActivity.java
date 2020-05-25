package co.edu.unab.proyectomoviles.basurapp.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Camion;

public class DetalleActivity extends AppCompatActivity {

    private TextView textViewplaca;
    private TextView textViewMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Bundle datosB = getIntent().getExtras();
        Camion prodObj = (Camion) datosB.getSerializable("objeto");

        this.asociarElementos();
        textViewplaca.setText(prodObj.getPlaca());
        textViewMarca.setText(prodObj.getMarca());

    }

    private void asociarElementos() {
        textViewplaca = findViewById(R.id.textView_placa);
        textViewMarca = findViewById(R.id.textView_marca);
    }

}
