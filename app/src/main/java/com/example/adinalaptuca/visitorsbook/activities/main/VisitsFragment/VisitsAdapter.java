package com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adinalaptuca.visitorsbook.R;
import com.example.adinalaptuca.visitorsbook.model.Visit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.VisitViewHolder> {
    private List<Visit> listVisits;

    VisitsAdapter(List<Visit> listVisits) {
        this.listVisits = listVisits;
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
    }

    class VisitViewHolder extends RecyclerView.ViewHolder {

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
    }

}
