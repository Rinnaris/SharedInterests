package com.example.lastone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//Launches at the start of the app when no profile exists

public class StartUp extends AppCompatActivity {
    Context context;


    String name = "";
    String phone = "";
    String email = "";

    EditText nameText;
    EditText phoneText;
    EditText emailText;

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        View view = this.getWindow().getDecorView();

        context = this;

        nameText = (EditText) findViewById(R.id.categoryText);

        next = (Button) findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                name = nameText.getText() + "";
                //if there is no name don't let them move on
                if(name.equals("")){
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Name is required, use a username if you don't want to share your real name.", duration);
                    toast.show();
                    return;
                }
                phone = phoneText.getText() + "";
                email = emailText.getText() + "";

                Intent intent = new Intent(StartUp.this, Setup.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("phone", phone);
                bundle.putString("email", email);

                intent.putExtras(bundle);

                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Enter your favourites in the corresponding category, don't forget to click add after each one.", duration);
                toast.show();


                startActivity(intent);
            }
        });
    }


    //found this on stackoverflow
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
