package com.example.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LActivity extends AppCompatActivity {


    private ListView List ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);




        List = (ListView) findViewById(R.id.List);

     List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Intent i = new Intent (LActivity.this, DeviceDetailActivity.class);
                     startActivity(i);
         }
     });



     String[] os = {"Android", "iPhone", "Windows","Bluckberry", "Linux"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,os);

        List.setAdapter(arrayAdapter);
    }
}
