package co.edu.unab.proyectomoviles.basurapp.model.entity;

import com.google.firebase.firestore.GeoPoint;

public class Camion {

    private int capacidad_tn;
    private String marca;
    private String modelo;
    private String placa;
    private int serial;
    //punto geografico > geopoint ↓↓↓
    private GeoPoint ubicacion;

    public Camion(int capacidad_tn, String marca, String modelo, String placa, int serial) {
        this.capacidad_tn = capacidad_tn;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.serial = serial;
    }

    public Camion(int capacidad_tn, String marca, String modelo, String placa, int serial, GeoPoint ubicacion) {
        this.capacidad_tn = capacidad_tn;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.serial = serial;
        this.ubicacion = ubicacion;
    }

    public Camion(){

    }

    public String getPlaca() {
        return placa;
    }

    public GeoPoint getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(GeoPoint ubicacion) {
        this.ubicacion = ubicacion;
    }
}
