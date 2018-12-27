package com.whistledevelopers.dhakshinkitchen.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.whistledevelopers.dhakshinkitchen.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_new,btn_ongoing,btn_history,btn_take_away;
    //SharedPreferences sharedpreferences;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
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

        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sp.edit();

        editor.putString("reloadArray", "false");

        editor.apply();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_new:
                editor.putString("type", "dining");

                editor.apply();

                startActivity(new Intent(HomeActivity.this,OrderActivity.class));
                break;
            case R.id.btn_ongoing:
                Intent intent=new Intent(HomeActivity.this,OngoingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btn_history:
                Intent intentHistory=new Intent(HomeActivity.this,HistoryActivity.class);
                intentHistory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHistory);
                break;
            case R.id.take_away:
                editor.putString("type", "takeaway");

                editor.apply();

                startActivity(new Intent(HomeActivity.this,OrderActivity.class));
                break;
        }

    }
}
