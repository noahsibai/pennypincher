package pennypincher.cps496.cmich.edu.pennypincher;

import android.util.Log;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tony on 2/12/2018.
 */

public class BudgetHistory {
    private String StartDate;
    private String EndDate;
    private String Status;
    private Double OverUnder;

    public void setStartDate(Date start){
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
        try{
            this.StartDate = df.format(start);
        }catch(Exception e){

        }
        try{
            Date startDate = df.parse(new DateTime().toString("MM-dd-yyyy h:mm:ss a"));
            this.StartDate = df.format(startDate);
        }catch (Exception e){
            Log.d("Parse 1","Parser 1");
        }
    }

    public String getStartDate(){
        return this.StartDate;
    }

    public void setEndDate(Date end){
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        try{
            this.EndDate = df.format(end);
        }
        catch(Exception e) {
            Date endDate = new Date();
            this.EndDate = df.format(endDate);
        }
    }

    public String getEndDate(){
        return this.EndDate;
    }

    public void setStatus(String statmess){
        this.Status = statmess;
    }

    public String getStatus(){
        return this.Status;
    }
    //Display variable not insert.
    public void setOverUnder(Double Amount){
        this.OverUnder = Amount;
    }

    public Double getOverUnder(){
        return this.OverUnder;
    }

    public String toString(){
        return "Primary Key: " + this.StartDate + " End Date: " + this.EndDate + " Status: " + this.Status
                + " OverUnder: " + this.OverUnder;
    }
}
