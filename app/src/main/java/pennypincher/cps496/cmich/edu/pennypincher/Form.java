package pennypincher.cps496.cmich.edu.pennypincher;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.Date;

public class Form extends AppCompatActivity {

    DBHandler db = new DBHandler(Form.this,"we",null,1);
    Spinner cat;
    ImageView thumb;
    EditText price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        thumb = (ImageView) findViewById(R.id.thumb);
        Intent extras = getIntent();
        setImage((Bitmap)extras.getParcelableExtra("BitMap"));
        cat = (Spinner) findViewById(R.id.category);
        price = (EditText) findViewById(R.id.price);
        Spinner cat = findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(adapter);
    }

    public void EnterData(View v){
        Boolean pass = false;
        int count = 0;
        if (cat.getSelectedItem().toString().equals("Select a Category")) {
            Toast.makeText(this,"Please Select a Category",Toast.LENGTH_SHORT).show();
            count++;
        }

        if (TextUtils.isEmpty(price.getText().toString())) {
            Toast.makeText(this,"Please enter a price.",Toast.LENGTH_SHORT).show();
            count++;
        }

        if (count == 0) pass = true;

        if (pass) {
            Double Amount = Double.parseDouble(price.getText().toString());
            Purchase newPurch = new Purchase();
            newPurch.setAmount(Amount);
            newPurch.setCategory(cat.getSelectedItem().toString());
            newPurch.setImagePath("/Img/");
            newPurch.setTimeOPurch(new Date().toString());
            Log.d("Amount",newPurch.GetAmount().toString());
            db.Insert(newPurch);
//            db.GetAllRecords();
            returnHome();
        }
    }

    public void setImage(Bitmap image) {
        /**
         * Sets ImageView
         */
        thumb.getLayoutParams().height = 500;
        thumb.getLayoutParams().width = 500;
        thumb.requestLayout();
        thumb.setImageBitmap(image);
    }

    public void returnHome(){
        /**
         * When the user taps the Submit button it will send them back to the MainActivity.
         */
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }
}
