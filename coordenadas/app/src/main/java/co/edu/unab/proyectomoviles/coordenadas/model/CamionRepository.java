package co.edu.unab.proyectomoviles.coordenadas.model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import co.edu.unab.proyectomoviles.coordenadas.model.entity.Camion;
import co.edu.unab.proyectomoviles.coordenadas.model.db.network.CallBackFirebase;

public class CamionRepository {

    private FirebaseFirestore fireDB;

    public CamionRepository() {
        fireDB = FirebaseFirestore.getInstance();
    }

    public void nuevaUbicacion(final Context context, final Camion camion, final CallBackFirebase<Camion> callBackFirestore) {
        fireDB.collection("camiones").document(camion.getPlaca()).set(camion).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "ubicacion actualizada", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
