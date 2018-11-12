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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    public static ArrayList<ItemsModel> itemsModelList;
    //  List<ConfirmItemModel> confirmItemModel;
    String url="http://192.168.43.14/Dhakshin/public/getMenu";
    RecyclerView recyclerView;
    Button btn_confirm;
    ItemAdapter itemAdapter;
    ActionBar actionBar;
    ProgressBar progressBar;
    ArrayList<ItemsModel> itemsModels;
    TextView text_message;
    public static final String SHARED_PREF_NAME = "Dhakshin";
    String[] tableNo = { "01", "02", "03", "04", "05","06","07","08"};
    String tableNum;

    private String[] itemList = new String[]{"Idly(4)", "Chappathi", "Poori", "Dosa", "Pongal", "Appam", "Mini Idly"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01520F")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        text_message=(TextView)findViewById(R.id.text_messsge);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        getData();

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sp.getString("reloadArray", null);
        Spinner spin = (Spinner) findViewById(R.id.spinner_table);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tableNo);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableNum=tableNo[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(!name.equals("")){
            if(name.equals("false")){
                getData();


            }else{
                getDataAgain();
            }
        }




        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("test", (Serializable) itemsModelList);
                intent.putExtra("tableno", tableNum);
                startActivity(intent);

            }
        });


    }

    public void getData(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    text_message.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONObject jsonObject=new JSONObject(response);
                    String message=jsonObject.getString("error");
                 //   Toast.makeText(OrderActivity.this, message, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray=jsonObject.getJSONArray("menu");
                    ArrayList arrayList=new ArrayList();

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        ItemsModel model=new ItemsModel();
                        model.setName(jsonObject1.getString("itemName"));
                        model.setCount("0");
                        model.setSelected(false);
                        arrayList.add(model);



                    }
                    itemsModelList=arrayList;
                    itemAdapter = new ItemAdapter(OrderActivity.this, itemsModelList);
                    recyclerView.setAdapter(itemAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void getDataAgain(){
       text_message.setVisibility(View.GONE);
        ArrayList arrayList=new ArrayList();
        for(int i=0;i<OrderActivity.itemsModelList.size();i++){
            ItemsModel model=new ItemsModel();
            // model.setName(jsonObject1.getString("itemName"));
            model.setName(OrderActivity.itemsModelList.get(i).getName());
            model.setCount(OrderActivity.itemsModelList.get(i).getCount());
            model.setSelected(OrderActivity.itemsModelList.get(i).isSelected());
            arrayList.add(model);



        }
        itemsModelList=arrayList;
        itemAdapter = new ItemAdapter(OrderActivity.this, itemsModelList);
        recyclerView.setAdapter(itemAdapter);


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
        for (int i = 0; i < OrderActivity.itemsModelList.size(); i++) {

            ItemsModel model = new ItemsModel();
            model.setCount(OrderActivity.itemsModelList.get(i).getCount());
            model.setName(OrderActivity.itemsModelList.get(i).getName());
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
