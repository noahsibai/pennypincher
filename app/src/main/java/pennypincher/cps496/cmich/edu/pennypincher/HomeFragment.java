package pennypincher.cps496.cmich.edu.pennypincher;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ArrayList<CategoryInfo> catArray;
    DBHandler db;
    TableLayout tab;

    RoundCornerProgressBar progress1;

    double total_spent = 0;
    BudgetDBHandler bdb;
    TextView totperc;

    ProgressBar pb;
    ProgressBar pb2;
    ProgressBar pb3;
    ProgressBar pb4;
    ProgressBar pb5;
    ProgressBar pb6;
    ProgressBar pb7;

    TextView tvC1cat;
    TextView tvC1perc;
    TextView tvC1cat2;
    TextView tvC1perc2;
    TextView tvC1cat3;
    TextView tvC1perc3;
    TextView tvC1cat4;
    TextView tvC1perc4;
    TextView tvC1cat5;
    TextView tvC1perc5;
    TextView tvC1cat6;
    TextView tvC1perc6;
    TextView tvC1cat7;
    TextView tvC1perc7;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);


//  table
        catArray = db.CategoryInfo();
        StringBuilder Sbuilder = new StringBuilder();


        for(int i = 0; i < catArray.size();i++){
            Log.d("catArray" + i,"Category: " + catArray.get(i).getCategory() + " Amount: " + catArray.get(i).getAmount());
            Sbuilder.append("Category: " + catArray.get(i).getCategory()).append(" Amount: " + catArray.get(i).getAmount());
        }


        for(int i = 0; i < catArray.size(); i++){
            total_spent = total_spent + (catArray.get(i).getAmount());
        }

//  PrgBar
        bdb = new BudgetDBHandler(v.getContext(), "we", null, 1);

        Budget t = bdb.GetAllRecords();
        progress1 = v.findViewById(R.id.progress_1);
        //        set the percentage
        double perc = (total_spent / ((float)(t.GetAmount()))) * 100;
        totperc = v.findViewById(R.id.PERCENT);
        if(t.GetAmount() > 0){
            totperc.setText(String.format("%.0f", perc) + " %");
        } else{
            totperc.setText("Please Set a Budget");
        }

        progress1.setMax((float)(t.GetAmount()));
        int buf = (int)((float)(total_spent) * .95);
        progress1.setProgress(buf);
        progress1.setSecondaryProgress((float)(total_spent));


        if (perc >= 100){
            progress1.setProgressColor(Color.RED);
            progress1.setSecondaryProgressColor(Color.RED);
        } else {
            progress1.setProgressColor(Color.parseColor("#26cc42"));
            progress1.setSecondaryProgressColor(Color.parseColor("#92fcca"));
        }






//        categoryChart


