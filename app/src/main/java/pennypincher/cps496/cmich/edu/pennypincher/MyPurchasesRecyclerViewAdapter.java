package pennypincher.cps496.cmich.edu.pennypincher;

/**
 * Created by noah on 11/20/17.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pennypincher.cps496.cmich.edu.pennypincher.PurchasesFragment.OnListFragmentInteractionListener;
import pennypincher.cps496.cmich.edu.pennypincher.dummy.PurchaseContent.PurchaseItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPurchasesRecyclerViewAdapter extends RecyclerView.Adapter<MyPurchasesRecyclerViewAdapter.ViewHolder> {
    private final List<Purchase> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPurchasesRecyclerViewAdapter(List<Purchase> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_purchases, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        String temp = String.format("Price: $%.2f", mValues.get(position).GetAmount());
        holder.AmountDisp.setText(temp);
        holder.CatDisp.setText("Category: " + mValues.get(position).GetCategory());
        holder.ImPath.getLayoutParams().height = 800;
        holder.ImPath.getLayoutParams().width = 800;
        holder.ImPath.requestLayout();
        holder.ImPath.setImageBitmap(mValues.get(position).GetImagePath());
        DateFormat df = new SimpleDateFormat("MM-dd-yy");
        String check = mValues.get(position).GetTOP();
        Date tmp = new Date();
        try {
            tmp = df.parse(check);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt = (SimpleDateFormat) df;
        holder.TOP.setText("Date Logged: " + dt.format(tmp));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                }
                //Onclick that enlarges the thumbnail to fullscreen activity
                Context context = v.getContext();
                Intent intent = new Intent(context,ReceiptItem.class);
                Bundle extras = new Bundle();
                Bitmap bit = mValues.get(position).GetImagePath();
                double amount = mValues.get(position).GetAmount();
                String cat = mValues.get(position).GetCategory();
                String TOP = mValues.get(position).GetTOP();
                intent.putExtra("Image", bit);
                extras.putDouble("amount", amount);
                extras.putString("cat", cat);
                extras.putString("TOP", TOP);
                extras.putInt("id",position);
                intent.putExtras(extras);
               // context.startActivity(intent);
//                System.out.println(amount + " " + cat + " " + TOP + "before act");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView AmountDisp;
        public final TextView CatDisp;
        public final ImageView ImPath;
        public final TextView TOP;
        public Purchase mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            AmountDisp = (TextView) view.findViewById(R.id.AmountDisp);
            CatDisp = (TextView) view.findViewById(R.id.CatDisp);
            ImPath = (ImageView) view.findViewById(R.id.ImPath);
            TOP = (TextView) view.findViewById(R.id.TOP);

        }

        @Override
        public String toString() {
            return super.toString() + " Amount: " + AmountDisp.getText() + " Category: " + CatDisp.getText()
                    + " ImPath: " + ImPath + " TOP: " + TOP.getText();
        }
    }
}