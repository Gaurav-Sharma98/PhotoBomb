package com.example.photobombproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.blikoon.qrcodescanner.QrCodeActivity;

public class Scanner extends AppCompatActivity {
    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner2);


                Button btn = findViewById(R.id.button);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent i = new Intent(Scanner.this, QrCodeActivity.class);
//                        startActivityForResult( i,REQUEST_CODE_QR_SCAN);
                    }
                });
            }
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {

                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode != Activity.RESULT_OK) {
                    Log.d("LOGTAG", "COULD NOT GET A GOOD RESULT.");
                    if (data == null)
                        return;
                    //Getting the passed result
                    String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
                    if (result != null) {
                        AlertDialog alertDialog = new AlertDialog.Builder(Scanner.this).create();
                        alertDialog.setTitle("Scan Error");
                        alertDialog.setMessage("QR Code could not be scanned");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    return;

                }
                if (requestCode == REQUEST_CODE_QR_SCAN) {
                    if (data == null)
                        return;
                    //Getting the passed result
                    String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                    Log.d("LOGTAG", "Have scan result in your app activity :" + result);
                    AlertDialog alertDialog = new AlertDialog.Builder(Scanner.this).create();
                    alertDialog.setTitle("Scan result");
                    alertDialog.setMessage(result);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
    }
}