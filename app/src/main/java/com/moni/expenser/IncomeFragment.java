package com.moni.expenser;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    String date;
    RecyclerView recyclerView;
    SQLiteDatabase database;
    DataDBHelper helper;
    DataAdapter adapter;
    Cursor cursor;
    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                        Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_income, container, false);
        if(getArguments()!=null){
            date=getArguments().getString("date");
        }
        FetchIncome fetchIncome = new FetchIncome();
        fetchIncome.execute();
        recyclerView = rootView.findViewById(R.id.recyclerIncome);

        //Inflate the layout for this fragment
        return rootView;

    }

private class FetchIncome extends AsyncTask<Void,Void,Void>{

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("async","background started");
        helper = new DataDBHelper(getActivity().getApplicationContext());
        database = helper.getReadableDatabase();
        Log.d("date",date);
        cursor = database.rawQuery("SELECT * FROM "+ DataContract.TableEntry.TABLE_NAME_INCOME+" WHERE "+ DataContract.TableEntry.COLUMN_DATE+" LIKE '%"+date+"'",null);
        Log.d("cursor",String.valueOf(cursor.getCount()));
        adapter = new DataAdapter(getActivity(),cursor,DataContract.TableEntry.TABLE_NAME_INCOME);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("rec","recViewCreated");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
}
