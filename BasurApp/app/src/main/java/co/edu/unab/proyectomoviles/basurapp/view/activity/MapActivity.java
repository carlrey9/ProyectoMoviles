package co.edu.unab.proyectomoviles.basurapp.view.activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import co.edu.unab.proyectomoviles.basurapp.CamionRepository;
import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.model.db.network.CallBackFirebase;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Camion;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int cantidad, aux;
    private ArrayList<LatLng> puntos = new ArrayList<>();
    private ArrayList<LatLng> markadores;
    private static double lati = 0.0, longi = 0.0;
    private CamionRepository camionRepositorio;
    private static Camion camion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        inicializar();

        //Log.d("prueba","latitud: "+camion.getPlaca());

        new CountDownTimer(864000 * 1000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {

                camionRepositorio.traerCoordenadas(new CallBackFirebase<Camion>() {
                    @Override
                    public void correcto(Camion respuesta) {
                        camion = respuesta;
                        Log.d("TAG", "Document: " + camion.getUbicacion().getLatitude());
                        lati = camion.getUbicacion().getLatitude();
                        longi = camion.getUbicacion().getLongitude();
                        puntos.add(new LatLng(lati, longi));
                    }
                });

                aux++;  //variable para agregar marcadores

            }

            @Override
            public void onFinish() {

            }

        }.start();
        //Log.d("prueba","latitud: "+camion.getUbicacion().getLatitude());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void inicializar() {

        camionRepositorio = new CamionRepository();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        new CountDownTimer(86400 * 1000, 5000) {

            @Override
            public void onTick(long millisUntilFinished) {

                LatLng bucaramanga = new LatLng(lati, longi);

                mMap.addMarker(new MarkerOptions().position(bucaramanga).title("ubicacion :" + cantidad));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bucaramanga, 18));

                if (cantidad < puntos.size()) {
                    //poner un wait para agregar otro punto a la lista y comenzar a trazar la ruta
                    //mMap.addPolyline(new PolylineOptions().add(puntos.get(cantidad),puntos.get(cantidad+1) ).width(5).color(Color.RED));

                }

                Log.i("sucess", "cantidad " + cantidad);

                cantidad++;

            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}
