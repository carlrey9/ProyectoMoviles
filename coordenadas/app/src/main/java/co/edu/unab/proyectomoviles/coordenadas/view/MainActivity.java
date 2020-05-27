package co.edu.unab.proyectomoviles.coordenadas.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import co.edu.unab.proyectomoviles.coordenadas.Localizacion;
import co.edu.unab.proyectomoviles.coordenadas.R;
import co.edu.unab.proyectomoviles.coordenadas.model.db.local.BaseDatos;
import co.edu.unab.proyectomoviles.coordenadas.model.db.local.CamionDAO;
import co.edu.unab.proyectomoviles.coordenadas.model.entity.Camion;
import co.edu.unab.proyectomoviles.coordenadas.model.db.network.CallBackFirebase;
import co.edu.unab.proyectomoviles.coordenadas.model.CamionRepository;

public class MainActivity extends AppCompatActivity {

    public TextView
            textViewUbicacion,
            textViewDireccion,
            textViewHora,
            textViewFecha;
    private int
            dia, mes, anio,
            hora, min, seg;
    private Time today;
    private Localizacion localizacion;
    private CamionRepository camionRepositorio;
    private Camion camion;
    private CamionDAO camionDAO;
    private BaseDatos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instanciar();
        inicializar();

        camion = camionDAO.obtener();

        new CountDownTimer(86400 * 1000, 1000) { //contador cada segundo da un ciclo
            @Override
            public void onTick(long millisUntilFinished) {
                today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                dia = today.monthDay;
                mes = today.month;
                anio = today.year;
                hora = today.hour;
                min = today.minute;
                seg = today.second;
                mes++;
                textViewFecha.setText("mes : " + mes + "\n " + "dia  : " + dia + "\n " + "año : " + anio);
                textViewHora.setText("hora : " + hora + ":" + min + ":" + seg);
            }

            public void onFinish() {

            }
        }.start();

        new CountDownTimer(86400 * 1000, 5000) {//contador cada 5 segundos da un ciclo
            @Override
            public void onTick(long millisUntilFinished) {
                camion.setUbicacion(new GeoPoint(localizacion.latitud, localizacion.longitud));//agrega coordenadas nuevas nueva
                textViewUbicacion.setText("Mi ubicacion actual es: " + "\n Lat = "
                        + localizacion.latitud + "\n Long = " + localizacion.longitud);
                camionRepositorio.nuevaUbicacion(MainActivity.this, camion, new CallBackFirebase<Camion>() {
                    @Override
                    public void correcto(Camion respuesta) { }
                });

            }

            public void onFinish() {

            }
        }.start();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //verifica si tenemos consedido el permiso de ubicacion y esta activo el gps
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

    }
    private void locationStart() { //metodo para activar el gps
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);

        final boolean gpsHabilitado = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (gpsHabilitado != true) startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }.start();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        textViewUbicacion.setText("Localizacion agregada");
        textViewDireccion.setText("vacio");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    textViewDireccion.setText("Mi direccion es: \n" + DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void instanciar() {
        textViewDireccion = findViewById(R.id.textView_direccion);
        textViewFecha = findViewById(R.id.textView_fecha);
        textViewHora = findViewById(R.id.textView_hora);
        textViewUbicacion = findViewById(R.id.textView_ubicacion);
    }

    private void inicializar() {
        localizacion = new Localizacion();
        camionRepositorio = new CamionRepository();
        db = BaseDatos.obtenerInstancia(this);
        camionDAO = db.camionDAO();
    }

}
