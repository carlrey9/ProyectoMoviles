package co.edu.unab.proyectomoviles.coordenadas.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.GeoPoint;

@Entity
public class Camion {

    private int capacidad_tn;
    private String marca;
    private String modelo;
    //indica llave primaria para Rooms ↓↓↓
    @PrimaryKey
    @NonNull
    private String placa;
    private int serial;
    //punto geografico > geopoint ↓↓↓
    @Ignore
    public GeoPoint ubicacion;

    public Camion(int capacidad_tn, String marca, String modelo, String placa, int serial) {
        this.capacidad_tn = capacidad_tn;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.serial = serial;
    }
    public Camion(){

    }

    public String getPlaca() {
        return placa;
    }

    public void setUbicacion(GeoPoint ubicacion) {
        this.ubicacion = ubicacion;
    }
}
