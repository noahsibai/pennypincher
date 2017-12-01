package pennypincher.cps496.cmich.edu.pennypincher;

/**
 * Created by noah on 11/17/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ssaurel on 02/12/2016.
 */
public class SplashActivity extends AppCompatActivity {
    DBHandler db = new DBHandler(SplashActivity.this, "we", null, 1);
    BudgetDBHandler bdb = new BudgetDBHandler(SplashActivity.this, "we", null, 1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /**
         * This will start the MainActivity once it is completely loaded.
         */
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        checkDates();
        startActivity(intent);
        finish();
    }


    public void checkDates(){
        Date cur = new Date();
        Budget bud = bdb.GetAllRecords();
        if (bud.GetDate() != null){
            DateFormat df = new SimpleDateFormat("MM-dd-yy");
            String check = bud.GetDate();
            Date startDate = null;
            try {
                startDate = df.parse(check);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yy");
            System.out.println(dt.format(startDate));

            if(cur.after(startDate) || cur.equals(startDate)){
                bdb.RemoveAllRecords();
                db.RemoveAllRecords();
            }
        } else {
            System.out.println("Winning");
        }

    }
}