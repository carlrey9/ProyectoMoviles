package co.edu.unab.proyectomoviles.basurapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import co.edu.unab.proyectomoviles.basurapp.model.db.network.CallBackFirebase;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Camion;

public class CamionRepository {

    private FirebaseFirestore fireDB;

    public CamionRepository() {
        fireDB = FirebaseFirestore.getInstance();
    }

    public void nuevaUbicacion(final Context context, final Camion camion, final CallBackFirebase<Camion> callBackFirestore) {
        fireDB.collection("camiones")
                .document(camion.getPlaca())
                .set(camion)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "ubicacion actualizada", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void traerCoordenadas(final CallBackFirebase<Camion> callBackFirebase) {

        fireDB.collection("camiones")
                .document("lol")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documento = task.getResult();
                            if (documento.exists()) {
                                Camion camion = documento.toObject(Camion.class);
                                callBackFirebase.correcto(camion);
                            }
                        }
                    }
                });
    }

}
