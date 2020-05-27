package co.edu.unab.proyectomoviles.basurapp.model.repository;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
 public class ClienteRepository {
    private String parameterusername;

    FirebaseFirestore dbFirestore;

    public ClienteRepository(Context contexto) {
        this.dbFirestore = dbFirestore;
    }



}

