package pennypincher.cps496.cmich.edu.pennypincher;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pennypincher.cps496.cmich.edu.pennypincher.dummy.PurchaseContent;

/**
 * Created by tony on 2/8/2018.
 */

public class ServReceiver extends BroadcastReceiver {
    String BH_KEY;
    @Override
    public void onReceive(Context context, Intent intent) {
        BudgetHistoryDBHandler bhdb = new BudgetHistoryDBHandler(context,"we",null,1);
        DBHandler db = new DBHandler(context, "we", null, 1);
        BudgetDBHandler bdb = new BudgetDBHandler(context, "we", null, 1);
        Date cur = new Date();
        Budget bud = bdb.GetAllRecords();
        if (bud.GetDate() != null){
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            Log.d("Splash","Splash Activity");
            String check = bud.GetDate();
            Date startDate = null;
            try {
                startDate = df.parse(check);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(cur.after(startDate) || cur.equals(startDate)){
                double total = 0;
                HistoryResult result = bhdb.MostRecenRecord();
                Log.d("MostRecent",result.toString());
                Budget b = bdb.GetAllRecords();
                Intent i = new Intent(context,NotificationActivity.class);
                ArrayList<CategoryInfo> catArray = db.CategoryInfo();
                for(int j = 0; j < catArray.size(); j++){
                    total = total + catArray.get(j).getAmount();
                }
                Log.d("total_spent",Double.toString(total));
                Log.d("BudgetServ",Double.toString(b.GetAmount()));
                BH_KEY = intent.getStringExtra("BH_LKEY");
                if(BH_KEY != null){
                    Log.d("BH_KEY",BH_KEY);
                    bhdb.UpdateOverUnder((b.GetAmount() - total),BH_KEY);
                }else{
                    Log.d("LastChecked",bhdb.MostRecenRecord().toString());
                }
                i.putExtra("total_spent",(float) total);
                i.putExtra("Budget", ((float) b.GetAmount()));
                PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.icon)
                                .setContentTitle("Penny Pincher")
                                .setContentText("After date")
                                .setContentIntent(pendingIntent);

                NotificationManager notmanager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
                notmanager.notify(1,mBuilder.build());

                bdb.RemoveAllRecords();
                db.RemoveAllRecords();
                db.close();
                bdb.close();
                bhdb.close();
                PurchaseContent.ITEMS.clear();
            }
        }
    }
}
