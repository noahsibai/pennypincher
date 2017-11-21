package pennypincher.cps496.cmich.edu.pennypincher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.util.Date;

/**
 * Created by Tony on 11/11/2017.
 */

public class Purchase {
    private Double Amount;
    private String Category;
    public byte[] ImagePath;
    private String TimeOPurch;

    public void setAmount(Double dub){
        Amount = dub;
    }

    public  void setCategory(String cat){ Category = cat; }

    public void setImagePath(byte[] img){ ImagePath = img; }

    public void setTimeOPurch(String time){
        TimeOPurch = time;
    }

    public Double GetAmount(){
        return Amount;
    }

    public String GetCategory(){
        return Category;
    }

    public byte[] GetByteImage() { return ImagePath; }

    public Bitmap GetImagePath(){
        byte[] temp = GetByteImage();
        return BitmapFactory.decodeByteArray(temp, 0, temp.length);
    }

    public String GetTOP(){
        return TimeOPurch;
    }

    @Override
    public String toString(){
        return "Amt: " + Amount + " Cat: " + Category + " ImagePath: " + ImagePath + " TOP: " + TimeOPurch;
    }
}