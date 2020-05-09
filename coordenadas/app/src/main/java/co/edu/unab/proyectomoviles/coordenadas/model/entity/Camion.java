package co.edu.unab.proyectomoviles.coordenadas.model.entity;

import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

public class Camion {

    private int capacidad_tn;
    private String marca;
    private String modelo;
    private String placa;
    private int serial;
    //punto geografico > geopoint ↓↓↓

    public GeoPoint ubicacion;

    public Camion(int capacidad_tn, String marca, String modelo, String placa, int serial, GeoPoint ubicacion) {
        this.capacidad_tn = capacidad_tn;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.serial = serial;
        this.ubicacion = ubicacion;
    }

    public int getCapacidad_tn() {
        return capacidad_tn;
    }

    public void setCapacidad_tn(int capacidad_tn) {
        this.capacidad_tn = capacidad_tn;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public GeoPoint getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(GeoPoint ubicacion) {
        this.ubicacion = ubicacion;
    }
}
