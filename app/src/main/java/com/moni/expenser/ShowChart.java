package com.moni.expenser;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ShowChart extends AppCompatActivity {
    PieChart pieChart;
    String type,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chart);
        pieChart = findViewById(R.id.piechart);
        Toolbar toolbar = findViewById(R.id.toolbarShowChart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        date = intent.getStringExtra("date");
        BackgroundTask3 task3 = new BackgroundTask3(this);
        task3.execute(type,date);

    }
    private class BackgroundTask3 extends AsyncTask<String,Void,String>{
        List<PieEntry> entries = new ArrayList<>();
        PieDataSet set;
        PieData data;
        Context c;
        BackgroundTask3(Context ctx){
            c=ctx;
        }

        @Override
        protected String doInBackground(String... strings) {
            String t = strings[0];
            String d = strings[1];
            DataDBHelper dbHelper = new DataDBHelper(c);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            if(t.equals("income")){
                Cursor cursor = database.rawQuery("SELECT "+DataContract.TableEntry.COLUMN_CATEGORY+", SUM("+DataContract.TableEntry.COLUMN_AMOUNT+") "+" FROM "+DataContract.TableEntry.TABLE_NAME_INCOME+" WHERE "+ DataContract.TableEntry.COLUMN_DATE+" LIKE '%"+d+"'"+" GROUP BY "+DataContract.TableEntry.COLUMN_CATEGORY,null);
                if(cursor!=null && cursor.getCount()!=0){
                    cursor.moveToFirst();
                    do{
                        entries.add(new PieEntry(cursor.getFloat(cursor.getColumnIndex("SUM("+DataContract.TableEntry.COLUMN_AMOUNT+")")),cursor.getString(cursor.getColumnIndex(DataContract.TableEntry.COLUMN_CATEGORY))));
                    }while (cursor.moveToNext());
                    set = new PieDataSet(entries,"Income");
                    set.setColors(ColorTemplate.JOYFUL_COLORS);
                    data = new PieData(set);
                    data.setValueTextSize(13f);

                }
            }
            if(t.equals("expense")){
                Cursor cursor = database.rawQuery("SELECT "+DataContract.TableEntry.COLUMN_CATEGORY+", SUM("+DataContract.TableEntry.COLUMN_AMOUNT+") "+" FROM "+DataContract.TableEntry.TABLE_NAME+" WHERE "+ DataContract.TableEntry.COLUMN_DATE+" LIKE '%"+d+"'"+" GROUP BY "+DataContract.TableEntry.COLUMN_CATEGORY,null);
                if(cursor!=null && cursor.getCount()!=0){
                    cursor.moveToFirst();
                    do{
                        entries.add(new PieEntry(cursor.getFloat(cursor.getColumnIndex("SUM("+DataContract.TableEntry.COLUMN_AMOUNT+")")),cursor.getString(cursor.getColumnIndex(DataContract.TableEntry.COLUMN_CATEGORY))));
                    }while (cursor.moveToNext());
                    set = new PieDataSet(entries,"Expense");
                    set.setColors(ColorTemplate.JOYFUL_COLORS);
                    data = new PieData(set);
                }
            }
            return "Success";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pieChart.setData(data);
            pieChart.animateXY(2000,2000);
            pieChart.invalidate();
        }
    }
}
