package co.edu.unab.proyectomoviles.basurapp.view.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.model.bd.network.CallBackFirebase;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Camion;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Localizacion;
import co.edu.unab.proyectomoviles.basurapp.model.repository.CamionRepository;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int cantidad;
    private double latiCerca = 0;
    private double longiCerca = 0;
    private static double latiCamion = 0.0, longiCamion = 0.0;
    private static double latiCliente = 0.0, longiCliente = 0.0;
    private CamionRepository camionRepositorio;
    private static Camion camion;
    private Localizacion localizacion;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    private int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle datosB = getIntent().getExtras();
        final String placaB = datosB.getString("placa");
        Log.d("TAG", "placa: " + placaB);
        this.inicializar();

        //Log.d("prueba","latitud: "+camion.getPlaca());

        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //verifica si tenemos consedido el permiso de ubicacion y esta activa el gps
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        new CountDownTimer(864000 * 1000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {

                camionRepositorio.traerCoordenadas(placaB, new CallBackFirebase<Camion>() {
                    @Override
                    public void correcto(Camion respuesta) {
                        camion = respuesta;
                        Log.d("TAG", "Document: " + camion.getUbicacion().getLatitude());
                        latiCamion = camion.getUbicacion().getLatitude();
                        longiCamion = camion.getUbicacion().getLongitude();

                    }
                });

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

        new CountDownTimer(3000 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                agregarUbicacion();
                cantidad++;
            }

            @Override
            public void onFinish() {

            }

        }.start();

    }

    public void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(MapActivity.this);

        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (gpsEnabled != true) {
                    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(settingsIntent);
                }
            }
        }.start();

        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        latiCliente = localizacion.latitud;
        longiCliente = localizacion.longitud;

    }

    public void agregarUbicacion() {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(latiCliente, longiCliente)).title("Mi Ubicación :"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latiCamion, longiCamion)).title("Camion :" + cantidad));
        verificarCercania(latiCamion, longiCamion, latiCliente, longiCliente);
        if (cantidad <= 1) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latiCamion, longiCamion), 17));
        }

    }

    public void verificarCercania(double lati1, double longi1, double lati2, double longi2) {
        lati1 = verificarNegativo(lati1);
        longi1 = verificarNegativo(longi1);
        lati2 = verificarNegativo(lati2);
        longi2 = verificarNegativo(longi2);

        latiCerca = lati1 - lati2;
        longiCerca = longi1 - longi2;
        //  Log.i("POSITIVOS","lats y longs"+lati1+lati2+longi1+longi2);
        latiCerca = verificarNegativo(latiCerca);
        longiCerca = verificarNegativo(longiCerca);

        if (latiCerca <= 0.00109653 && longiCerca <= 0.00138402) {
            //enviar notificacion

            if (cont == 0) {
                //Log.i("NOTIFICACION", "enviar notificacion");
                enviarNotificacion();
                enviarNotificacionChannel();
            }

        }

    }

    public double verificarNegativo(double num) {
        if (num < 0.0) {
            num = num * (-1);
        }
        return num;
    }

    public void enviarNotificacion() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.camion_basura);
        builder.setContentTitle("BasurApp");
        builder.setContentText("Apurate, el camion de basura está cerca");
        builder.setColor(Color.GREEN);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setLights(Color.GREEN, 1000, 1000);
        builder.setVibrate(new long[]{1000, 1000, 1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
        cont++;

    }

    public void enviarNotificacionChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
            builder.setDefaults(Notification.DEFAULT_SOUND);
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }
}


