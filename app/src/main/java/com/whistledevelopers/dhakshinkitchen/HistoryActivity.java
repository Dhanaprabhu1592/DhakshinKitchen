package com.whistledevelopers.dhakshinkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    List<OngoingModel> ongoingModels;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView text_message;
    Button btnBack;
    String url="http://192.168.43.14/Dhakshin/public/history";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        btnBack=(Button)findViewById(R.id.btn_back);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        text_message=(TextView)findViewById(R.id.text_messsge);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_ongoing);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ongoingModels = new ArrayList<>();

        getData();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistory=new Intent(HistoryActivity.this,HistoryActivity.class);
                intentHistory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHistory);

    }
        });

    }

    private void getData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String message=jsonObject.getString("error");
                    //Toast.makeText(OngoingActivity.this, message, Toast.LENGTH_SHORT).show();

                    JSONArray jsonArray=jsonObject.getJSONArray("history");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        ongoingModels.add(new OngoingModel(object.getString("type"),object.getString("status"),object.getString("orderId")));

                    }
                  HistoryAdapter ongoingAdapter=new HistoryAdapter(HistoryActivity.this,ongoingModels);
                    recyclerView.setAdapter(ongoingAdapter);
                    if(ongoingAdapter.getItemCount()!=0){
                        text_message.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(HistoryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
        progressBar.setVisibility(View.VISIBLE);
    }
}
