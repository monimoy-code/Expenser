package com.moni.expenser;

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
import android.support.v7.widget.Toolbar;

public class SelectActivity extends AppCompatActivity {
    ListView listView;
    String[] categories = {"Food", "Bills", "Transportation", "Home", "Car", "Entertainment", "Shopping", "Clothing", "Insurance", "Tax", "Telephone",
            "Cigarette", "Health", "Sport", "Baby", "Pet", "Beauty", "Electronics", "Drinks", "Snacks", "Gifts", "Social", "Travel", "Education", "Books", "Office", "Others"};
    int[] Logos = {R.drawable.ic_dish, R.drawable.ic_bill, R.drawable.ic_bus, R.drawable.ic_house, R.drawable.ic_car, R.drawable.ic_tickets,
            R.drawable.ic_shopping_cart, R.drawable.ic_suit, R.drawable.ic_life_insurance, R.drawable.ic_taxes, R.drawable.ic_digital,
            R.drawable.ic_cigarette, R.drawable.ic_stethoscope, R.drawable.ic_runner, R.drawable.ic_baby_boy, R.drawable.ic_dog, R.drawable.ic_cosmetics, R.drawable.ic_responsive,
            R.drawable.ic_beer, R.drawable.ic_snack, R.drawable.ic_gift, R.drawable.ic_social_care, R.drawable.ic_suitcase, R.drawable.ic_graduated, R.drawable.ic_book,
            R.drawable.ic_town, R.drawable.ic_menu};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
            Toolbar toolbar1 = findViewById(R.id.toolbarSelect);
            setSupportActionBar(toolbar1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listView1);
        CustomAdapter customAdapter  = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),FillDetails.class);
                intent.putExtra("category",categories[position]);
                intent.putExtra("logo",Logos[position]);
                intent.putExtra("table","expense");
                startActivity(intent);
            }
        });

    }
    private class CustomAdapter extends BaseAdapter{
        TextView name;
        ImageView image;

        @Override
        public int getCount() {
            return Logos.length;
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
            View v1 = getLayoutInflater().inflate(R.layout.custom_list_element,parent,false);
            name = v1.findViewById(R.id.logoName);
            image = v1.findViewById(R.id.logoImage);
            name.setText(categories[position]);
            image.setImageResource(Logos[position]);
            return v1;
        }
    }

}
