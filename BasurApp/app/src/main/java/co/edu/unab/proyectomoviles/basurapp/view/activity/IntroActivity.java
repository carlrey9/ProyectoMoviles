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

    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final SharedPreferences misPreferencias = getSharedPreferences(getString(R.string.mis_datos_primer_ingreso), MODE_PRIVATE);

        Boolean logueado = misPreferencias.getBoolean("primerIngreso", false);
        if(logueado){
            Intent in = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(in);
            finish();
        }

        img1 = findViewById(R.id.img_1);
        String url = "https://www.pngkey.com/png/full/237-2374807_heavy-duty-type-garbage-truck.png";
        Picasso.get().load(url).into(img1);

        //SILDE SCREEN DESIGN-----
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView tv_next = findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor miEditor = misPreferencias.edit();
                miEditor.putBoolean("primerIngreso", true);
                miEditor.apply();

                startActivity(new Intent(IntroActivity.this, SlideActivity.class));
                finish();


            }
        });

        //SILDE SCREEN DESIGN-----
    }


}
