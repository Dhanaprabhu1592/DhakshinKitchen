package com.whistledevelopers.dhakshinkitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    public static ArrayList<ItemsModel> itemsModelList;
    //  List<ConfirmItemModel> confirmItemModel;
    RecyclerView recyclerView;
    Button btn_confirm;
    ItemAdapter itemAdapter;
    ActionBar actionBar;
    public static final String SHARED_PREF_NAME = "Dhakshin";

    private String[] itemList = new String[]{"Idly(4)", "Chappathi", "Poori", "Dosa", "Pongal", "Appam", "Mini Idly"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01520F")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sp.getString("reloadArray", null);

        if(!name.equals("")){
            if(name.equals("false")){
                itemsModelList = getModel();

            }else{
             itemsModelList=getModelUpdate();
            }
        }




        itemAdapter = new ItemAdapter(this, itemsModelList);
        recyclerView.setAdapter(itemAdapter);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("test", (Serializable) itemsModelList);
                startActivity(intent);
            }
        });


    }

    private ArrayList<ItemsModel> getModel() {
        ArrayList<ItemsModel> itemsModels = new ArrayList<>();
        for (int i = 0; i < itemList.length; i++) {

            ItemsModel model = new ItemsModel();
            model.setCount("0");
            model.setName(itemList[i]);
            model.setSelected(false);
            itemsModels.add(model);
        }
        return itemsModels;
    }
    private ArrayList<ItemsModel> getModelUpdate() {
        ArrayList<ItemsModel> itemsModels = new ArrayList<>();
        for (int i = 0; i < itemList.length; i++) {

            ItemsModel model = new ItemsModel();
            ItemAdapter itemAdapter=new ItemAdapter();
            model.setCount(OrderActivity.itemsModelList.get(i).getCount());
            model.setName(itemList[i]);
            itemsModels.add(model);
        }
        return itemsModels;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search);


        SearchView searchView = (SearchView) menuItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ItemAdapter itemAdapter = (ItemAdapter) recyclerView.getAdapter();
                Filter filter = itemAdapter.getFilter();
                filter.filter(newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

}
