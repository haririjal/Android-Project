package com.sujan.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txtTextView1;
    private EditText edtEmail;
    private Button btnSubmit;
    private String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTextView1= findViewById(R.id.txtTextView1);
        edtEmail= findViewById(R.id.edtEmail);
        btnSubmit= findViewById(R.id.btnSubmit);
        value= getIntent().getStringExtra("Name");
        if (value!=null && !value.equals("")){
            Log.d("IntentValue", value);
        }

    }

}
