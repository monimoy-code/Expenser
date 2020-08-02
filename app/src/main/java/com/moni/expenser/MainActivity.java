package com.moni.expenser;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SQLiteDatabase mdatabase;
    static DataAdapter madapter;
    DataDBHelper mdbhelper;
    int month,year;
    TabLayout tabLayout;
    TabItem tabIncome,tabExpense;
    PageAdapter pageAdapter,mpageadapter;
    ViewPager viewPager;
    String date;
    TextView textView,showIncome,showExpense,showBalance;
    Calendar calendar;
    private FloatingActionButton fab_main, fab1_expense, fab2_income;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView textview_expense, textview_income;

    Boolean isOpen = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mdbhelper = new DataDBHelper(this);
        mdatabase = mdbhelper.getReadableDatabase();
        fab_main = findViewById(R.id.fab);
        fab1_expense = findViewById(R.id.fabExpense);
        fab2_income = findViewById(R.id.fabIncome);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        textview_expense = (TextView) findViewById(R.id.textview_expense);
        textview_income = (TextView) findViewById(R.id.textview_income);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    textview_expense.setVisibility(View.INVISIBLE);
                    textview_income.setVisibility(View.INVISIBLE);
                    fab2_income.startAnimation(fab_close);
                    fab1_expense.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fab2_income.setClickable(false);
                    fab1_expense.setClickable(false);
                    isOpen = false;
                } else {
                    textview_expense.setVisibility(View.VISIBLE);
                    textview_income.setVisibility(View.VISIBLE);
                    fab2_income.startAnimation(fab_open);
                    fab1_expense.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fab2_income.setClickable(true);
                    fab1_expense.setClickable(true);
                    isOpen = true;
                }

            }
        });
        fab2_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SelectActivityIncome.class);
                startActivity(intent);

            }
        });

        fab1_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),SelectActivity.class);
            startActivity(intent);

            }
        });


        textView = findViewById(R.id.dispDate);
        showIncome = findViewById(R.id.showIncome);
        showExpense = findViewById(R.id.showExpenses);
        showBalance = findViewById(R.id.showBalance);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        calendar = Calendar.getInstance();
        month = (calendar.get(Calendar.MONTH))+1;
        year = calendar.get(Calendar.YEAR);
        textView.setText(month+"/"+year);
        date = textView.getText().toString();
        showData();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPicker pd = new MonthYearPicker();
                pd.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textView.setText((month)+"/"+year);
                        date = textView.getText().toString();
                        viewPager.getAdapter().notifyDataSetChanged();
                        showData();
                    }
                });
                pd.show(getSupportFragmentManager(), "MonthYearPicker");
            }
        });
        showIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ShowChart.class);
                i.putExtra("type","income");
                i.putExtra("date",date);
                startActivity(i);
            }
        });
        showExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ShowChart.class);
                i.putExtra("type","expense");
                i.putExtra("date",date);
                startActivity(i);
            }
        });
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabIncome = findViewById(R.id.IncomeTab);
        tabExpense = findViewById(R.id.ExpenseTab);
        pageAdapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
   // private Cursor getAllItems()
   // {
    //    return mdatabase.rawQuery("SELECT * FROM "+ DataContract.TableEntry.TABLE_NAME+" WHERE "+ DataContract.TableEntry.COLUMN_DATE+" LIKE '%"+date+"'",null);
   //
   // }
    public void showData()
    {
        double total_expense=0,total_income=0,total_balance=0;
        String str;
        Cursor cursor = mdatabase.rawQuery("SELECT SUM("+DataContract.TableEntry.COLUMN_AMOUNT+") as TOTAL FROM "+DataContract.TableEntry.TABLE_NAME+" WHERE "+ DataContract.TableEntry.COLUMN_DATE+" LIKE '%"+date+"'",null);
        if(cursor.moveToFirst()) {
            total_expense = cursor.getDouble(cursor.getColumnIndex("TOTAL"));
            str = String.format("%.2f",total_expense);
            showExpense.setText(str);
        }
        Cursor cursor2 = mdatabase.rawQuery("SELECT SUM("+DataContract.TableEntry.COLUMN_AMOUNT+") as TOTAL FROM "+DataContract.TableEntry.TABLE_NAME_INCOME+" WHERE "+ DataContract.TableEntry.COLUMN_DATE+" LIKE '%"+date+"'",null);
        if(cursor2.moveToFirst()){
            total_income = cursor2.getDouble(cursor2.getColumnIndex("TOTAL"));
            str = String.format("%.2f",total_income);
            Log.d("income",String.valueOf(total_income));
            showIncome.setText(str);
        }
        total_balance = total_income-total_expense;
        str = String.format("%.2f",total_balance);
        showBalance.setText(str);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.chart) {
            Intent i = new Intent(this,ShowChart.class);
            i.putExtra("type","expense");
            i.putExtra("date",date);
            startActivity(i);

        } else if (id == R.id.settings) {
            Intent i = new Intent(this,SetNotification.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mdatabase.close();
    }
    public class PageAdapter extends FragmentStatePagerAdapter {
        private int TabCount;
        PageAdapter(FragmentManager fm, int TabCount){
            super(fm);
            this.TabCount=TabCount;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    Bundle bundle = new Bundle();
                    bundle.putString("date",date);
                    Log.d("bundle date",date);
                    IncomeFragment obj = new IncomeFragment();
                    obj.setArguments(bundle);
                    return obj;
                case 1:
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("date",date);
                    ExpenseFragment obj1 = new ExpenseFragment();
                    obj1.setArguments(bundle1);
                    return obj1;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return TabCount;
        }
    }

}
