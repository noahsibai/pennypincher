package pennypincher.cps496.cmich.edu.pennypincher;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;

import es.dmoral.toasty.Toasty;

public class ReceiptItem extends AppCompatActivity {
    Bitmap img;
    double amount;
    String catString;
    String TOP;
    ImageView imageView;
    Spinner cat;
    EditText amountView;
    int id;

    DBHandler db = new DBHandler(ReceiptItem.this, "we", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_item);
        Bundle extras = getIntent().getExtras();
        Intent in = getIntent();
        imageView = findViewById(R.id.img);
        img = in.getParcelableExtra("Image");
        amount = extras.getDouble("amount");
        catString = extras.getString("cat");
        TOP = extras.getString("TOP");
        cat = findViewById(R.id.category);
        amountView = findViewById(R.id.editAmount);
        amountView.setText(String.valueOf(amount));
        id = extras.getInt("id");
        setImage(img);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(adapter);
        cat.setSelection(adapter.getPosition(catString));
        Toasty.Config.getInstance()
                .setErrorColor(Color.RED)
                .setInfoColor(Color.BLUE)
                .setSuccessColor(Color.GREEN)
                .setWarningColor(Color.YELLOW)
                .setTextColor(Color.WHITE)
                .tintIcon(false)
                .setTextSize(20).apply();
        System.out.println(amount + " " + cat + " " + TOP);
    }

    public void setImage(Bitmap img){
        imageView.getLayoutParams().height = 800;
        imageView.getLayoutParams().width = 800;
        imageView.requestLayout();
        imageView.setImageBitmap(img);
    }

    public void update(View v){
        ContentValues cv = new ContentValues();
        cv.put("Amount",amount);
        cv.put("Category",catString); //These Fields should be your String values of actual column names
        cv.put("ImagePath",getBytes(img));
        cv.put("PurchaseDate",TOP);
        db.update(cv,TOP);
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);

    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public void delete(View v){

    }

}
