package pennypincher.cps496.cmich.edu.pennypincher;

/**
 * Created by noah on 11/20/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pennypincher.cps496.cmich.edu.pennypincher.PurchasesFragment.OnListFragmentInteractionListener;
import pennypincher.cps496.cmich.edu.pennypincher.dummy.PurchaseContent.PurchaseItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link pennypincher.cps496.cmich.edu.pennypincher.PurchaseContent.PurchaseItem} and makes a call to the
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.AmountDisp.setText(mValues.get(position).GetAmount().toString());
        holder.CatDisp.setText(mValues.get(position).GetCategory());
        holder.ImPath.setImageBitmap(mValues.get(position).GetImagePath());
        holder.TOP.setText(mValues.get(position).GetTOP());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                }
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