package com.moni.expenser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SelectActivityIncome extends AppCompatActivity {
    ListView listView;
    String[] categories={"Salary","Awards","Grants","Rental","Refund","Coupon","Lottery","Investments","Others"};
    int[] Logos = {R.drawable.ic_wages,R.drawable.ic_reward,R.drawable.ic_book_one,R.drawable.ic_rental,R.drawable.ic_refund,
    R.drawable.ic_coupon,R.drawable.ic_winner,R.drawable.ic_coins,R.drawable.ic_menu};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_income);
        Toolbar toolbar1 = findViewById(R.id.toolbarSelectIncome);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listView1);
        CustomAdapter adapter = new CustomAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),FillDetails.class);
                intent.putExtra("category",categories[position]);
                intent.putExtra("logo",Logos[position]);
                intent.putExtra("table","income");
                startActivity(intent);
            }
        });

    }
    private class CustomAdapter extends BaseAdapter{
        TextView name;
        ImageView image;

        @Override
        public int getCount() {
            return categories.length;
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
