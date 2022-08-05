package com.gurbuzkaanakkaya.catchit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    TextView textView;
    EditText nameOfHunter;
    Button button;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.yakala);
        nameOfHunter = findViewById(R.id.nameOfHunter);
        button = findViewById(R.id.run1);
        username = "";

    }
    public void run1(View view){
        if(nameOfHunter.getText().toString().matches("")){
            Toast.makeText(MainActivity2.this, "İsmini Girmelisin!", Toast.LENGTH_SHORT).show();
        }else {
            username = nameOfHunter.getText().toString();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("userInput", username);
            startActivity(intent);
            MainActivity2.this.finishAffinity();
        }
    }
}