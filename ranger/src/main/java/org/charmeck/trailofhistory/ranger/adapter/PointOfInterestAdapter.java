package org.charmeck.trailofhistory.ranger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.charmeck.trailofhistory.ranger.DetailPointOfInterestActivity;
import org.charmeck.trailofhistory.ranger.R;
import org.charmeck.trailofhistory.ranger.model.PointOfInterest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Trey Robinson on 7/10/16.
 */
public class PointOfInterestAdapter extends RecyclerView.Adapter<PointOfInterestAdapter.ViewHolder> {
    private List<PointOfInterest> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case


        @BindView(R.id.poiName) TextView mTextView;

        private PointOfInterest pointOfInterest;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        public void bindPOI(PointOfInterest pointOfInterest){
            this.pointOfInterest = pointOfInterest;
            mTextView.setText(pointOfInterest.getName());
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            context.startActivity(DetailPointOfInterestActivity.newInstance(context, pointOfInterest));
        }

    }

    public PointOfInterestAdapter(List<PointOfInterest> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public PointOfInterestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_point_of_interest, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindPOI(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}