package co.edu.unab.proyectomoviles.basurapp.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.model.entity.Camion;

public class CamionAdapter extends RecyclerView.Adapter {

    private List<Camion> camiones;
    onItemClickListener espichador;

    public CamionAdapter(List<Camion> camiones, onItemClickListener espichador) {
        this.camiones = camiones;
        this.espichador = espichador;
    }

    public void setCamiones(List<Camion> camiones) {
        this.camiones = camiones;
        this.notifyDataSetChanged();
    }

    public CamionAdapter(List<Camion> camiones) {
        this.camiones = camiones;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detalle, parent, false);
        return new CamionViewHolder(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        CamionViewHolder holderBind = (CamionViewHolder) holder;
        final Camion prodBind = camiones.get(position);
        holderBind.textViewplaca.setText(prodBind.getPlaca());
        holderBind.textViewMarca.setText(prodBind.getMarca());

        holderBind.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                espichador.onItemClick(prodBind, position);
            }
        });

        holderBind.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("tap", "click sostenido en : " + prodBind);
                return false;
            }

        });

    }

    @Override
    public int getItemCount() {
        return camiones.size();
    }

    public interface onItemClickListener {

        void onItemClick(Camion camion, int posicion);

    }

    class CamionViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewplaca;
        private TextView textViewMarca;

        public CamionViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewplaca = itemView.findViewById(R.id.textView_placa);
            textViewMarca = itemView.findViewById(R.id.textView_marca);
        }

    }

}


