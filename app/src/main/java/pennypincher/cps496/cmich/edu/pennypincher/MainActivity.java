package pennypincher.cps496.cmich.edu.pennypincher;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

@SuppressWarnings("ALL")
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
    SettingsFragment set;
    Date dtPicked;
    int year;
    int month;
    int day;
    boolean datePicked = false;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        db.GetAllRecords();
        System.out.println(bdb.GetAllRecords());
        Toasty.Config.getInstance()
                .setErrorColor(Color.RED)
                .setInfoColor(Color.BLUE)
                .setSuccessColor(Color.GREEN)
                .setWarningColor(Color.YELLOW)
                .setTextColor(Color.WHITE)
                .tintIcon(false)
                .setTextSize(20).apply();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void openDate(View v) {
        showDialog(DIALOG_ID);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, dpickerListner, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListner =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    year = year;
                    month = month + 1;
                    day = day;
                    DateFormat df = new SimpleDateFormat("MM-dd-yy");
                    String check = month + "-" + day + "-" + year;
                    try {
                        dtPicked = df.parse(check);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    datePicked = true;
                    TextView dateSelected = bud.getActivity().findViewById(R.id.dateSelected);
                    dateSelected.setText(check);
                }
            };


    public void enterData(View v) {
        Boolean pass = false;
        int count = 0;

        if (TextUtils.isEmpty(bud.amount.getText().toString())) {
            Toasty.error(this, "Please enter a number for Set Budget.",
                    Toast.LENGTH_SHORT, true).show();
//            Toast.makeText(bud.getContext(), "Please enter a number for Set Budget.", Toast.LENGTH_SHORT);
            count++;
        }
        if (!datePicked){
            Toasty.error(this, "Please Select a date.",
                    Toast.LENGTH_SHORT, true).show();
//            Toast.makeText(bud.getContext(), "Please Select a date", Toast.LENGTH_SHORT);
            count++;
        }
        if (count ==0 ) pass = true;
        if (pass) {
            double amount = Double.parseDouble(bud.amount.getText().toString());
            SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yy");
            System.out.println(dt.format(dtPicked));
            Budget newBud = new Budget(amount, dt.format(dtPicked));
            bdb.Insert(newBud);
            System.out.println(bdb.GetAllRecords());
            datePicked = false;
            Toasty.success(this, "Budget Set.",
                    Toast.LENGTH_SHORT, true).show();
            bud = new BudgetFragment();
            FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
            Frag.replace(R.id.content, bud).commit();
        }
    }

    public void ClearBudget(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Do you really want to to delete this budget?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        bdb.RemoveAllRecords();
                        bud = new BudgetFragment();
                        FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
                        Frag.replace(R.id.content, bud).commit();
                        db.GetAllRecords();
                        Toasty.success(MainActivity.this, "Budget has been deleted.",
                                Toast.LENGTH_SHORT, true).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toasty.warning(MainActivity.this, "Budget not deleted",
                                Toast.LENGTH_SHORT, true).show();

                    }
                }).show();

    }

    public void ClearDB(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Do you really want to to delete the entire of the Database" +
                        " If you do you will lose all of your receipts and budget as well.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        bdb.RemoveAllRecords();
                        db.RemoveAllRecords();
                        home = new HomeFragment();
                        FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
                        Frag.replace(R.id.content, home).commit();
                        db.GetAllRecords();
                        Toasty.success(MainActivity.this, "Budget and receipts" +
                                        " has been deleted.",
                                Toast.LENGTH_SHORT, true).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toasty.warning(MainActivity.this,
                                "Budget and receipts not deleted",
                                Toast.LENGTH_SHORT, true).show();
                    }
                }).show();

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
            set = new SettingsFragment();
            FragmentTransaction Frag = getSupportFragmentManager().beginTransaction();
            Frag.replace(R.id.content, set).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
