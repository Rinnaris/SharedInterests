package com.example.lastone;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {

    //***************************************
    //background variables
    ServiceHandler handler;
    UserProfile profile;
    LinearLayout.LayoutParams textParams;
    LinearLayout.LayoutParams buttonParams;
    Button cancelButton;
    Button saveButton;

    //information variables
    EditText name;
    EditText phone;
    EditText email;

    //Movie variables
    LinearLayout movieLayout;
    EditText movieText;
    Button movieButton;
    ArrayList<String> movies;
    ArrayList<LinearLayout> movieLayoutList;
    ArrayList<TextView> movieTextViewList;
    ArrayList<Button> movieButtonList;

    //Music variables
    LinearLayout musicLayout;
    EditText musicText;
    Button musicButton;
    ArrayList<String> music;
    ArrayList<LinearLayout> musicLayoutList;
    ArrayList<TextView> musicTextViewList;
    ArrayList<Button> musicButtonList;

    //Sport variables
    LinearLayout sportLayout;
    EditText sportText;
    Button sportButton;
    ArrayList<String> sport;
    ArrayList<LinearLayout> sportLayoutList;
    ArrayList<TextView> sportTextViewList;
    ArrayList<Button> sportButtonList;

    //book variables
    LinearLayout bookLayout;
    EditText bookText;
    Button bookButton;
    ArrayList<String>book;
    ArrayList<LinearLayout> bookLayoutList;
    ArrayList<TextView> bookTextViewList;
    ArrayList<Button> bookButtonList;

    //hobby variables
    LinearLayout hobbyLayout;
    EditText hobbyText;
    Button hobbyButton;
    ArrayList<String> hobby;
    ArrayList<LinearLayout> hobbyLayoutList;
    ArrayList<TextView> hobbyTextViewList;
    ArrayList<Button> hobbyButtonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //gets handler and UserProfile
        handler = new ServiceHandler(this, (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE));
        handler.unregisterService();
        profile = handler.getMainProfileFromStorage();

        //builds the params needed for each item
        textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 10);
        buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        //sets information variables
        setInfoVariables();

        //sets movie variables
        setMovieVariables();
        buildMovieLayout();

        //sets music variables
        setMusicVariables();
        buildMusicLayout();

        //sets sports variables
        setsportVariables();
        buildsportLayout();

        //sets book variables
        setbookVariables();
        buildbookLayout();

        //sets hobby variables
        sethobbyVariables();
        buildhobbyLayout();

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")){
                    handler.toast("Username field is required");
                    return;
                }
                profile.setName(name.getText().toString());
                profile.setMovie(movies);
                profile.setMusic(music);
                profile.setSport(sport);
                profile.setBook(book);
                profile.setHobby(hobby);

                handler.saveMainProfileToStorage();
                finish();
            }
        });
    }

    //*********************************
