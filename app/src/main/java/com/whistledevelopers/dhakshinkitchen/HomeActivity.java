package com.whistledevelopers.dhakshinkitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_new,btn_ongoing,btn_history,btn_take_away;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREF_NAME = "Dhakshin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_new=(Button)findViewById(R.id.btn_new);
        btn_ongoing=(Button)findViewById(R.id.btn_ongoing);
        btn_history=(Button)findViewById(R.id.btn_history);
        btn_take_away=(Button)findViewById(R.id.take_away);
        btn_new.setOnClickListener(this);
        btn_ongoing.setOnClickListener(this);
        btn_history.setOnClickListener(this);
        btn_take_away.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("reloadArray", "false");

        editor.apply();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_new:
                startActivity(new Intent(HomeActivity.this,OrderActivity.class));
                break;
            case R.id.btn_ongoing:
                break;
            case R.id.btn_history:
                break;
            case R.id.take_away:
                break;
        }

    }
}
