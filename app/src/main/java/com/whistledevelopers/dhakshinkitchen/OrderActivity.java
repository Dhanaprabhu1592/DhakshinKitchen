package com.whistledevelopers.dhakshinkitchen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    List<ItemsModel> itemsModelList;
  //  List<ConfirmItemModel> confirmItemModel;
    RecyclerView recyclerView;
    Button btn_confirm;
    ItemAdapter itemAdapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01520F")));
        btn_confirm=(Button)findViewById(R.id.btn_confirm);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsModelList=new ArrayList<>();

//        confirmItemModel=new ArrayList<ConfirmItemModel>();

        itemsModelList.add(new ItemsModel("Idly","4"));
        itemsModelList.add(new ItemsModel("Pongal","1"));
        itemsModelList.add(new ItemsModel("Poori","4"));
        itemsModelList.add(new ItemsModel("Chappathi","4"));
        itemsModelList.add(new ItemsModel("Idiyappam","4"));
        itemsModelList.add(new ItemsModel("Appam","4"));
        itemsModelList.add(new ItemsModel("Idly","4"));
        itemsModelList.add(new ItemsModel("Pongal","1"));
        itemsModelList.add(new ItemsModel("Poori","4"));
        itemsModelList.add(new ItemsModel("Chappathi","4"));
        itemsModelList.add(new ItemsModel("Idiyappam","4"));
        itemsModelList.add(new ItemsModel("Appam","4"));


        /*confirmItemModel.add(new ConfirmItemModel("idly","1") );
        confirmItemModel.add(new ConfirmItemModel("idly","2") );
        confirmItemModel.add(new ConfirmItemModel("idly","3") );
        confirmItemModel.add(new ConfirmItemModel("idly","4") );
        confirmItemModel.add(new ConfirmItemModel("idly","5") );
        confirmItemModel.add(new ConfirmItemModel("idly","6") );
        confirmItemModel.add(new ConfirmItemModel("idly","7") );
        confirmItemModel.add(new ConfirmItemModel("idly","8") );
*/
        itemAdapter=new ItemAdapter(this,itemsModelList);
        recyclerView.setAdapter(itemAdapter);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this,ConfirmOrderActivity.class);
                intent.putExtra("test", (Serializable) itemAdapter.confirmItemModel);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        itemAdapter.confirmItemModel.clear();
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);
        MenuItem menuItem=menu.findItem(R.id.search);


        SearchView searchView=(SearchView) menuItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ItemAdapter itemAdapter= (ItemAdapter) recyclerView.getAdapter();
                Filter filter=itemAdapter.getFilter();
                filter.filter(newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

}
