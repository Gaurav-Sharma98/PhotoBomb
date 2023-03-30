package com.example.photobombproject.broadcastingsample;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.photobombproject.R;
import com.example.photobombproject.dashboard.Home;
import com.example.photobombproject.utility.NetworkUtil;

public class MyReceiver extends BroadcastReceiver {
    Dialog dialog;
    TextView nettxt;

    @Override
    public void onReceive(Context context, Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);
        dialog = new Dialog(context,android.R.style.Theme_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.custom_dialog_layout);
        Button restartapp = (Button)dialog.findViewById(R.id.restart_app);
        nettxt =(TextView)dialog.findViewById(R.id.net_text);

        restartapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
                Log.d("clickedbutton","yes");
                Intent i = new Intent(context, Home.class);
                context.startActivity(i);
            }
        });

        Log.d("network",status);
        if(status.isEmpty()||status.equals("No internet is available")||status.equals("No Internet Connection")) {
            status="No Internet Connection";
            dialog.show();
        }
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }

}