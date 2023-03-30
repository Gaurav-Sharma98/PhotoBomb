package com.example.photobombproject.OnBoardScreen.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.photobombproject.R;

public class OnBoardAdaptor extends PagerAdapter{
    ViewPager viewPager;
    Context context;
    private int[] GalImages = new int[]{
            R.drawable.one1,
            R.drawable.two1,
            R.drawable.two1
    };

    LayoutInflater mLayoutInflater;

    public OnBoardAdaptor(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.onboarddesign, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.ob_main);
        imageView.setImageResource(GalImages[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}

