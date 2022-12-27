package com.example.projectinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GetStart extends AppCompatActivity {

    private Button btngetstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);

        btngetstart=findViewById(R.id.btnGetStart);

        btngetstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStart.this,UserLogin.class);
                startActivity(intent);
            }
        });
    }
}