//        TVs for info
        tvC1cat = v.findViewById(R.id.tv1);
        tvC1perc = v.findViewById(R.id.tv2);
        tvC1cat2 = v.findViewById(R.id.tv3);
        tvC1perc2 = v.findViewById(R.id.tv4);
        tvC1cat3 = v.findViewById(R.id.tv5);
        tvC1perc3 = v.findViewById(R.id.tv6);
        tvC1cat4 = v.findViewById(R.id.tv7);
        tvC1perc4 = v.findViewById(R.id.tv8);
        tvC1cat5 = v.findViewById(R.id.tv9);
        tvC1perc5 = v.findViewById(R.id.tv10);
        tvC1cat6 = v.findViewById(R.id.tv11);
        tvC1perc6 = v.findViewById(R.id.tv12);
        tvC1cat7 = v.findViewById(R.id.tv13);
        tvC1perc7 = v.findViewById(R.id.tv14);

        Collections.sort(catArray, new CustomComparator());
        Collections.reverse(catArray);

        if (1 <= catArray.size()) {
            v.findViewById(R.id.p0).setVisibility(View.VISIBLE);
        tvC1cat.setText(catArray.get(0).getCategory());
        double perc1 = ((catArray.get(0).getAmount()) / total_spent) * 100;
        Log.d("key ",String.format("%.0f", perc1));
        tvC1perc.setText(String.format("%.0f", perc1) + " %");
//        cat 1
        pb = v.findViewById(R.id.progressBar);
        Drawable d = new ProgressDrawable(0xff1ad1ff, 0xcc006680);
        pb.setProgressDrawable(d);
        pb.setProgress((int)(perc1));

        ProgressBarAnimation anim = new ProgressBarAnimation(pb, 0,(float)perc1 );
        anim.setDuration(1000);
        pb.startAnimation(anim);}

        if (2 <= catArray.size()) {
            v.findViewById(R.id.p1).setVisibility(View.VISIBLE);
            tvC1cat2.setText(catArray.get(1).getCategory());
        double perc2 = ((catArray.get(1).getAmount()) / total_spent) * 100;
        tvC1perc2.setText(String.format("%.0f", perc2) + " %");
//        cat 2
        pb2 = v.findViewById(R.id.progressBar2);
        Drawable d2 = new ProgressDrawable(0xf2ff8000, 0xccb50800);
        pb2.setProgressDrawable(d2);
        pb2.setProgress((int)(perc2));

        ProgressBarAnimation anim = new ProgressBarAnimation(pb2, 0,(float)perc2 );
        anim.setDuration(1000);
        pb2.startAnimation(anim);}

        if (3 <= catArray.size()) {
            v.findViewById(R.id.p2).setVisibility(View.VISIBLE);
            tvC1cat3.setText(catArray.get(2).getCategory());
        double perc3 = ((catArray.get(2).getAmount()) / total_spent) * 100;
        tvC1perc3.setText(String.format("%.0f", perc3) + " %");
//        cat 3
        pb3 = v.findViewById(R.id.progressBar3);
        Drawable d3 = new ProgressDrawable(0xdde81aff, 0xcc8a0099);
        pb3.setProgressDrawable(d3);
        pb3.setProgress((int)(perc3));

        ProgressBarAnimation anim = new ProgressBarAnimation(pb3, 0,(float)perc3 );
        anim.setDuration(1000);
        pb3.startAnimation(anim);}

        if (4 <= catArray.size()) {
            v.findViewById(R.id.p3).setVisibility(View.VISIBLE);
            tvC1cat4.setText(catArray.get(3).getCategory());
        double perc4 = ((catArray.get(3).getAmount()) / total_spent) * 100;
        tvC1perc4.setText(String.format("%.0f", perc4) + " %");
//        cat 4
        pb4 = v.findViewById(R.id.progressBar4);
        Drawable d4 = new ProgressDrawable(0xddff4d4f, 0xcc800002);
        pb4.setProgressDrawable(d4);
        pb4.setProgress((int)(perc4));

        ProgressBarAnimation anim = new ProgressBarAnimation(pb4, 0,(float)perc4 );
        anim.setDuration(1000);
        pb4.startAnimation(anim);}

        if (5 <= catArray.size()) {
            v.findViewById(R.id.p4).setVisibility(View.VISIBLE);
            tvC1cat5.setText(catArray.get(4).getCategory());
        double perc5 = ((catArray.get(4).getAmount()) / total_spent) * 100;
        tvC1perc5.setText(String.format("%.0f", perc5) + " %");
//        cat 5
        pb5 = v.findViewById(R.id.progressBar5);
        Drawable d5 = new ProgressDrawable(0xf2ff8000, 0xccb50800);
        pb5.setProgressDrawable(d5);
        pb5.setProgress((int)(perc5));

        ProgressBarAnimation anim = new ProgressBarAnimation(pb5, 0,(float)perc5 );
        anim.setDuration(1000);
        pb5.startAnimation(anim);}

        if (6 <= catArray.size()) {
            v.findViewById(R.id.p5).setVisibility(View.VISIBLE);
            tvC1cat6.setText(catArray.get(5).getCategory());
        double perc6 = ((catArray.get(5).getAmount()) / total_spent) * 100;
        tvC1perc6.setText(String.format("%.0f", perc6) + " %");
//        cat 6
        pb6 = v.findViewById(R.id.progressBar6);
        Drawable d6 = new ProgressDrawable(0xdde81aff, 0xcc8a0099);
        pb6.setProgressDrawable(d6);
        pb6.setProgress((int)(perc6));

        ProgressBarAnimation anim = new ProgressBarAnimation(pb6, 0,(float)perc6 );
        anim.setDuration(1000);
        pb6.startAnimation(anim);}

        if (7 <= catArray.size()) {
            v.findViewById(R.id.p6).setVisibility(View.VISIBLE);
            tvC1cat7.setText(catArray.get(6).getCategory());
        double perc7 = ((catArray.get(6).getAmount()) / total_spent) * 100;
        tvC1perc7.setText(String.format("%.0f", perc7) + " %");
//        cat 7
        pb7 = v.findViewById(R.id.progressBar7);
        Drawable d7 = new ProgressDrawable(0xddff4d4f, 0xcc800002);
        pb7.setProgressDrawable(d7);
        pb7.setProgress((int)(perc7));

        ProgressBarAnimation anim = new ProgressBarAnimation(pb7, 0,((float)perc7));
        anim.setDuration(1000);
        pb7.startAnimation(anim);}




        return v;
    }






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
        db = new DBHandler(getActivity(),"we",null,1);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

class CustomComparator implements Comparator<CategoryInfo> {
    @Override
    public int compare(CategoryInfo o1, CategoryInfo o2) {
        return o1.getAmount().compareTo(o2.getAmount());
    }
}