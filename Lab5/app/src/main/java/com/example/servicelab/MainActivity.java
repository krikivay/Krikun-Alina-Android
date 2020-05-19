package com.example.servicelab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etPart1, etPart2, etOperand;
    TextView tvResult;
    Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        etPart1 = findViewById(R.id.etPart1);
        etOperand = findViewById(R.id.etOperand);
        etPart2 = findViewById(R.id.etPart2);
        btnCalc = findViewById(R.id.btnCalc);

        btnCalc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        PendingIntent pendingIntent;
        //передача данных сервису
        pendingIntent = createPendingResult(0, new Intent(), 0);
        startService(new Intent(MainActivity.this, CalculationService.class).putExtra("num1", etPart1.getText().toString())
                .putExtra("num2", etPart2.getText().toString())
                .putExtra("operand", etOperand.getText().toString())
                .putExtra("pending intent", pendingIntent));
    }
    //обработка результатов работы сервиса
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        String errorMessage;
        double result;
        switch (resultCode)
        {
            case 0:
                result = data.getDoubleExtra("result", 0.0);
                tvResult.setText(Double.toString(result));
                break;
            case -1:
                errorMessage = data.getStringExtra("num1error");
                tvResult.setText(errorMessage);
                break;
            case -2:
                errorMessage = data.getStringExtra("num2error");
                tvResult.setText(errorMessage);
                break;
            case -3:
                errorMessage = data.getStringExtra("operanderror");
                tvResult.setText(errorMessage);
                break;
        }


    }
}
