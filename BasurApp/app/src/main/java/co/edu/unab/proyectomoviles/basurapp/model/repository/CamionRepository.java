package co.edu.unab.proyectomoviles.basurapp.model.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.proyectomoviles.basurapp.model.bd.network.CallBackFirebase;

import co.edu.unab.proyectomoviles.basurapp.model.entity.Camion;

public class CamionRepository {

    private FirebaseFirestore fireDB;

    public CamionRepository() {
        fireDB = FirebaseFirestore.getInstance();
    }

    public void obtenerTodosFirestore(final CallBackFirebase<List<Camion>> callBackFirestore) {
        fireDB.collection("camiones").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Camion> camiones = new ArrayList<>();
                            if (task.getResult() != null) {
                                for (QueryDocumentSnapshot documento : task.getResult()) {
                                    Camion camion = documento.toObject(Camion.class);
                                    camion.setPlaca(documento.getId());
                                    camiones.add(camion);
                                }
                            }
                            callBackFirestore.correcto(camiones);
                        }
                    }
                });
    }

    public void traerCoordenadas(String placa, final CallBackFirebase<Camion> callBackFirebase) {

        fireDB.collection("camiones")
                .document(placa)
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
