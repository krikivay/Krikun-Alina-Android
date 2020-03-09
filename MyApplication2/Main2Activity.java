package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ArrayList<Integer> data = getIntent().getExtras().getIntegerArrayList("date");

        Date today = new Date();
        Date date = new Date(data.get(0), data.get(1), data.get(2));
        Period period = Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        TextView txt = (TextView) findViewById(R.id.tvAnswer);
        if(period.getYears() + 1900 < 0) txt.setText("You are not born yet");
        if(period.getYears() + 1900 >= 0 && period.getYears() + 1900 < 18) txt.setText("You are minor");
        if(period.getYears() + 1900 >= 18 && period.getYears() < 28) txt.setText("You are young");
        if(period.getYears() + 1900 >= 28) txt.setText("You are old");
    }
}