//hobby methods

    private void buildhobbyLayout(){
        //for each item in hobby
        for (String s: hobby) {

            //create a new horizonal linear layout
            LinearLayout tempLinLayout = new LinearLayout(this);
            tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

            //create a textView and name it
            TextView tempTextView = new TextView(this);
            tempTextView.setText(s);
            tempTextView.setLayoutParams(textParams);

            //create a button
            Button tempButton = new Button(this);
            tempButton.setText("Delete");
            tempButton.setLayoutParams(buttonParams);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get index of button that was clicked
                    int index = hobbyButtonList.indexOf(v);

                    //remove that item from all arraylists
                    hobby.remove(index);
                    hobbyTextViewList.remove(index);
                    hobbyLayoutList.remove(index);
                    hobbyButtonList.remove(index);
                    hobbyLayout.removeView((View) v.getParent());
                }
            });

            tempLinLayout.addView(tempTextView);
            tempLinLayout.addView(tempButton);

            hobbyLayoutList.add(tempLinLayout);
            hobbyTextViewList.add(tempTextView);
            hobbyButtonList.add(tempButton);

            hobbyLayout.addView(tempLinLayout);
        }
    }

    private void sethobbyVariables() {
        hobbyLayout = (LinearLayout) findViewById(R.id.hobbyLinear);
        hobby = profile.getHobbys();
        hobbyLayoutList = new ArrayList<>();
        hobbyTextViewList = new ArrayList<>();
        hobbyButtonList = new ArrayList<>();

        hobbyText = (EditText) findViewById(R.id.addHobbyText);
        hobbyButton = (Button) findViewById(R.id.addHobbyButton);
        hobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hobbyText.getText().toString().equals("")){
                    return;
                }
                addhobby(hobbyText.getText().toString());
            }
        });
    }

    private void addhobby(String s) {
        hobby.add(s);

        //create a new horizonal linear layout
        LinearLayout tempLinLayout = new LinearLayout(this);
        tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

        //create a textView and name it
        TextView tempTextView = new TextView(this);
        tempTextView.setText(s);
        tempTextView.setLayoutParams(textParams);

        //create a button
        Button tempButton = new Button(this);
        tempButton.setText("Delete");
        tempButton.setLayoutParams(buttonParams);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get index of button that was clicked
                int index = hobbyButtonList.indexOf(v);

                //remove that item from all arraylists
                hobby.remove(index);
                hobbyTextViewList.remove(index);
                hobbyLayoutList.remove(index);
                hobbyButtonList.remove(index);
                hobbyLayout.removeView((View) v.getParent());
            }
        });

        tempLinLayout.addView(tempTextView);
        tempLinLayout.addView(tempButton);

        hobbyLayoutList.add(tempLinLayout);
        hobbyTextViewList.add(tempTextView);
        hobbyButtonList.add(tempButton);

        hobbyLayout.addView(tempLinLayout);
    }
    //*********************************

    //*********************************
//book methods

    private void buildbookLayout(){
        //for each item in book
        for (String s: book) {

            //create a new horizonal linear layout
            LinearLayout tempLinLayout = new LinearLayout(this);
            tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

            //create a textView and name it
            TextView tempTextView = new TextView(this);
            tempTextView.setText(s);
            tempTextView.setLayoutParams(textParams);

            //create a button
            Button tempButton = new Button(this);
            tempButton.setText("Delete");
            tempButton.setLayoutParams(buttonParams);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get index of button that was clicked
                    int index = bookButtonList.indexOf(v);

                    //remove that item from all arraylists
                    book.remove(index);
                    bookTextViewList.remove(index);
                    bookLayoutList.remove(index);
                    bookButtonList.remove(index);
                    bookLayout.removeView((View) v.getParent());
                }
            });

            tempLinLayout.addView(tempTextView);
            tempLinLayout.addView(tempButton);

            bookLayoutList.add(tempLinLayout);
            bookTextViewList.add(tempTextView);
            bookButtonList.add(tempButton);

            bookLayout.addView(tempLinLayout);
        }
    }

    private void setbookVariables() {
        bookLayout = (LinearLayout) findViewById(R.id.bookLinear);
        book = profile.getBooks();
        bookLayoutList = new ArrayList<>();
        bookTextViewList = new ArrayList<>();
        bookButtonList = new ArrayList<>();

        bookText = (EditText) findViewById(R.id.addBookText);
        bookButton = (Button) findViewById(R.id.addBookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookText.getText().toString().equals("")){
                    return;
                }
                addbook(bookText.getText().toString());
            }
        });
    }

    private void addbook(String s) {
        book.add(s);

        //create a new horizonal linear layout
        LinearLayout tempLinLayout = new LinearLayout(this);
        tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

        //create a textView and name it
        TextView tempTextView = new TextView(this);
        tempTextView.setText(s);
        tempTextView.setLayoutParams(textParams);

        //create a button
        Button tempButton = new Button(this);
        tempButton.setText("Delete");
        tempButton.setLayoutParams(buttonParams);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get index of button that was clicked
                int index = bookButtonList.indexOf(v);

                //remove that item from all arraylists
                book.remove(index);
                bookTextViewList.remove(index);
                bookLayoutList.remove(index);
                bookButtonList.remove(index);
                bookLayout.removeView((View) v.getParent());
            }
        });

        tempLinLayout.addView(tempTextView);
        tempLinLayout.addView(tempButton);

        bookLayoutList.add(tempLinLayout);
        bookTextViewList.add(tempTextView);
        bookButtonList.add(tempButton);

        bookLayout.addView(tempLinLayout);
    }
