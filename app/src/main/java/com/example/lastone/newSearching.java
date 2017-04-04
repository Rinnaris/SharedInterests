package com.example.lastone;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rinnaris on 2017-04-03.
 */

public class newSearching extends AppCompatActivity {

    //non UI variables
    ServiceHandler handler;
    public ArrayList<UserProfile> profileList;
    Thread queue;
    boolean running;
    boolean update;

    //UI variables
    LinearLayout topLayout;
    ArrayList<LinearLayout> topContents;
    FloatingActionButton searchButton;
    FloatingActionButton editButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_searching);

        //sets up nonUI variables
        handler = new ServiceHandler(this, (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE));
        profileList = new ArrayList<>();
        running = true;

        //starts building layout
        topLayout = (LinearLayout) findViewById(R.id.topView);
        topContents = new ArrayList<>();

        //creates buttons
        searchButton = (FloatingActionButton) findViewById(R.id.refreshButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.discoverService();
                profileList = handler.getNearby();
                buildUI();
            }
        });

        editButton = (FloatingActionButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(newSearching.this, Edit.class);
                startActivity(intent);
            }
        });
    }

    private void discoverLoop() {
        queue = new Thread(new Runnable() {
            public void run() {
                while(running) {
                    handler.discoverService();
                    Handler refresh = new Handler(Looper.getMainLooper());
                    refresh.post(new Runnable() {
                        public void run()
                        {
                            buildUI();
                        }
                    });
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

    public void buildUI() {

        topLayout.removeAllViews();
        ArrayList<String> temp;


        //for each nearby profile
        for (UserProfile up: profileList) {
            //create a linear layout for the profile
            LinearLayout profileMaster = new LinearLayout(this);
            profileMaster.setOrientation(LinearLayout.VERTICAL);

            //add info TextViews to Linear Layout
            TextView name = new TextView(this);
            name.setText(up.getName());
            name.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            name.setTextSize(30);
            name.setTypeface(null, Typeface.BOLD);
            profileMaster.addView(name);

            //set up movie linearlayout
            LinearLayout movieLayout = new LinearLayout(this);
            movieLayout.setOrientation(LinearLayout.VERTICAL);
            temp = up.getMovies();

            //movie textVew
            TextView movieTitle = new TextView(this);
            movieTitle.setText("Movies/TV");
            movieTitle.setTextSize(24);
            movieTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

            movieLayout.addView(movieTitle);
            View movieSeperator = new View(this);
            movieSeperator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            movieSeperator.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            movieLayout.addView(movieSeperator);

            //for each movie in profile
            for (String s: temp) {
                //create a text view
                TextView tempText = new TextView(this);
                tempText.setText("\t" + s);
                movieLayout.addView(tempText);
            }

            profileMaster.addView(movieLayout);

            //music

            //set up music linearlayout
            LinearLayout musicLayout = new LinearLayout(this);
            musicLayout.setOrientation(LinearLayout.VERTICAL);
            temp = up.getMusic();

            //music textVew
            TextView musicTitle = new TextView(this);
            musicTitle.setText("Music");
            musicTitle.setTextSize(24);
            musicTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

            musicLayout.addView(musicTitle);
            View musicSeperator = new View(this);
            musicSeperator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            musicSeperator.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            musicLayout.addView(musicSeperator);

            //for each music in profile
            for (String s: temp) {
                //create a text view
                TextView tempText = new TextView(this);
                tempText.setText("\t" + s);
                musicLayout.addView(tempText);
            }

            profileMaster.addView(musicLayout);

            //Sports

            //set up music linearlayout
            LinearLayout sportLayout = new LinearLayout(this);
            sportLayout.setOrientation(LinearLayout.VERTICAL);
            temp = up.getSports();

            //music textVew
            TextView sportTitle = new TextView(this);
            sportTitle.setText("Sports");
            sportTitle.setTextSize(24);
            sportTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

            sportLayout.addView(sportTitle);
            View sportSeperator = new View(this);
            sportSeperator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            sportSeperator.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            sportLayout.addView(sportSeperator);

            //for each music in profile
            for (String s: temp) {
                //create a text view
                TextView tempText = new TextView(this);
                tempText.setText("\t" + s);
                sportLayout.addView(tempText);
            }

            profileMaster.addView(sportLayout);

            //Books

            //set up music linearlayout
            LinearLayout bookLayout = new LinearLayout(this);
            bookLayout.setOrientation(LinearLayout.VERTICAL);
            temp = up.getBooks();

            //music textVew
            TextView bookTitle = new TextView(this);
            bookTitle.setText("Books");
            bookTitle.setTextSize(24);
            bookTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

            bookLayout.addView(bookTitle);
            View bookSeperator = new View(this);
            bookSeperator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            bookSeperator.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            bookLayout.addView(bookSeperator);

            //for each music in profile
            for (String s: temp) {
                //create a text view
                TextView tempText = new TextView(this);
                tempText.setText("\t" + s);
                bookLayout.addView(tempText);
            }

            profileMaster.addView(bookLayout);

            //hobbies

            //set up music linearlayout
            LinearLayout hobbyLayout= new LinearLayout(this);
            hobbyLayout.setOrientation(LinearLayout.VERTICAL);
            temp = up.getHobbys();

            //music textVew
            TextView hobbyTitle = new TextView(this);
            hobbyTitle.setText("Hobbies");
            hobbyTitle.setTextSize(24);
            hobbyTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

            hobbyLayout.addView(hobbyTitle);
            View hobbySeperator = new View(this);
            hobbySeperator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            hobbySeperator.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            hobbyLayout.addView(hobbySeperator);

            //for each music in profile
            for (String s: temp) {
                //create a text view
                TextView tempText = new TextView(this);
                tempText.setText("\t" + s);
                hobbyLayout.addView(tempText);
            }

            profileMaster.addView(hobbyLayout);

            //add final view to top
            topLayout.addView(profileMaster);
        }
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
        handler.toast("Search may need to be pressed again after a few seconds to update list.");
        handler.unregisterService();
        handler = new ServiceHandler(this, (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE));
        handler.discoverService();
        running = true;

        super.onResume();
    }
}
