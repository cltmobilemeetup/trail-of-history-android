package org.charmeck.trailofhistory.poi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import org.charmeck.trailofhistory.R;
import org.charmeck.trailofhistory.model.PointOfInterest;

/**
 * Created by Trey Robinson on 3/8/16.
 */
public class PointOfInterestAdapter
    extends RecyclerView.Adapter<PointOfInterestAdapter.ViewHolder> {

  private List<PointOfInterest> pointOfInterestList;

  static class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.poiName) TextView name;
    @Bind(R.id.poiDescription) TextView description;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public PointOfInterestAdapter(List<PointOfInterest> pointOfInterestList) {
    this.pointOfInterestList = pointOfInterestList;
  }

  public void addPointOfIntrest(PointOfInterest pointOfInterest) {
    pointOfInterestList.add(pointOfInterest);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_point_of_interest, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.name.setText(pointOfInterestList.get(position).name);
    holder.description.setText(pointOfInterestList.get(position).description);
  }

  @Override public int getItemCount() {
    return pointOfInterestList.size();
  }
}