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
 * Created by Tony on 11/11/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    //All Static variables
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "BudgetAppDB";

    //Table Name
    private static final String BUDGET_TABLE_DETAILS = "LOCAL_BUDGET";

    //Table Column Names
    private static final String KEY_CATEGORY = "Category";
    private static final String KEY_AMOUNT = "Amount";
    private static final String KEY_PURCHASE_DATE = "PurchaseDate";
    private static final String KEY_IMG_PATH = "ImagePath";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + BUDGET_TABLE_DETAILS + "("
                + KEY_AMOUNT + " REAL,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_IMG_PATH + " TEXT,"
                + KEY_PURCHASE_DATE + " TEXT PRIMARY KEY)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + BUDGET_TABLE_DETAILS);

        onCreate(db);

    }

    public void Insert(Purchase newPurch){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_AMOUNT, newPurch.GetAmount());
        values.put(KEY_CATEGORY,newPurch.GetCategory());
        values.put(KEY_IMG_PATH, newPurch.GetByteImage());
        values.put(KEY_PURCHASE_DATE, newPurch.GetTOP());
        db.insert(BUDGET_TABLE_DETAILS,null,values);
        db.close();

        Log.d("Purch",newPurch.toString());

    }
    //Not sure if it is inserting data or not but causes error everytime I get to Categories
    //Log statement.
    public void GetAllRecords(){
        Log.d("All Records Start","Start");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BUDGET_TABLE_DETAILS,null);
        ArrayList<Purchase> Purchases = new ArrayList<Purchase>();
        Purchase SingPurch;

        if(cursor.getCount() > 0){
            for(int i = 0; i < cursor.getCount(); i ++){
                cursor.moveToNext();
                SingPurch = new Purchase();
                SingPurch.setAmount(cursor.getDouble(0));
                SingPurch.setCategory(cursor.getString(1));
                SingPurch.setImagePath(cursor.getBlob(2));
                SingPurch.setTimeOPurch(cursor.getString(3));
                Purchases.add(SingPurch);
            }
        }
        cursor.close();
        PurchaseContent.ITEMS.clear();
        for (int i = 0; i < Purchases.size(); i++ ){
            PurchaseContent.ITEMS.add(Purchases.get(i));
        }

        db.close();

        Log.d("All Records End","End");
    }

    public void RemoveAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BUDGET_TABLE_DETAILS,null,null);
        Log.d("Deleted","Deleted Table");
    }
}