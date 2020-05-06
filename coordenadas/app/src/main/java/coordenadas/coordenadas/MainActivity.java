package coordenadas.coordenadas;

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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public TextView
            textViewUbicacion,
            textViewDireccion,
            textViewHora,
            textViewFecha;
    private int
            dia,
            mes,
            anio,
            hora,
            min,
            seg;
    private String text;
    private Time today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asociarElementos();
        final Localizacion localizacion = new Localizacion();

        new CountDownTimer(86400 * 1000, 1000) {
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

        new CountDownTimer(86400 * 1000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {

                text = "Mi ubicacion actual es: " + "\n Lat = "
                        + localizacion.latitud + "\n Long = " + localizacion.longitud;
                textViewUbicacion.setText(text);
            }

            public void onFinish() {

            }
        }.start();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Log.d("prueba", "if: entro");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            //Log.d("prueba", "ifelse: entro");
            locationStart();
        }
        //Log.d("prueba", "JAK 1:" + (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));
        //Log.d("prueba", "JAK 2:" + (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED));
    }

    private void asociarElementos() {
        textViewDireccion = findViewById(R.id.textView_direccion);
        textViewFecha = findViewById(R.id.textView_fecha);
        textViewHora = findViewById(R.id.textView_hora);
        textViewUbicacion = findViewById(R.id.textView_ubicacion);
    }

    private void locationStart() {
        //Log.d("prueba", "locationStart");
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);

        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Log.d("prueba", "locationStart: " + gpsEnabled);
            }

            public void onFinish() {
                if (gpsEnabled != true) {
                    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(settingsIntent);
                }
            }
        }.start();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Log.d("prueba", "no: " + gpsEnabled);
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
                locationStart();                                                            //  estaba comentado todo el metodo
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
                        loc.getLatitude(), loc.getLongitude(), 1);                   // estaba comentado todo el metodo
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    textViewDireccion.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
