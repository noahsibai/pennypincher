package pennypincher.cps496.cmich.edu.pennypincher;

import android.content.Context;
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
        Context mContext = v.getContext();
        tab = v.findViewById(R.id.Table);
        catArray = db.CategoryInfo();
        StringBuilder Sbuilder = new StringBuilder();
        for(int i = 0; i < catArray.size();i++){
            Log.d("catArray" + i,"Category: " + catArray.get(i).getCategory() + " Amount: " + catArray.get(i).getAmount());
            Sbuilder.append("Category: " + catArray.get(i).getCategory()).append(" Amount: " + catArray.get(i).getAmount());
        }

        for(int i = 0; i < catArray.size(); i++){
            total_spent = total_spent + (catArray.get(i).getAmount());
            TableRow tr = new TableRow(mContext);
            TextView tv = new TextView(mContext);
            TextView tv2 = new TextView(mContext);
            tv.setText(catArray.get(i).getCategory());
            tv2.setText(String.format("%.2f",catArray.get(i).getAmount()));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PT,15);
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PT, 15);
            tr.addView(tv);
            tr.addView(tv2);
            tab.addView(tr);
        }

//  PrgBar
        bdb = new BudgetDBHandler(v.getContext(), "we", null, 1);

        Budget t = bdb.GetAllRecords();
        progress1 = v.findViewById(R.id.progress_1);

//        set the percentage
        double perc = (total_spent / ((float)(t.GetAmount()))) * 100;
        totperc = v.findViewById(R.id.PERCENT);
        totperc.setText(String.format("%.0f", perc) + " %");

        progress1.setMax((float)(t.GetAmount()));
        int buf = (int)((float)(total_spent) * .95);
        progress1.setProgress(buf);
        progress1.setSecondaryProgress((float)(total_spent));

//        categoryChart

        loadCats(v);


        return v;
    }


    public void loadCats(View v) {

//        load in stats
        bdb = new BudgetDBHandler(v.getContext(), "we", null, 1);

        Budget budget = bdb.GetAllRecords();

//        TVs for info
        TextView tvC1cat = v.findViewById(R.id.tv1);
        tvC1cat.setText("text?");
        TextView tvC1perc = v.findViewById(R.id.tv2);
        TextView tvC2cat = v.findViewById(R.id.tv3);
        TextView tvC2perc = v.findViewById(R.id.tv4);

//        cat 1
        pb = v.findViewById(R.id.progressBar);
        Drawable d = new ProgressDrawable(0xff1ad1ff, 0xcc006680);
        pb.setProgressDrawable(d);
        pb.setProgress(20);
//        cat 2
        pb2 = v.findViewById(R.id.progressBar2);
        Drawable d2 = new ProgressDrawable(0xf2ff8000, 0xccb50800);
        pb2.setProgressDrawable(d2);
        pb2.setProgress(60);
//        cat 3
        pb3 = v.findViewById(R.id.progressBar3);
        Drawable d3 = new ProgressDrawable(0xdde81aff, 0xcc8a0099);
        pb3.setProgressDrawable(d3);
        pb3.setProgress(12);
//        cat 4
        pb4 = v.findViewById(R.id.progressBar4);
        Drawable d4 = new ProgressDrawable(0xddff4d4f, 0xcc800002);
        pb4.setProgressDrawable(d4);
        pb4.setProgress(88);
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