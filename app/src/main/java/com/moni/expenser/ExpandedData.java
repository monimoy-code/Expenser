package com.moni.expenser;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandedData extends AppCompatActivity {
    String[] categories = {"Food", "Bills", "Transportation", "Home", "Car", "Entertainment", "Shopping", "Clothing", "Insurance", "Tax", "Telephone",
            "Cigarette", "Health", "Sport", "Baby", "Pet", "Beauty", "Electronics", "Drinks", "Snacks", "Gifts", "Social", "Travel", "Education", "Books", "Office", "Others","Salary","Awards","Grants","Rental","Refund","Coupon","Lottery","Investments"};
    int[] Logos = {R.drawable.ic_dish, R.drawable.ic_bill, R.drawable.ic_bus, R.drawable.ic_house, R.drawable.ic_car, R.drawable.ic_tickets,
            R.drawable.ic_shopping_cart, R.drawable.ic_suit, R.drawable.ic_life_insurance, R.drawable.ic_taxes, R.drawable.ic_digital,
            R.drawable.ic_cigarette, R.drawable.ic_stethoscope, R.drawable.ic_runner, R.drawable.ic_baby_boy, R.drawable.ic_dog, R.drawable.ic_cosmetics, R.drawable.ic_responsive,
            R.drawable.ic_beer, R.drawable.ic_snack, R.drawable.ic_gift, R.drawable.ic_social_care, R.drawable.ic_suitcase, R.drawable.ic_graduated, R.drawable.ic_book,
            R.drawable.ic_town, R.drawable.ic_menu,R.drawable.ic_wages,R.drawable.ic_reward,R.drawable.ic_book_one,R.drawable.ic_rental,R.drawable.ic_refund,
            R.drawable.ic_coupon,R.drawable.ic_winner,R.drawable.ic_coins};

    TextView amt,desc,dat,cat;
    String temp,table;
    Float temp2;
    int temp3,pos;
    ImageView img;
    SQLiteDatabase mdb;
    DataDBHelper mdbhlpr;
    FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_data);
        Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbarExpanded);
        setSupportActionBar(toolbar3);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img = findViewById(R.id.expImg);
        amt = findViewById(R.id.expMoney);
        desc = findViewById(R.id.expDesc);
        dat = findViewById(R.id.expDate);
        cat = findViewById(R.id.expCategory);
        final Intent intent = getIntent();
        temp=intent.getStringExtra("Category");
        cat.setText(temp);
        for(int i=0;i<categories.length;i++)
        {
            if(categories[i].equals(temp)){
                pos=i;
                break;
            }
        }
        img.setImageResource(Logos[pos]);
        temp=intent.getStringExtra("Date");
        dat.setText(temp);
        Bundle bundle=getIntent().getExtras();
        temp2 = bundle.getFloat("Amount");
        String str = String.format("%.2f",temp2);
        amt.setText(str);
        temp=intent.getStringExtra("Description");
        desc.setText(temp);
        button = findViewById(R.id.fabDel);
        temp3=bundle.getInt("ID");
        table = intent.getStringExtra("Table Name");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mdbhlpr = new DataDBHelper(ExpandedData.this);
            mdb = mdbhlpr.getWritableDatabase();
            if(table.equals(DataContract.TableEntry.TABLE_NAME)) {
                mdb.execSQL("DELETE FROM " + DataContract.TableEntry.TABLE_NAME + " WHERE " + DataContract.TableEntry._ID + " = " + temp3 + ";");
            }
            if(table.equals(DataContract.TableEntry.TABLE_NAME_INCOME)){
                mdb.execSQL("DELETE FROM " + DataContract.TableEntry.TABLE_NAME_INCOME + " WHERE " + DataContract.TableEntry._ID + " = " + temp3 + ";");
            }
            Intent intent5 = new Intent(ExpandedData.this,MainActivity.class);
            startActivity(intent5);
            }
        });
    }
}