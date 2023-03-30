package com.example.photobombproject.signupndlogin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.photobombproject.R;

public class Contrats_Screen extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contrats__screen, container, false);

        Button goLogin=view.findViewById(R.id.goLogin);
        goLogin.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_contrats_Screen_to_login));

        return  view;
    }
}