package com.whistledevelopers.dhakshinkitchen.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.whistledevelopers.dhakshinkitchen.adapter.ConfirmItemAdapter;
import com.whistledevelopers.dhakshinkitchen.R;
import com.whistledevelopers.dhakshinkitchen.model.ItemsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static Button btn_addItem,btn_placeorder;
    ProgressBar progressBar;
    //String url="http://192.168.43.14/Dhakshin/public/create";
    String url="http://dhakshin.victoryschool.in/create";
   public static List<ItemsModel> confirmOrderList;

    public static final String SHARED_PREF_NAME = "Dhakshin";
    public static String dataList;
    String tableNum;
    String name;
    public static TextView txt_warning;
    String time;
    ImageView back_confirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        btn_addItem=(Button)findViewById(R.id.add_item);
        btn_placeorder=(Button)findViewById(R.id.btn_placeorder);
        txt_warning=(TextView)findViewById(R.id.text_warning);
        back_confirm_btn=(ImageView)findViewById(R.id.back_confirm_btn);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_confirm_order);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        confirmOrderList=new ArrayList<>();
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
         name = sp.getString("type", null);

        btn_placeorder.setEnabled(false);
        Intent intent=getIntent();
        //List<ItemsModel> itemModels=(List<ItemsModel>) intent.getSerializableExtra("test");
        tableNum=intent.getExtras().getString("tableno");
        if(tableNum==null){
            tableNum="";
        }

        ConfirmItemAdapter confirmItemAdapter=new ConfirmItemAdapter(this, OrderActivity.itemsModelList);
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

        back_confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack=new Intent(ConfirmOrderActivity.this,OrderActivity.class);
                intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentBack);

            }
        });
        //Toast.makeText(this, time, Toast.LENGTH_SHORT).show();



       /* for(int i=0;i<OrderActivity.itemsModelList.size();i++){
            ItemsModel model=new ItemsModel();
            if(!OrderActivity.itemsModelList.get(i).getCount().equals("0"))
            // model.setName(jsonObject1.getString("itemName"));
            model.setName(OrderActivity.itemsModelList.get(i).getName());
            model.setCount(OrderActivity.itemsModelList.get(i).getCount());
            //model.setSelected(OrderActivity.itemsModelList.get(i).isSelected());
            confirmOrderList.add(model);
        }
       */  }

    private void sendData() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("error").equals("false")){
                        showDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);

                Toast.makeText(ConfirmOrderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                Log.d("DataList",dataList);
                map.put("orderData",dataList);
                map.put("billAmount","");
                map.put("orderId",tableNum+time);
                map.put("type",name);
                map.put("tableNo",tableNum);

                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,6,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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

