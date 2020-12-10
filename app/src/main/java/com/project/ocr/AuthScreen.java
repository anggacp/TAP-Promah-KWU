package com.project.ocr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AuthScreen extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.auth_screen, container, false);


        Button clickOke = (Button) view.findViewById(R.id.button_oke);
        clickOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction frHome = getFragmentManager().beginTransaction();
                frHome.replace(R.id.fragment_container,new HomeScreen());
                frHome.addToBackStack(null).commit();
            }
        });

        return view;
    }
}
