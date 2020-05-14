package co.edu.unab.proyectomoviles.basurapp.model.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.proyectomoviles.basurapp.model.bd.network.CallBackFirestore;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Cliente;

public class ClienteRepository {
    private String parameterusername;

    FirebaseFirestore dbFirestore;

    public ClienteRepository(Context contexto) {
        this.dbFirestore = dbFirestore;
    }


    public void reconocerLogin ( final CallBackFirestore <List<Cliente>> callBackFirestore ){
        dbFirestore.collection("usuarios_cliente").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Cliente> clientes = new ArrayList<>();
                    if (task.getResult() != null){

                    }
                    callBackFirestore.correcto(clientes);

                }else{
                    Log.d("Error","no se conectó con la base de datos");
                }
            }
        });

    }
}

