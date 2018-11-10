package com.whistledevelopers.dhakshinkitchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btn_addItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        btn_addItem=(Button)findViewById(R.id.add_item);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_confirm_order);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //ArrayList<String> test = getIntent().getStringArrayListExtra("test");


        Intent intent=getIntent();
        List<ItemsModel> itemModels=(List<ItemsModel>) intent.getSerializableExtra("test");

        ConfirmItemAdapter confirmItemAdapter=new ConfirmItemAdapter(this,itemModels);
        recyclerView.setAdapter(confirmItemAdapter);
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmOrderActivity.this,OrderActivity.class));
            }
        });



    }
}
