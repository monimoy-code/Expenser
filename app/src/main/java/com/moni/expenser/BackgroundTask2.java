package com.moni.expenser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

public class BackgroundTask2 extends AsyncTask<String,Void,String> {
    Context ctx;
    BackgroundTask2(Context context){
        this.ctx=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        DataDBHelper dataDBHelper = new DataDBHelper(ctx);
        String date = strings[0];
        String Category = strings[1];
        Float amount = Float.parseFloat(strings[2]);
        String desc = strings[3];
        SQLiteDatabase db = dataDBHelper.getWritableDatabase();
        dataDBHelper.addInfoIncome(db,date,Category,amount,desc);
        return "Row inserted!!!";
    }
}
