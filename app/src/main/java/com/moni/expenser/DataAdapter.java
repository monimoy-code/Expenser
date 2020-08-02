package com.moni.expenser;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    String[] categories = {"Food", "Bills", "Transportation", "Home", "Car", "Entertainment", "Shopping", "Clothing", "Insurance", "Tax", "Telephone",
            "Cigarette", "Health", "Sport", "Baby", "Pet", "Beauty", "Electronics", "Drinks", "Snacks", "Gifts", "Social", "Travel", "Education", "Books", "Office", "Others","Salary","Awards","Grants","Rental","Refund","Coupon","Lottery","Investments"};
    int[] Logos = {R.drawable.ic_dish, R.drawable.ic_bill, R.drawable.ic_bus, R.drawable.ic_house, R.drawable.ic_car, R.drawable.ic_tickets,
            R.drawable.ic_shopping_cart, R.drawable.ic_suit, R.drawable.ic_life_insurance, R.drawable.ic_taxes, R.drawable.ic_digital,
            R.drawable.ic_cigarette, R.drawable.ic_stethoscope, R.drawable.ic_runner, R.drawable.ic_baby_boy, R.drawable.ic_dog, R.drawable.ic_cosmetics, R.drawable.ic_responsive,
            R.drawable.ic_beer, R.drawable.ic_snack, R.drawable.ic_gift, R.drawable.ic_social_care, R.drawable.ic_suitcase, R.drawable.ic_graduated, R.drawable.ic_book,
            R.drawable.ic_town, R.drawable.ic_menu,R.drawable.ic_wages,R.drawable.ic_reward,R.drawable.ic_book_one,R.drawable.ic_rental,R.drawable.ic_refund,
            R.drawable.ic_coupon,R.drawable.ic_winner,R.drawable.ic_coins};

    private Context mcontext;
    private Cursor mcursor;
    String mTable;
    public DataAdapter(Context context, Cursor cursor,String Table)
    {
        mcontext = context;
        mcursor = cursor;
        mTable = Table;
    }


    public class DataViewHolder extends RecyclerView.ViewHolder{
        public TextView moneyText;
        public TextView dateText;
        public TextView Cattext;
        public ImageView Img;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            moneyText = itemView.findViewById(R.id.recAmount);
            dateText = itemView.findViewById(R.id.recDate);
            Cattext = itemView.findViewById(R.id.recCategory);
            Img = itemView.findViewById(R.id.recImg);
        }
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.recycler_list_item,viewGroup,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {
        if (!mcursor.moveToPosition(i)){
            return;
        }
        final int id = mcursor.getInt(mcursor.getColumnIndex(DataContract.TableEntry._ID));
        final String date = mcursor.getString(mcursor.getColumnIndex(DataContract.TableEntry.COLUMN_DATE));
        final Float amount = mcursor.getFloat(mcursor.getColumnIndex(DataContract.TableEntry.COLUMN_AMOUNT));
        final String category = mcursor.getString(mcursor.getColumnIndex(DataContract.TableEntry.COLUMN_CATEGORY));
        final String desc = mcursor.getString(mcursor.getColumnIndex(DataContract.TableEntry.COLUMN_DESC));
        dataViewHolder.Cattext.setText(category);
        dataViewHolder.dateText.setText(date);
        String str = String.format("%.2f",amount);
        dataViewHolder.moneyText.setText(str);
        for(int j = 0;j<categories.length;j++)
        {
            if(categories[j].equals(category))
            {
                dataViewHolder.Img.setImageResource(Logos[j]);
                break;
            }
        }
        dataViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,ExpandedData.class);
                intent.putExtra("Category",category);
                intent.putExtra("Date",date);
                intent.putExtra("Amount",amount);
                intent.putExtra("Description",desc);
                intent.putExtra("ID",id);
                intent.putExtra("Table Name",mTable);
                mcontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mcursor.getCount();
    }
    public void swapCursor(Cursor newCursor)
    {
        if(mcursor!=null){
            mcursor.close();
        }
        mcursor = newCursor;
        if(newCursor!=null){
            notifyDataSetChanged();
        }
    }
}
