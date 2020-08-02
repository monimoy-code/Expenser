package com.moni.expenser;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FillDetails extends AppCompatActivity {
    ImageView imageView,showimg;
    String putAmount,decision;
    TextView textView,showtxt;
    String date,receivedCat,putDesc;
    TextInputLayout amt,dsc;
    int receivedImg;
    DatePickerDialog picker;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);
        Toolbar toolbar2 = findViewById(R.id.toolbarFillDetails);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showimg = findViewById(R.id.detailsImage);
        showtxt = findViewById(R.id.detailsText);
        button = findViewById(R.id.entervalue);
        amt = findViewById(R.id.getAmount);
        dsc = findViewById(R.id.getDesc);
        Intent intent = getIntent();
        receivedCat = intent.getStringExtra("category");
        receivedImg = intent.getIntExtra("logo",0);
        decision = intent.getStringExtra("table");
        showimg.setImageResource(receivedImg);
        showtxt.setText(receivedCat);
        imageView = findViewById(R.id.setDate);
        textView = findViewById(R.id.showDate);
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        textView.setText(date);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(FillDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textView.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        date=textView.getText().toString();
                    }
                },year,month,day);
                picker.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateAmount() && validateMemo())
                {
                    Log.d("error msg 3:","sql entry");
                    if(decision.equals("expense")) {
                        BackgroundTask backgroundTask = new BackgroundTask(FillDetails.this);
                        backgroundTask.execute(date, receivedCat, putAmount, putDesc);
                        finish();
                    }
                    if(decision.equals("income")){
                        BackgroundTask2 backgroundTask2 = new BackgroundTask2(FillDetails.this);
                        backgroundTask2.execute(date, receivedCat, putAmount, putDesc);
                        finish();
                    }
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }


        });
    }
    private boolean validateAmount()
    {
        putAmount = amt.getEditText().getText().toString();
        if(putAmount.isEmpty())
        {
            amt.setError("Amount cannot be empty");
            return false;
        }
        else
        {
            amt.setError(null);
            return true;
        }
    }
    private boolean validateMemo()
    {
        putDesc = dsc.getEditText().getText().toString().trim();
        if(putDesc.isEmpty())
        {
            dsc.setError("Memo cannot be empty");
            return false;
        }
        else
        {
            dsc.setError(null);
            return true;
        }
    }
}

