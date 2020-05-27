package co.edu.unab.proyectomoviles.basurapp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.onesignal.OneSignal;

import co.edu.unab.proyectomoviles.basurapp.R;

public class HomeActivity extends AppCompatActivity {

    private Button btnCerrarSesion;
    private Button btnVercamiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        oneSignal();

        asociarElementos();

        verCamiones();

        cerrarSesion();
    }

    public void asociarElementos(){
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);
        btnVercamiones = findViewById(R.id.btn_ver_camiones);
    }

    public void cerrarSesion(){
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences misPreferencias = getSharedPreferences(getString(R.string.mis_datos), MODE_PRIVATE);
                SharedPreferences.Editor miEditor = misPreferencias.edit();
                miEditor.clear();
                miEditor.apply();
                Intent in = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        });

    }

    public  void verCamiones(){
        btnVercamiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(in);
            }
        });
    }

    public void oneSignal(){
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
