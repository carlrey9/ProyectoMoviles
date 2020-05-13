package co.edu.unab.proyectomoviles.coordenadas.model.db.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import co.edu.unab.proyectomoviles.coordenadas.model.entity.Camion;

@Database(entities = {Camion.class}, version = 1, exportSchema = false)
public abstract class BaseDatos extends RoomDatabase {

    public abstract CamionDAO camionDAO();
    private static BaseDatos instancia;

    public static BaseDatos obtenerInstancia(Context contexto){
        if (instancia==null){
            instancia = Room.databaseBuilder(
                    contexto.getApplicationContext(),BaseDatos.class,
                    "tiendapp.db").allowMainThreadQueries().build();
        }
        return instancia;
    }


}
