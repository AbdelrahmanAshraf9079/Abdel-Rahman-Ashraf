package com.example.weatherapp;


import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class LActivity <wifiManager extends ScanResult> extends AppCompatActivity  {


    private ListView List ;
    private Button scanResult;


    private WifiManager wifiManager ;
    private java.util.List<android.net.wifi.ScanResult> results ;
    private ArrayList<String> arrayList = new ArrayList<>();


    int[] IMAGES = {R.drawable.esp,R.drawable.esps,R.drawable.epsa,R.drawable.espb,R.drawable.espc,R.drawable.espd,R.drawable.espe,R.drawable.espf} ;

    //String[] names = {"Esp1","Esp2"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);




        List = (ListView) findViewById(R.id.List);
        scanResult = (Button)findViewById(R.id.scanResult);

        scanResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              scanWifi();
            }
        });


        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(!wifiManager.isWifiEnabled()){

            Toast.makeText(this,"Wifi is disabled",Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
            scanWifi();
        }








     List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Intent i = new Intent (LActivity.this, DeviceDetailActivity.class);
                     startActivity(i);
         }
     });


//        scanWifi();
//        CustomAdapter customAdapter = new CustomAdapter();
//        List.setAdapter(customAdapter);

    }

    private void scanWifi(){

        arrayList.clear();
        registerReceiver(wifiReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Toast.makeText(this,"Scanning Wifi....",Toast.LENGTH_SHORT).show();


    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = wifiManager.getScanResults();
            unregisterReceiver(this);

           for (android.net.wifi.ScanResult scanResult:results){
               arrayList.add(scanResult.SSID + " - " + scanResult.capabilities);
//               CustomAdapter.notifyDataSetChanged();

            }
            CustomAdapter customAdapter = new CustomAdapter();
            List.setAdapter(customAdapter);

        }
    };




    class  CustomAdapter extends BaseAdapter {


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
            if (arrayList.size()!=0)
            Name.setText(arrayList.get(position));  //names[position]

            return convertView;
        }
    }
}
