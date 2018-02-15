package pennypincher.cps496.cmich.edu.pennypincher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tony on 2/12/2018.
 */

public class BudgetHistoryDBHandler extends SQLiteOpenHelper{
    //All Static variables
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "BudgetHistoryDB";

    //Table Name
    private static final String BUDGET_TABLE_DETAILS = "BUDGET_HISTORY";

    //Table Column Names
    private static final String KEY_BH_START = "BudgetStartDate";
    private static final String KEY_BH_END = "BudgetEndDate";
    private static final String KEY_BH_STATUS = "BudgetStatus";
    private static final String KEY_BH_OVERUNDER = "OverUnder";


    public BudgetHistoryDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + BUDGET_TABLE_DETAILS + "("
                + KEY_BH_START + " TEXT PRIMARY KEY, "
                + KEY_BH_END + " TEXT, "
                + KEY_BH_STATUS + " TEXT, "
                + KEY_BH_OVERUNDER + " REAL"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BUDGET_TABLE_DETAILS);
        onCreate(db);
    }

    public String Insert(BudgetHistory newBudget){
        try{
            Log.d("InsideInsert", newBudget.toString());
            SQLiteDatabase db = this.getWritableDatabase();
            Log.d("ObjectValues",newBudget.toString());
            ContentValues values = new ContentValues();
            values.put(KEY_BH_START, newBudget.getStartDate());
            values.put(KEY_BH_END, newBudget.getEndDate());
            values.put(KEY_BH_STATUS, newBudget.getStatus());
            db.insert(BUDGET_TABLE_DETAILS,null,values);
            db.close();
        }catch (Exception e){
            Log.d("Exception", e.toString());
        }
        return newBudget.getStartDate();
    }

    public ArrayList<HistoryResult> GetAllRecords(){
        ArrayList<HistoryResult> histList = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BUDGET_TABLE_DETAILS,null);
        String out = "Fails";
        double retVal = 0;
        while(cursor.moveToNext()) {
            HistoryResult result = new HistoryResult();
            try{
                result.setStartDate(df.parse(cursor.getString(0)));
            }catch(Exception e){
                Log.d("BudgetHistoryStart","Exception: " + e);
            }
            try{
                result.setEndDate(dt.parse(cursor.getString(1)));
                Log.d("Cursor",cursor.getString(1));
            }catch(Exception e){
                Log.d("BudgetHistoryEnd","Exception: " + e);
            }
            result.setStatus(cursor.getString(2));
            result.setOverUnder(cursor.getDouble(3));
            histList.add(result);
        }
        cursor.close();
        db.close();
        return histList;
    }

    public void UpdateStatus(String update, String key){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + BUDGET_TABLE_DETAILS + " SET " + KEY_BH_STATUS + " = " + "'" + update + "'"
                + " WHERE " + KEY_BH_START + " = '" + key + "'";
        Log.d("SQLQuery", query);
        db.execSQL(query);
        db.close();
    }

    public void UpdateOverUnder(Double Amount,String Key){
        Log.d("Key",Key);
        SQLiteDatabase db = this.getWritableDatabase();
        String squery = "SELECT * FROM " + BUDGET_TABLE_DETAILS + " WHERE " + KEY_BH_START + " = '" + Key + "'";
        String query = "UPDATE " + BUDGET_TABLE_DETAILS + " SET " + KEY_BH_OVERUNDER + " = " + Math.round(Amount*100)/100
                + " WHERE " + KEY_BH_START + " = " +'"'+ Key + '"';
        db.execSQL(query);
        db.close();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(squery,null);
        cursor.moveToFirst();
        Log.d("QueryInfo", "Start: " + cursor.getString(0) + " End: " + cursor.getString(1)
                + " Status: " + cursor.getString(2) + " OverUnder: " + cursor.getDouble(3));
        db.close();
    }

    public HistoryResult MostRecenRecord(){
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        SQLiteDatabase db = this.getReadableDatabase();
        HistoryResult hr = new HistoryResult();
        String query = "SELECT * FROM " + BUDGET_TABLE_DETAILS + " ORDER BY " + KEY_BH_START + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToLast();
        try{
            hr.setStartDate(df.parse(cursor.getString(0)));
        }catch (Exception e){
            Log.d("MRRStart", e.toString());
        }
        try{
            hr.setEndDate(dt.parse(cursor.getString(1)));
        }catch (Exception e){
            Log.d("MRREnd", e.toString());
        }
        hr.setStatus(cursor.getString(2));
        hr.setOverUnder(cursor.getDouble(3));
        Log.d("HistResult",hr.toString());
        return hr;
    }

    public void RemoveAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BUDGET_TABLE_DETAILS,null,null);
    }
}
