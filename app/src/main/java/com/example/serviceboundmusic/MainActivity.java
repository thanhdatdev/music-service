package com.example.serviceboundmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.serviceboundmusic.MyService.MyBinder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton btOn = (ImageButton) findViewById(R.id.btOn);
        final ImageButton btOff = (ImageButton) findViewById(R.id.btOff);
        final ImageButton btFast = (ImageButton) findViewById(R.id.btnPrevios);
        recyclerView = findViewById(R.id.recyc);
        list = new ArrayList<>();
        list.add("Đừng tin Her - Bray");
        adapter = new Adapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Khởi tạo ServiceConnection
        connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBound = false;
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                myService = binder.getService(); // lấy đối tượng MyService
                isBound = true;
            }
        };

        // Khởi tạo intent
        final Intent intent =
                new Intent(MainActivity.this,
                        MyService.class);
        bindService(intent, connection,
                Context.BIND_AUTO_CREATE);
        Toast.makeText(MainActivity.this,
                "Mở bài hát", Toast.LENGTH_SHORT).show();
        Log.d("Service","Service đang hoạt động");

        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                    if(myService.isPlaying()){
                        myService.pause();
                        btOn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        Toast.makeText(MainActivity.this,
                                "Tạm dừng", Toast.LENGTH_SHORT).show();
                        Log.d("Play","Nhac dang mo....");
                    }else{
                        myService.play();
                        btOn.setImageResource(R.drawable.ic_baseline_pause_24);
                        Toast.makeText(MainActivity.this,
                                "Tiếp tục", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nếu Service đang hoạt động
                if (isBound==true) {
                    // Tắt Service
                    unbindService(connection);
                    isBound = false;
                    Toast.makeText(MainActivity.this,
                            "Tắt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                    myService.fastForward();
                    myService.fastStart();
                    Toast.makeText(MainActivity.this,
                            "Tua tới bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnPrevios).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if (isBound) {
                    // tua bài hát
                    myService.fastForward();
                    myService.fastStart();
                    Toast.makeText(MainActivity.this,
                            "Tua lui bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}