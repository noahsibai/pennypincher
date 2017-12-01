package pennypincher.cps496.cmich.edu.pennypincher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
    DBHandler db = new DBHandler(MainActivity.this, "we", null, 1);
    BudgetDBHandler bdb = new BudgetDBHandler(MainActivity.this, "we", null, 1);
    NavigationView navigationView;
    HomeFragment home;
    PurchasesFragment purch;
    BudgetFragment bud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setImageResource(R.drawable.ic_no_camera_24);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = view.getContext();
                Intent form = new Intent(c, Form.class);
                startActivity(form);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            home = new HomeFragment();
            FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
            Frag.replace(R.id.content, home).commit();
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        db.GetAllRecords();
        System.out.println(bdb.GetAllRecords());
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void enterData(View v) {
        double amount = Double.parseDouble(bud.amount.getText().toString());
        System.out.println(amount);
        if (amount == 0) {
            Toast.makeText(this, "Please enter a number for Set Budget.", Toast.LENGTH_SHORT);
        } else {
            SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yy hh:mm:ss a");
            Date cur = new Date();
            Budget newBud = new Budget(amount, dt.format(cur));
            bdb.Insert(newBud);
            System.out.println(bdb.GetAllRecords());
            bud = new BudgetFragment();
            FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
            Frag.replace(R.id.content, bud).commit();
        }
    }

    public void ClearDB(View v) {
        bdb.RemoveAllRecords();
        db.RemoveAllRecords();
        bud = new BudgetFragment();
        FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
        Frag.replace(R.id.content, bud).commit();
        db.GetAllRecords();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * This method grabs the image taken by the user and sends it to the form Activity.
         */
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent form = new Intent(this, Form.class);
            form.putExtra("BitMap", imageBitmap);
            startActivity(form);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            home = new HomeFragment();
            FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
            Frag.replace(R.id.content, home).commit();
        } else if (id == R.id.nav_receipts) {
            purch = new PurchasesFragment();
            FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
            Frag.replace(R.id.content, purch).commit();
        } else if (id == R.id.nav_set_Budget) {
            bud = new BudgetFragment();
            FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
            Frag.replace(R.id.content, bud).commit();
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
