package co.edu.unab.proyectomoviles.coordenadas.model.db.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import co.edu.unab.proyectomoviles.coordenadas.model.Entity.Camion;

@Dao
public interface CamionDAO {

    //se definen las consultas que se vana a realizar en Rooms con SQL
    @Insert
    void agregar(Camion camion);

    @Query("SELECT * FROM Camion")
    Camion obtener();

}
