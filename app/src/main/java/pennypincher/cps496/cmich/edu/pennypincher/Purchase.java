package pennypincher.cps496.cmich.edu.pennypincher;

import android.media.Image;

import java.util.Date;

/**
 * Created by Tony on 11/11/2017.
 */

public class Purchase {
    private Double Amount;
    private String Category;
    private String ImagePath;
    private String TimeOPurch;

    public void setAmount(Double dub){
        Amount = dub;
    }

    public  void setCategory(String cat){
        Category = cat;
    }

    public void setImagePath(String img){
        ImagePath = img;
    }

    public void setTimeOPurch(String time){
        TimeOPurch = time;
    }

    public Double GetAmount(){
        return Amount;
    }

    public String GetCategory(){
        return Category;
    }

    public String GetImagePath(){
        return ImagePath;
    }

    public String GetTOP(){
        return TimeOPurch;
    }

    @Override
    public String toString(){
        return "Amt: " + Amount + " Cat: " + Category + " ImagePath: " + ImagePath + " TOP: " + TimeOPurch;
    }
}