//*********************************

    //*********************************
    //sport methods

    private void buildsportLayout(){
        //for each item in sport
        for (String s: sport) {

            //create a new horizonal linear layout
            LinearLayout tempLinLayout = new LinearLayout(this);
            tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

            //create a textView and name it
            TextView tempTextView = new TextView(this);
            tempTextView.setText(s);
            tempTextView.setLayoutParams(textParams);

            //create a button
            Button tempButton = new Button(this);
            tempButton.setText("Delete");
            tempButton.setLayoutParams(buttonParams);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get index of button that was clicked
                    int index = sportButtonList.indexOf(v);

                    //remove that item from all arraylists
                    sport.remove(index);
                    sportTextViewList.remove(index);
                    sportLayoutList.remove(index);
                    sportButtonList.remove(index);
                    sportLayout.removeView((View) v.getParent());
                }
            });

            tempLinLayout.addView(tempTextView);
            tempLinLayout.addView(tempButton);

            sportLayoutList.add(tempLinLayout);
            sportTextViewList.add(tempTextView);
            sportButtonList.add(tempButton);

            sportLayout.addView(tempLinLayout);
        }
    }

    private void setsportVariables() {
        sportLayout = (LinearLayout) findViewById(R.id.sportLinear);
        sport = profile.getSports();
        sportLayoutList = new ArrayList<>();
        sportTextViewList = new ArrayList<>();
        sportButtonList = new ArrayList<>();

        sportText = (EditText) findViewById(R.id.addSportText);
        sportButton = (Button) findViewById(R.id.addSportButton);
        sportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sportText.getText().toString().equals("")){
                    return;
                }
                addsport(sportText.getText().toString());
            }
        });
    }

    private void addsport(String s) {
        sport.add(s);

        //create a new horizonal linear layout
        LinearLayout tempLinLayout = new LinearLayout(this);
        tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

        //create a textView and name it
        TextView tempTextView = new TextView(this);
        tempTextView.setText(s);
        tempTextView.setLayoutParams(textParams);

        //create a button
        Button tempButton = new Button(this);
        tempButton.setText("Delete");
        tempButton.setLayoutParams(buttonParams);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get index of button that was clicked
                int index = sportButtonList.indexOf(v);

                //remove that item from all arraylists
                sport.remove(index);
                sportTextViewList.remove(index);
                sportLayoutList.remove(index);
                sportButtonList.remove(index);
                sportLayout.removeView((View) v.getParent());
            }
        });

        tempLinLayout.addView(tempTextView);
        tempLinLayout.addView(tempButton);

        sportLayoutList.add(tempLinLayout);
        sportTextViewList.add(tempTextView);
        sportButtonList.add(tempButton);

        sportLayout.addView(tempLinLayout);
    }
    //*********************************

    //*********************************
    //music methods

    private void buildMusicLayout(){
        //for each item in music
        for (String s: music) {

            //create a new horizonal linear layout
            LinearLayout tempLinLayout = new LinearLayout(this);
            tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

            //create a textView and name it
            TextView tempTextView = new TextView(this);
            tempTextView.setText(s);
            tempTextView.setLayoutParams(textParams);

            //create a button
            Button tempButton = new Button(this);
            tempButton.setText("Delete");
            tempButton.setLayoutParams(buttonParams);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get index of button that was clicked
                    int index = musicButtonList.indexOf(v);

                    //remove that item from all arraylists
                    music.remove(index);
                    musicTextViewList.remove(index);
                    musicLayoutList.remove(index);
                    musicButtonList.remove(index);
                    musicLayout.removeView((View) v.getParent());
                }
            });

            tempLinLayout.addView(tempTextView);
            tempLinLayout.addView(tempButton);

            musicLayoutList.add(tempLinLayout);
            musicTextViewList.add(tempTextView);
            musicButtonList.add(tempButton);

            musicLayout.addView(tempLinLayout);
        }
    }

    private void setMusicVariables() {
        musicLayout = (LinearLayout) findViewById(R.id.musicLinear);
        music = profile.getMusic();
        musicLayoutList = new ArrayList<>();
        musicTextViewList = new ArrayList<>();
        musicButtonList = new ArrayList<>();

        musicText = (EditText) findViewById(R.id.addMusicText);
        musicButton = (Button) findViewById(R.id.addMusicButton);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMusic(musicText.getText().toString());
            }
        });
    }

    private void addMusic(String s) {
        music.add(s);

        //create a new horizonal linear layout
        LinearLayout tempLinLayout = new LinearLayout(this);
        tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

        //create a textView and name it
        TextView tempTextView = new TextView(this);
        tempTextView.setText(s);
        tempTextView.setLayoutParams(textParams);

        //create a button
        Button tempButton = new Button(this);
        tempButton.setText("Delete");
        tempButton.setLayoutParams(buttonParams);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get index of button that was clicked
                int index = musicButtonList.indexOf(v);

                //remove that item from all arraylists
                music.remove(index);
                musicTextViewList.remove(index);
                musicLayoutList.remove(index);
                musicButtonList.remove(index);
                musicLayout.removeView((View) v.getParent());
            }
        });

        tempLinLayout.addView(tempTextView);
        tempLinLayout.addView(tempButton);

        musicLayoutList.add(tempLinLayout);
        musicTextViewList.add(tempTextView);
        musicButtonList.add(tempButton);

        musicLayout.addView(tempLinLayout);
    }
    //*********************************

    //**********************************
    //movie methods
    private void buildMovieLayout() {


        //for each item in movies
        for (String s: movies) {

            //create a new horizonal linear layout
            LinearLayout tempLinLayout = new LinearLayout(this);
            tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

            //create a textView and name it
            TextView tempTextView = new TextView(this);
            tempTextView.setText(s);
            tempTextView.setLayoutParams(textParams);

            //create a button
            Button tempButton = new Button(this);
            tempButton.setText("Delete");
            tempButton.setLayoutParams(buttonParams);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get index of button that was clicked
                    int index = movieButtonList.indexOf(v);

                    //remove that item from all arraylists
                    movies.remove(index);
                    movieTextViewList.remove(index);
                    movieLayoutList.remove(index);
                    movieButtonList.remove(index);
                    movieLayout.removeView((View) v.getParent());
                }
            });

            tempLinLayout.addView(tempTextView);
            tempLinLayout.addView(tempButton);

            movieLayoutList.add(tempLinLayout);
            movieTextViewList.add(tempTextView);
            movieButtonList.add(tempButton);

            movieLayout.addView(tempLinLayout);
        }
    }

    private void setMovieVariables() {
        movieLayout = (LinearLayout) findViewById(R.id.movieLinear);
        movies = profile.getMovies();
        movieLayoutList = new ArrayList<>();
        movieTextViewList = new ArrayList<>();
        movieButtonList = new ArrayList<>();

        movieText = (EditText) findViewById(R.id.addMovieText);
        movieButton = (Button) findViewById(R.id.addMovieButton);
        movieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieText.getText().toString().equals("")){
                    return;
                }
                addMovie(movieText.getText().toString());
            }
        });
    }

    private void addMovie(String s) {
        movies.add(s);

        //create a new horizonal linear layout
        LinearLayout tempLinLayout = new LinearLayout(this);
        tempLinLayout.setOrientation(LinearLayout.HORIZONTAL);

        //create a textView and name it
        TextView tempTextView = new TextView(this);
        tempTextView.setText(s);
        tempTextView.setLayoutParams(textParams);

        //create a button
        Button tempButton = new Button(this);
        tempButton.setText("Delete");
        tempButton.setLayoutParams(buttonParams);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get index of button that was clicked
                int index = movieButtonList.indexOf(v);

                //remove that item from all arraylists
                movies.remove(index);
                movieTextViewList.remove(index);
                movieLayoutList.remove(index);
                movieButtonList.remove(index);
                movieLayout.removeView((View) v.getParent());
            }
        });

        tempLinLayout.addView(tempTextView);
        tempLinLayout.addView(tempButton);

        movieLayoutList.add(tempLinLayout);
        movieTextViewList.add(tempTextView);
        movieButtonList.add(tempButton);

        movieLayout.addView(tempLinLayout);
    }
    //*********************************

    //*********************************
    //assigns infovariables
    private void setInfoVariables() {
        name = (EditText) findViewById(R.id.nameText);
        name.setText(profile.getName());
        phone = (EditText) findViewById(R.id.phoneText);
        email = (EditText) findViewById(R.id.emailText);
    }
    //*********************************

    //hides keyboard on click outside of textview

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

}
