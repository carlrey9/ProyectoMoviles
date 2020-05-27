package co.edu.unab.proyectomoviles.basurapp.view.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;
import com.onesignal.OneSignal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.model.bd.network.CallBackFirebase;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Camion;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Localizacion;
import co.edu.unab.proyectomoviles.basurapp.model.model.entity.Cliente;
import co.edu.unab.proyectomoviles.basurapp.model.repository.CamionRepository;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int cantidad, aux;
    private double latiCerca=0;
    private double longiCerca=0;
    private ArrayList<LatLng> puntos = new ArrayList<>();
    private ArrayList<LatLng> markadores;
    private static double lati = 0.0, longi = 0.0;
    private static double latiCliente = 0.0, longiCliente = 0.0;

    private CamionRepository camionRepositorio;
    private static Camion camion;
    private static Cliente cliente;
    private Marker marcador;
    private Localizacion localizacion;

    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID=0;
    private int cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle datosB = getIntent().getExtras();
        final String placaB = datosB.getString("placa");
        Log.d("TAG", "placa: " + placaB);
        this.inicializar();

        //Log.d("prueba","latitud: "+camion.getPlaca());

        new CountDownTimer(864000 * 1000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {

                camionRepositorio.traerCoordenadas(placaB, new CallBackFirebase<Camion>() {
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

                mMap.addMarker(new MarkerOptions().position(bucaramanga).title("ubicacion :" + cantidad)
                        .icon(bitmapDescriptorFromVector(getApplicationContext(),
                                R.drawable.ic_reciclarrrr)));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bucaramanga, 18));

                if (cantidad < puntos.size()) {
                    //poner un wait para agregar otro punto a la lista y comenzar a trazar la ruta
                    //mMap.addPolyline(new PolylineOptions().add(puntos.get(cantidad),puntos.get(cantidad+1) ).width(5).color(Color.RED));

                }

                Log.i("sucess", "cantidad " + cantidad);

                cantidad++;

            }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.
                getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }

            public void agregarUbicacionCliente(){
                if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //verifica si tenemos consedido el permiso de ubicacion y esta activa el gps
                    ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                } else {
                    locationStart();
                }


            }

            public void locationStart(){
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
                latiCliente=localizacion.latitud;
                longiCliente=localizacion.longitud;

                LatLng ubicacionCliente = new LatLng(latiCliente, longiCliente);
                mMap.addMarker(new MarkerOptions().position(ubicacionCliente).title("Mi Ubicación :"));



                /*textViewUbicacion.setText("Localizacion agregada");
                textViewDireccion.setText("vacio");

                 */

            }

            public void agregarUbicacionBucaramanga(){
                LatLng bucaramanga = new LatLng(lati, longi);
                mMap.addMarker(new MarkerOptions().position(bucaramanga).title("Camion :" + cantidad));
                verificarCercania(lati,longi,latiCliente,longiCliente);
                if (cantidad <= 1){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bucaramanga, 17));
                }

            }

            public void verificarCercania(double lati1, double longi1, double lati2,double longi2){
                lati1=verificarNegativo(lati1);
                longi1=verificarNegativo(longi1);
                lati2=verificarNegativo(lati2);
                longi2=verificarNegativo(longi2);

                latiCerca = lati1-lati2;
                longiCerca = longi1-longi2;

                Log.i("POSITIVOS","lats y longs"+lati1+lati2+longi1+longi2);

                latiCerca = verificarNegativo(latiCerca);
                longiCerca = verificarNegativo(longiCerca);

                if (latiCerca <= 0.00109653 && longiCerca <= 0.00138402){
                    //enviar notificacion

                    if (cont ==0){
                        Log.i("NOTIFICACION", "enviar notificacion");
                        enviarNotificacion();
                        enviarNotificacionChannel();
                    }

                }

            }

            public double verificarNegativo(double num){
                if (num <0.0){
                    num=num*(-1);
                }
                return num;
            }

            public void enviarNotificacion(){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
                builder.setSmallIcon(R.drawable.camion_basura);
                builder.setContentTitle("BasurApp");
                builder.setContentText("Apurate, el camion de basura está cerca");
                builder.setColor(Color.GREEN);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setLights(Color.GREEN,1000,1000);
                builder.setVibrate(new long[]{1000,1000,1000});
                builder.setDefaults(Notification.DEFAULT_SOUND);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
                cont++;

            }

            public void enviarNotificacionChannel(){
                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
                    builder.setDefaults(Notification.DEFAULT_SOUND);
                    CharSequence name = "Notificacion";
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(notificationChannel);

                }
            }

            @Override
            public void onFinish() {

            }

        }.start();

        }
}



