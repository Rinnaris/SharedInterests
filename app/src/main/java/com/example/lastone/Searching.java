package com.example.lastone;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by rinnaris on 2017-04-01.
 */

public class Searching extends AppCompatActivity {
    ServiceHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        handler = new ServiceHandler(this, (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE));
    }


}
