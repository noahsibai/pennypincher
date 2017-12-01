package pennypincher.cps496.cmich.edu.pennypincher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import pennypincher.cps496.cmich.edu.pennypincher.dummy.PurchaseContent;

/**
 * Created by Noah on 11/30/2017.
 */

public class BudgetDBHandler extends SQLiteOpenHelper {

    //All Static variables
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "BudgetAppBudgetDB";

    //Table Name
    private static final String BUDGET_TABLE_DETAILS = "LOCAL_BUDGET";

    //Table Column Names
    private static final String KEY_BUDGET_AMOUNT = "BudgetAmount";
    private static final String KEY_PURCHASE_DATE = "PurchaseDate";


    public BudgetDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + BUDGET_TABLE_DETAILS + "("
                + KEY_BUDGET_AMOUNT + " REAL,"
                + KEY_PURCHASE_DATE + " TEXT PRIMARY KEY)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BUDGET_TABLE_DETAILS);
        onCreate(db);
    }

    public void Insert(Budget newBudget){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BUDGET_AMOUNT, newBudget.GetAmount());
        values.put(KEY_PURCHASE_DATE, newBudget.GetDate());
        db.insert(BUDGET_TABLE_DETAILS,null,values);
        db.close();
        Log.d("Budget",newBudget.toString());
    }

    public double GetAllRecords(){
        Log.d("All Records Start","Start");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BUDGET_TABLE_DETAILS,null);
        String out = "Fails";
        double retVal = 0;
        if(cursor.getCount() > 0) {
            cursor.moveToNext();
            retVal = cursor.getDouble(0);
            Budget budget = new Budget(cursor.getDouble(0), cursor.getString(1));
            cursor.close();
            db.close();
            System.out.println(ToString(budget));
            Log.d("All Records End","End");
            return retVal;
        }
        return 0;

    }

    public String ToString(Budget budget) {
        String c = budget.GetAmount() + " " + budget.GetDate();
        return c;
    }

    public void RemoveAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BUDGET_TABLE_DETAILS,null,null);
        Log.d("Deleted","Deleted Table");
    }


}