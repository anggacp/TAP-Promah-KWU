package com.project.ocr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeScreen extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_screen, container, false);

        ImageButton clickOCR = (ImageButton) view.findViewById(R.id.ocr_button);
        clickOCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction frOCR = getFragmentManager().beginTransaction();
                frOCR.replace(R.id.fragment_container,new OcrScreen());
                frOCR.addToBackStack(null).commit();
            }
        });

        return view;
    }
}
