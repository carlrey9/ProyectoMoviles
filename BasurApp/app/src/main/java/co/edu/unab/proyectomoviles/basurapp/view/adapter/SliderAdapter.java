package co.edu.unab.proyectomoviles.basurapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import co.edu.unab.proyectomoviles.basurapp.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    private int[] slide_imagens = {
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3,
    };

    private String[] slider_title = {
            "Basura en \ntu Area",
            "Saca a \ntiempo la Basura",
            "Sin \ndesesperos ni Afanes"
    };

    private String[] slider_desc = {
            "BasurApp esta en tus manos, BasurApp esta en tus manos, BasurApp esta en tus manos, BasurApp esta en tus manos, ",
            "BasurApp esta en tus manos, BasurApp esta en tus manos, BasurApp esta en tus manos, BasurApp esta en tus manos, ",
            "BasurApp esta en tus manos, BasurApp esta en tus manos, BasurApp esta en tus manos, BasurApp esta en tus manos, "
    };

    @Override
    public int getCount() {
        return slider_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView img_banner = view.findViewById(R.id.img_banner);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_desc = view.findViewById(R.id.tv_desc);

        img_banner.setImageResource(slide_imagens[position]);
        tv_title.setText(slider_title[position]);
        tv_desc.setText(slider_desc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
