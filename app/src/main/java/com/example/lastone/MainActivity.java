package com.example.lastone;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button nextButton;
    ServiceHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates a servicehandler with a temporary profile which will immediatly be replaced so don't worry about it
        handler = new ServiceHandler(this, (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE), new UserProfile("Bobby~^MOV~AAA"));
        handler.unregisterService();

        //gets button and sets listener
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gets profile from local storage
                UserProfile saved = handler.getMainProfileFromStorage();

                //if there isn't a saved profile yet
                if(saved == null){
                    Intent intent = new Intent(MainActivity.this, StartUp.class);
                    startActivity(intent);
                    handler.toast("No saved profile found. Preparing for setup.");
                }
                else{
                    //opens the searching activity instead
                    Intent intent = new Intent(MainActivity.this, newSearching.class);
                    startActivity(intent);
                }
            }
        });
    }
}
