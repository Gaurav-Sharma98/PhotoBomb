package com.example.photobombproject.utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import com.example.photobombproject.R;


public class Progress extends Dialog {

    public Progress(Context context) {
        super(context/*, android.R.style.Theme_Translucent_NoTitleBar*/);
        super.setCancelable(false);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.progressbar);
    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void hide() {
        super.hide();
        //  progressBar.setVisibility(View.GONE);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
