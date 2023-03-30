package com.example.photobombproject.dashboard.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.photobombproject.R;
public class JoinFragment extends Fragment {

    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join, container, false);


        Button btn = view.findViewById(R.id.scan_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),QrCodeActivity.class));
            }
        });




        return  view;
    }
}