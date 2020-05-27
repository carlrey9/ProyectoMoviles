package co.edu.unab.proyectomoviles.basurapp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import co.edu.unab.proyectomoviles.basurapp.R;
import co.edu.unab.proyectomoviles.basurapp.view.adapter.SliderAdapter;

public class SlideActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private SliderAdapter sliderAdapter;
    private LinearLayout layout_dots;
    private TextView[] tv_dost;
    private Button btnOmitirIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        final SharedPreferences misPreferencias = getSharedPreferences(getString(R.string.mis_datos_primer_ingreso2), MODE_PRIVATE);
        Boolean logueado = misPreferencias.getBoolean("primerIngreso2", false);
        if(logueado){
            Intent in = new Intent(SlideActivity.this, LoginActivity.class);
            startActivity(in);
            finish();
        }

        iniciarScreenDesing();

        asociarElementos();

        iniciarSlider();



        btnOmitirIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor miEditor = misPreferencias.edit();
                miEditor.putBoolean("primerIngreso2", true);
                miEditor.apply();

                Intent in = new Intent(SlideActivity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        });

    }

    private void addDots(int position){
        tv_dost = new TextView[3];
        layout_dots.removeAllViews();
        for (int i = 0; i < tv_dost.length; i++){
            tv_dost[i] = new TextView(SlideActivity.this);
            tv_dost[i].setText(Html.fromHtml("&#8226;"));
            tv_dost[i].setTextSize(35);
            tv_dost[i].setTextColor(getResources().getColor(R.color.light_white));

            layout_dots.addView(tv_dost[i]);
        }
        if (tv_dost.length > 0){
            tv_dost[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void asociarElementos(){
        view_pager = findViewById(R.id.view_pager);
        layout_dots = findViewById(R.id.layout_dots);
        btnOmitirIntro = findViewById(R.id.btn_omitir_intro);
    }

    public void iniciarScreenDesing(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void iniciarSlider (){
        sliderAdapter = new SliderAdapter((SlideActivity.this));
        view_pager.setAdapter(sliderAdapter);

        addDots(0);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
