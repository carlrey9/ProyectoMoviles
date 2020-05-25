package co.edu.unab.proyectomoviles.basurapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.model.bd.network.CallBackFirebase;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Camion;
import co.edu.unab.proyectomoviles.basurapp.model.repository.CamionRepository;
import co.edu.unab.proyectomoviles.basurapp.view.adapter.CamionAdapter;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CamionAdapter adapter;

    public List<Camion> camiones;
    private CamionRepository camionRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        camionRepositorio = new CamionRepository();
        camiones = new ArrayList<>();
        getDataFirestore();

        adapter = new CamionAdapter(camiones, new CamionAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Camion camion, int posicion) {
                Toast.makeText(getApplicationContext(), "tap en: " + camion.getPlaca(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, MapActivity.class).putExtra("placa", camion.getPlaca()));
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }

    private void getDataFirestore(){
        camionRepositorio.obtenerTodosFirestore(new CallBackFirebase<List<Camion>>() {
            @Override
            public void correcto(List<Camion> respuesta) {
                adapter.setCamiones(respuesta);
            }
        });
    }

}


