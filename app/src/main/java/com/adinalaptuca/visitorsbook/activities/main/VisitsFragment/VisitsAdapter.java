package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.swipeLayout.SwipeRevealLayout;
import com.adinalaptuca.visitorsbook.custom.swipeLayout.ViewBinderHelper;
import com.adinalaptuca.visitorsbook.model.Visit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.VisitViewHolder> {
    private List<Visit> listVisits;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    VisitsAdapter(List<Visit> listVisits) {
        this.listVisits = listVisits;
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public int getItemCount() {
        return listVisits.size();
    }

    @Override
    public VisitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VisitViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_visit, parent, false));
    }

    @Override
    public void onBindViewHolder(VisitViewHolder holder, int position) {
        holder.lblName.setText(listVisits.get(position).name());
        binderHelper.bind(holder.swipeLayout, listVisits.get(position).name());
    }

    class VisitViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipeLayout)
        SwipeRevealLayout swipeLayout;

        @BindView(R.id.lblDate)
        TextView lblDate;

        @BindView(R.id.lblName)
        TextView lblName;

        @BindView(R.id.lblTime)
        TextView lblTime;

        @BindView(R.id.lblTimeSpent)
        TextView lblTimeSpent;

        VisitViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btnCheckOut)
        public void btnCheckOut() {
            Log.e("asd", "btnCheckOut: " + getAdapterPosition());
        }
    }

}
