package com.project.ocr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.gson.Gson;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OcrScreen extends AppCompatActivity {

    TextToSpeech textToSpeech;
    TranslateAPI translateAPI;

    ImageView preview;
    Button ocrSpeechBtn;
    Button ocrTranslateBtn;
    TextView ocrResultTxt;
    TextView ocrTranslateTxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocr_screen);

        //Untuk menyembunyikan appbar
        getSupportActionBar().hide();


        preview = findViewById(R.id.image_preview);
        ocrSpeechBtn = findViewById(R.id.bunyikan_button);
        ocrTranslateBtn = findViewById(R.id.translate_button);
        ocrResultTxt = findViewById(R.id.ocr_result);
        ocrTranslateTxt = findViewById(R.id.ocr_translate);

        textToSpeech = new TextToSpeech(OcrScreen.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        ocrSpeechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = ocrResultTxt.getText().toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH,null,null);
                }
            }
        });

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, 101);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nlp-translation.p.rapidapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        translateAPI = retrofit.create(TranslateAPI.class);

        ocrTranslateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { createPost(); }
        });
    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    public void doProcess(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        //from bunle, extract the image
        Bitmap bitmap = (Bitmap) bundle.get("data");
        //set image in ImageView
        preview.setImageBitmap(bitmap);
        //process the Image
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVision firebaseVision = FirebaseVision.getInstance();
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
        Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
        task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                String s = firebaseVisionText.getText();
                ocrResultTxt.setText(s);
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void createPost() {
        String toBeTranslated = ocrResultTxt.getText().toString();
        Call<Post> call = translateAPI.createPost(toBeTranslated, "id");
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()) {
                    ocrTranslateTxt.setText("Code: " + response.code());
                    return;
                }

                response.body().toString();
                Post postResponse = response.body();

                Gson gson = new Gson();
                TranslatedText p = gson.fromJson(postResponse.getTranslated_text(), TranslatedText.class);

                String content = p.id;
                ocrTranslateTxt.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                ocrTranslateTxt.setText(t.getMessage());
            }
        });
    }
}
