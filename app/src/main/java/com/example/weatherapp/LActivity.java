package com.example.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LActivity extends AppCompatActivity {


    private ListView List ;

    int[] IMAGES = {R.drawable.sql,R.drawable.java,R.drawable.js,R.drawable.csharp,R.drawable.python,R.drawable.cplus,R.drawable.group} ;

    String[] names = {"SQL", "JAVA", "JAVA SCRIPT","C#", "PYTHON","C++", "ABDO,SOBHY,BILLY BASHA"};

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


        CustomAdapter customAdapter = new CustomAdapter();
        List.setAdapter(customAdapter);


    }

    class  CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.customlayout,null);

            ImageView image = (ImageView) convertView.findViewById(R.id.image);
            TextView Name = (TextView)convertView.findViewById(R.id.Name) ;

            image.setImageResource(IMAGES [position]);
            Name.setText(names[position]);
            return convertView;
        }
    }
}
