package com.example.lastone;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by rinnaris on 2017-04-01.
 */

public class Searching extends AppCompatActivity {

    FloatingActionButton fab;
    Button button;
    ServiceHandler handler;
    Thread queue;
    boolean running;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        /*button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.discoverService();
            }
        });*/

        running = true;

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searching.this, newSearching.class);
                startActivity(intent);
            }
        });
        handler = new ServiceHandler(this, (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE));
        handler.discoverService();
        discoverLoop();
    }

    private void discoverLoop() {
        queue = new Thread(new Runnable() {
            public void run() {
                while(running) {
                    handler.discoverService();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        queue.start();
    }

    protected void onDestroy(){
        handler.unregisterService();
        super.onDestroy();
    }

    protected void onPause(){
        running = false;
        handler.unregisterService();
        super.onPause();
    }

    protected void onResume(){
        handler = new ServiceHandler(this, (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE));
        running = true;

        super.onResume();
    }
}
