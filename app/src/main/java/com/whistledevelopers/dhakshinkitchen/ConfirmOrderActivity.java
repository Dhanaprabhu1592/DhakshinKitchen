package com.whistledevelopers.dhakshinkitchen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConfirmOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static Button btn_addItem,btn_placeorder;
    ProgressBar progressBar;
    String url="http://192.168.43.14/Dhakshin/public/create";
    public static final String SHARED_PREF_NAME = "Dhakshin";
    String dataList;
    String tableNum;
    String name;
    public static TextView txt_warning;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        btn_addItem=(Button)findViewById(R.id.add_item);
        btn_placeorder=(Button)findViewById(R.id.btn_placeorder);
        txt_warning=(TextView)findViewById(R.id.text_warning);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_confirm_order);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //ArrayList<String> test = getIntent().getStringArrayListExtra("test");
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
         name = sp.getString("type", null);


        Intent intent=getIntent();
        //List<ItemsModel> itemModels=(List<ItemsModel>) intent.getSerializableExtra("test");
        tableNum=intent.getExtras().getString("tableno");

        ConfirmItemAdapter confirmItemAdapter=new ConfirmItemAdapter(this,OrderActivity.itemsModelList);
        recyclerView.setAdapter(confirmItemAdapter);
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmOrderActivity.this,OrderActivity.class));
            }
        });

        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinute=rightNow.get(Calendar.MINUTE);
        int currentSeconds=rightNow.get(Calendar.SECOND);
        time=String.valueOf(currentHourIn24Format)+String.valueOf(currentMinute)+String.valueOf(currentSeconds);
        Toast.makeText(this, time, Toast.LENGTH_SHORT).show();



        Gson gson=new Gson();
        dataList=gson.toJson(OrderActivity.itemsModelList);
    }

    private void sendData() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("error").equals("false")){
                        showDialog();
                    //    Toast.makeText(ConfirmOrderActivity.this, "Order Placed SuccessFully", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ConfirmOrderActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                map.put("orderData",dataList);
                map.put("billAmount","");
                map.put("orderId",tableNum+time);
                map.put("type",name);
                map.put("tableNo",tableNum);

                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmOrderActivity.this);
        builder.setTitle(getString(R.string.app_name));

        builder.setMessage("Order Placed SuccessFully...");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });


        AlertDialog alert = builder.create();
        alert.show();
    }
}

