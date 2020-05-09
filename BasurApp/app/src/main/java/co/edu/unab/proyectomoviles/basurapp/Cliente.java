package co.edu.unab.proyectomoviles.basurapp;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;

public class Cliente implements Serializable {


    public String id;
    private String p_nombre;
    private String p_apellido;
    private double celular;
    private int edad;
    private String userName;
    private String password;


    //private geopoint posicion


    public Cliente( String p_nombre, String p_apellido, double celular, int edad, String userName, String password) {
        this.id = "";
        this.p_nombre = p_nombre;
        this.p_apellido = p_apellido;
        this.celular = celular;
        this.edad = edad;
        this.userName = userName;
        this.password = password;
    }

    //constructore vacio y con parametros
    public Cliente() {
    }



    //getters and setters


    public String getP_nombre() {
        return p_nombre;
    }

    public void setP_nombre(String p_nombre) {
        this.p_nombre = p_nombre;
    }

    public String getP_apellido() {
        return p_apellido;
    }

    public void setP_apellido(String p_apellido) {
        this.p_apellido = p_apellido;
    }

    public double getCelular() {
        return celular;
    }

    public void setCelular(double celular) {
        this.celular = celular;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
