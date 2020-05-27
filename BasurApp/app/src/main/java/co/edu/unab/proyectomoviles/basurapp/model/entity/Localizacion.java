package co.edu.unab.proyectomoviles.basurapp.model.entity;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import co.edu.unab.proyectomoviles.basurapp.view.activity.MapActivity;

public class Localizacion implements LocationListener {

    private MapActivity mapActivity;
    public static double
            latitud = 0.0,
            longitud = 3.2;
    public void setMainActivity(MapActivity mapActivity) {
        this.mapActivity = mapActivity;
    }

    @Override  // obtiene la latitud y longitud
    public void onLocationChanged (Location loc){
            /* Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
               debido a la deteccion de un cambio de ubicacion*/
        latitud = loc.getLatitude();
        longitud = loc.getLongitude();
        Log.d("prueba","gps:"+latitud);
        this.mapActivity.setLocation(loc);   //------>del metodo setLocation   //pone la direccion

    }

    @Override
    public void onProviderDisabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es desactivado

    }

    @Override
    public void onProviderEnabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es activado
        //mapActivity.textViewUbicacion.setText("el GPS esta activado");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

}
