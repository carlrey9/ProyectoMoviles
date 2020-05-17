package co.edu.unab.proyectomoviles.basurapp.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.model.entity.ModelClass;
import co.edu.unab.proyectomoviles.basurapp.view.adapter.Adapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RECYCLERVIEW
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<ModelClass> modelClassList = new ArrayList<>();
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 1", "Titulo 1 Usuario"));
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 2", "Titulo 2 Usuario"));
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 3", "Titulo 3 Usuario"));
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 4", "Titulo 4 Usuario"));
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 5", "Titulo 5 Usuario"));
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 6", "Titulo 6 Usuario"));
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 7", "Titulo 7 Usuario"));
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 8", "Titulo 8 Usuario"));
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 9", "Titulo 9 Usuario"));
        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background, "Titulo 10", "Titulo 10 Usuario"));

        Adapter adapter = new Adapter(modelClassList);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        //--------------------------------------------------inicio-------------------------------------------------

        //--------------------------------------------------fin-------------------------------------------------


    }
}
