package com.example.weatherapp;

import  android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.le.ScanResult;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.support.v4.app.FragmentActivity;

//-------------

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.Manifest;




public class MainActivity  extends AppCompatActivity {


    private EditText Email ;
    private EditText Password;
    private Button Login;
    private Button register;
    private TextView text ;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth ;

    //------------
    private final int LOCATION_REQUEST_CODE=2;
    private final int WIFI_REQUEST_CODE=3;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        askPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION_REQUEST_CODE);
        askPermission(Manifest.permission.ACCESS_WIFI_STATE,WIFI_REQUEST_CODE);


        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        text = (TextView) findViewById(R.id.text);

        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);

        Login = (Button) findViewById(R.id.Login);
        register=(Button)findViewById(R.id.register);

        progressDialog = new ProgressDialog(this);







        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String email = Email.getText().toString().trim();
               String password = Password.getText().toString().trim();

               if(email.isEmpty()){
                   Toast.makeText(MainActivity.this ,"PLease enter your Email", Toast.LENGTH_SHORT).show();
                   return;
               }else{

                   if(password.isEmpty()){
                       Toast.makeText(MainActivity.this ,"PLease enter your password", Toast.LENGTH_SHORT).show();
                       return;
                   }else{

                       progressDialog.setMessage("Signing in Please Wait.....");
                       progressDialog.show();

                       firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {

                              progressDialog.dismiss();
                               if(task.isSuccessful()){
                                   openList();
                               }else{
                                   Toast.makeText(MainActivity.this ,"PLease enter your correct data", Toast.LENGTH_SHORT).show();
                                   return;
                               }

                           }
                       });


                   }
               }

            }
        });


       //  Keep Usert Login in :

       //if (firebaseAuth.getCurrentUser() !=null){

         // openList();
        //}



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this , RegisterActivity.class);
                startActivity(intent);

            }
        });

    }


    public void openList(){

                Intent intent = new Intent( MainActivity.this , LActivity.class);
                startActivity(intent);

    }


    public void askPermission(String permission,int requestCode){

        if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{permission},requestCode) ;

        }else{
            Toast.makeText(this,"Permission is Already Granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

         switch(requestCode){

             case LOCATION_REQUEST_CODE:
                 if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                     Toast.makeText(this,"Location Permission Granted",Toast.LENGTH_SHORT).show();
                 }else{
                     Toast.makeText(this,"Location Permission Denied",Toast.LENGTH_SHORT).show();
                     askPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION_REQUEST_CODE);
                     return;
                 }
             case WIFI_REQUEST_CODE:
                 if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                     Toast.makeText(this,"Wifi Permission Granted",Toast.LENGTH_SHORT).show();
                 }else{
                     Toast.makeText(this,"Wifi Permission Denied",Toast.LENGTH_SHORT).show();
                     askPermission(Manifest.permission.CHANGE_WIFI_STATE,WIFI_REQUEST_CODE);
                     return;
                 }


         }

    }
}
