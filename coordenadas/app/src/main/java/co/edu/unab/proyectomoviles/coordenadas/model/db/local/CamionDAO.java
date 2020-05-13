package co.edu.unab.proyectomoviles.coordenadas.model.db.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import co.edu.unab.proyectomoviles.coordenadas.model.entity.Camion;

@Dao
public interface CamionDAO {

    @Insert
    void agregar(Camion miProducto);

    @Query("SELECT * FROM Camion")
    Camion obtener();

}
