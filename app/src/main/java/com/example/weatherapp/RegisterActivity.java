package com.example.weatherapp;

import  android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {



    private Button register;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confrim;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Firebase auth instance
          auth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confrim = (EditText) findViewById(R.id.confirm);





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(RegisterActivity.this);

                if (!username.getText().toString().isEmpty() ){

                    if (!email.getText().toString().isEmpty()){

                        if(!password.getText().toString().isEmpty()){

                            if(!confrim.getText().toString().isEmpty()) {

                                if(confrim.getText().toString().trim().equals(password.getText().toString().trim())) {
                                    String u = username.getText().toString().trim();
                                    String e = email.getText().toString().trim();
                                    String p = password.getText().toString().trim();

                                    auth.createUserWithEmailAndPassword(e, p)
                                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {

                                                    if (task.isSuccessful()) {

                                                        progressDialog.setMessage("Registering Pleas Wait....");
                                                        progressDialog.show();
                                                        Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                        Intent intent = new Intent( RegisterActivity.this , LActivity.class);
                                                        startActivity(intent);


                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();

                                                    }

                                                }
                                            });
                                }else{
                                    Toast.makeText(RegisterActivity.this ,"The Passwords you entered doesn't match", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            else{
                                Toast.makeText(RegisterActivity.this ,"PLease confrim your Password", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }else{
                            Toast.makeText(RegisterActivity.this ,"PLease enter your Password", Toast.LENGTH_SHORT).show();
                               return;
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this ,"PLease enter your Email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else{
                    Toast.makeText(RegisterActivity.this ,"PLease enter the Username", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });


    }

}
