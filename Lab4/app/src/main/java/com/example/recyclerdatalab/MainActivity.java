package com.example.recyclerdatalab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    Button btn;
    List<Post> posts = new ArrayList<>();
    TextView tvSomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSomeText = findViewById(R.id.tvSomeText);
        tvSomeText.setVisibility(View.INVISIBLE);
        btn = findViewById(R.id.btnDownload);
        rv = findViewById(R.id.rv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //загрузка данных с помощью retrofit
                NetworkService.getInstance()
                        .getJSONApi()
                        .getAllPosts()
                        .enqueue(new Callback<List<Post>>() {
                            @Override
                            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                                //получение списка данных
                                posts = response.body();
                                tvSomeText.setVisibility(View.VISIBLE);
                                //размещение данных в RecyclerView
                                DataAdapter dataAdapter = new DataAdapter(MainActivity.this, posts);
                                rv.setAdapter(dataAdapter);
                            }

                            @Override
                            public void onFailure(Call<List<Post>> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
