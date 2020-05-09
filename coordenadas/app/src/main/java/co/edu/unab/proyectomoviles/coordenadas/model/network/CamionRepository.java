package co.edu.unab.proyectomoviles.coordenadas.model.network;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import co.edu.unab.proyectomoviles.coordenadas.model.entity.Camion;

public class CamionRepository {

    private FirebaseFirestore fireDB;

    public CamionRepository() {
        fireDB = FirebaseFirestore.getInstance();
    }

    public void agregarFirestore(final Camion camion, final CallBackFirebase<Camion> callBackFirestore) {
        fireDB.collection("camiones").add(camion).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    callBackFirestore.correcto(camion);
                }
            }
        });
    }

}
