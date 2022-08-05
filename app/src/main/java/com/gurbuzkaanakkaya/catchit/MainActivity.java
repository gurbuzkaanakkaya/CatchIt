package com.gurbuzkaanakkaya.catchit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timeText;
    TextView scoreText;
    TextView puanText;
    Button button;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView imageView11;
    ImageView imageView12;
    Random random = new Random();
    int[] randomNum = new int[2];
    int score;
    int i;
    int storedScore;
    String userName1;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userInput");
        timeText = findViewById(R.id.time);
        scoreText = findViewById(R.id.score);
        puanText = findViewById(R.id.puanText);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        imageView11 = findViewById(R.id.imageView11);
        imageView12 = findViewById(R.id.imageView12);
        button = findViewById(R.id.run);
        imageArray = new ImageView[]{imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,imageView12};
        hideImages();
        score = 0;
        randomNum[0] =random.nextInt(12);
        randomNum[1] =random.nextInt(12);
        sharedPreferences = this.getSharedPreferences("com.gurbuzkaanakkaya.catchit", Context.MODE_PRIVATE);
        storedScore = sharedPreferences.getInt("storedScore",0);
        userName1 = sharedPreferences.getString("userName","");
        puanText.setVisibility(View.VISIBLE);
        puanText.setText("En Yüksek Puan: "  + userName1+": "+ storedScore);
        button.setVisibility(View.INVISIBLE);
        puanText.setVisibility(View.INVISIBLE);
        score =0;
        scoreText.setText("PUAN: "+ score);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                hideImages();
                if(randomNum[1] == randomNum[0]){
                    while(randomNum[0] == randomNum[1]){
                        randomNum[1]=random.nextInt(12);}
                    imageArray[randomNum[1]].setVisibility(View.VISIBLE);
                    handler.postDelayed(this, 500);
                    randomNum[0] = randomNum[1];
                    randomNum[1]= random.nextInt(12);
                }
                else if(randomNum[0] != randomNum[1]){
                    imageArray[randomNum[1]].setVisibility(View.VISIBLE);
                    handler.postDelayed(this, 500);
                    randomNum[0] = randomNum[1];
                    randomNum[1]=random.nextInt(12);
                }
            }
        };
        handler.post(runnable);

        new CountDownTimer(30000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Süre: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Süre bitti");
                handler.removeCallbacks(runnable);
                for(ImageView image: imageArray){
                    image.setVisibility(View.INVISIBLE);
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Blue Ball");
                alert.setMessage("Bir oyun daha ?");
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        if(score>storedScore) {
                            storedScore = score;
                            userName1=userName;
                            sharedPreferences.edit().putInt("storedScore", score).apply();
                            sharedPreferences.edit().putString("userName",userName1).apply();
                            puanText.setText("En Yüksek Puan: "+ userName1+" : "+storedScore);
                        }
                    }
                });
                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        button.setVisibility(View.VISIBLE);
                        puanText.setVisibility(View.VISIBLE);
                        scoreText.setText("PUAN: 0");
                        if(score>storedScore) {
                            storedScore = score;
                            sharedPreferences.edit().putInt("storedScore", score).apply();
                            userName1=userName;
                            sharedPreferences.edit().putString("userName",userName1).apply();
                            puanText.setText("En Yüksek Puan: "+ userName1+" : "+storedScore);
                            Toast.makeText(MainActivity.this, "Tebrikler Yeni Rekor!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.show();
            }
        }.start();
    }
    public void run(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(intent);
        MainActivity.this.finishAffinity();
    }
    public void increaseScore(View view){
        score++;
        scoreText.setText("Puan: "+ score);
    }
    public void hideImages(){
        for(ImageView image: imageArray){
            image.setVisibility(View.INVISIBLE);
        }
    }
}
