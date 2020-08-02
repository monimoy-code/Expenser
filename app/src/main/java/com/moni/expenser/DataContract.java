package com.moni.expenser;

import android.provider.BaseColumns;

public class DataContract {
    private DataContract(){}
    public static final class TableEntry implements BaseColumns {
        public static final String TABLE_NAME = "ExpenseList";
        public static final String COLUMN_DATE = "Date";
        public static final String COLUMN_AMOUNT = "Amount";
        public static final String COLUMN_CATEGORY = "Category";
        public static final String COLUMN_DESC = "Description";
        public static final String TABLE_NAME_INCOME = "IncomeList";
    }
}
