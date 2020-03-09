package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.bLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                DatePicker datePicker = (DatePicker) findViewById(R.id.dpDate);
                ArrayList<Integer> date = new ArrayList<>();
                date.add(datePicker.getYear());
                date.add(datePicker.getMonth() + 1);
                date.add(datePicker.getDayOfMonth());
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }

}
