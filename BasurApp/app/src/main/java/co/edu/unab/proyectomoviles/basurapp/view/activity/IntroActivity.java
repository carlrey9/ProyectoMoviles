package co.edu.unab.proyectomoviles.basurapp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import co.edu.unab.proyectomoviles.basurapp.R;

public class IntroActivity extends AppCompatActivity {

    private ImageView img1;
    private TextView txvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        final SharedPreferences misPreferencias = getSharedPreferences(getString(R.string.mis_datos_primer_ingreso), MODE_PRIVATE);

        Boolean primerIngreso = misPreferencias.getBoolean("primerIngreso", false);
        if (primerIngreso) {
            Intent in = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(in);
            finish();
        }

        asociarElementos();

        ponerImagen();

        iniciarScreenDesing();

        txvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor miEditor = misPreferencias.edit();
                miEditor.putBoolean("primerIngreso", true);
                miEditor.apply();

                startActivity(new Intent(IntroActivity.this, SlideActivity.class));
                finish();


            }
        });

    }
    
    
    public void asociarElementos(){
        img1 = findViewById(R.id.img_1);
        txvNext = findViewById(R.id.tv_next);
    }
    
    public void ponerImagen(){
        String url = "https://www.pngkey.com/png/full/237-2374807_heavy-duty-type-garbage-truck.png";
        Picasso.get().load(url).into(img1);
    }
    
    public void iniciarScreenDesing(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    

    


}
