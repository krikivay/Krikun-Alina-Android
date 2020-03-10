package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Button btn;
    EditText[] editText = new EditText[2];
    DatePicker date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText[0] = (EditText) findViewById(R.id.etFirstName);
        editText[1] = (EditText) findViewById(R.id.etLastName);

        date = (DatePicker) findViewById(R.id.dpDate);
        date.setMaxDate(System.currentTimeMillis());

        btn = (Button) findViewById(R.id.bLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText[0].getText().length() == 0){
                    editText[0].setError("full in!");
                }
                if(editText[1].getText().length() == 0){
                    editText[1].setError("full in!");
                }
                else {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    DatePicker datePicker = (DatePicker) findViewById(R.id.dpDate);
                    ArrayList<Integer> date = new ArrayList<>();
                    date.add(datePicker.getYear());
                    date.add(datePicker.getMonth() + 1);
                    date.add(datePicker.getDayOfMonth());
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
            }
        });

    }

}
