package pennypincher.cps496.cmich.edu.pennypincher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class Form extends AppCompatActivity {

    DBHandler db = new DBHandler(Form.this, "we", null, 1);
    Spinner cat;
    ImageView thumb;
    EditText price;
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        thumb = findViewById(R.id.thumb);
        Intent extras = getIntent();
        img = extras.getParcelableExtra("BitMap");

        if (img == null) {
            img = BitmapFactory.decodeResource(Form.this.getResources(), R.drawable.nopictureoption);
        }
        setImageView(img);
        cat = findViewById(R.id.category);
        price = findViewById(R.id.price);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(adapter);
        Toasty.Config.getInstance()
                .setErrorColor(Color.RED)
                .setInfoColor(Color.BLUE)
                .setSuccessColor(Color.GREEN)
                .setWarningColor(Color.YELLOW)
                .setTextColor(Color.WHITE)
                .tintIcon(false)
                .setTextSize(20).apply();
    }

    public void EnterData(View v) {
        Boolean pass = false;
        int count = 0;
        if (cat.getSelectedItem().toString().equals("Select a Category")) {
            Toasty.error(this, "Please Select a Category",
                    Toast.LENGTH_SHORT, true).show();
            count++;
        }

        if (TextUtils.isEmpty(price.getText().toString())) {
            Toasty.error(this, "Please enter a price.",
                    Toast.LENGTH_SHORT, true).show();
            count++;
        }

        if (count == 0) pass = true;

        if (pass) {
            Double Amount = Double.parseDouble(price.getText().toString());
            Purchase newPurch = new Purchase();
            newPurch.setAmount(Amount);
            newPurch.setCategory(cat.getSelectedItem().toString());
            newPurch.setImagePath(getBytes(img));
            SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yy hh:mm:ss a");
            Date cur = new Date();
            newPurch.setTimeOPurch(dt.format(cur));
            Log.d("Amount", newPurch.GetAmount().toString());
            db.Insert(newPurch);
            db.GetAllRecords();
            Toasty.success(this, "Submited.",
                    Toast.LENGTH_SHORT, true).show();
            returnHome();
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public void setImageView(Bitmap image) {
        /**
         * Sets ImageView
         */
        thumb.getLayoutParams().height = 500;
        thumb.getLayoutParams().width = 500;
        thumb.requestLayout();
        thumb.setImageBitmap(image);
    }

    public void returnHome() {
        /**
         * When the user taps the Submit button it will send them back to the MainActivity.
         */
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }
}
