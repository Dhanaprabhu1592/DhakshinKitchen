package com.whistledevelopers.dhakshinkitchen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        tv = (TextView) findViewById(R.id.tv);

        for (int i = 0; i < 5; i++) {
            String text = tv.getText().toString();
            tv.setText(text + MainActivity.modelArrayList.get(i).getFruit()+" -> "+MainActivity.modelArrayList.get(i).getNumber()+"\n");
        }
    }
}
