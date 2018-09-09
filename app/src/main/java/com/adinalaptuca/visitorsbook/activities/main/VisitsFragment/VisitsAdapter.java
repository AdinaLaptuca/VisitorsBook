package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ViewVisit.ViewVisitFragment;
import com.adinalaptuca.visitorsbook.custom.BaseActivityInterface;
import com.adinalaptuca.visitorsbook.custom.swipeLayout.SwipeRevealLayout;
import com.adinalaptuca.visitorsbook.custom.swipeLayout.ViewBinderHelper;
import com.adinalaptuca.visitorsbook.model.Visit;
import com.adinalaptuca.visitorsbook.utils.DateUtils;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.VisitViewHolder> {

    interface OnVisitActionListener {
        void doCheckin(Visit visit);
        void doCheckout(Visit visit);
        void viewVisit(Visit visit);

        void removeVisit(Visit visit);
    }

    private OnVisitActionListener mOnVisitActionListener;

    private List<Visit> listVisits;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();       // used to remember the swiped cells while scrolling
    private String previousDate = DateUtils.dateWithComponents(1970, 1, 1).toString();

    VisitsAdapter(List<Visit> listVisits, OnVisitActionListener listener) {
        this.mOnVisitActionListener = listener;
        this.listVisits = listVisits;
        binderHelper.setOpenOnlyOne(true);
    }

    void dataRefreshed() {
        notifyDataSetChanged();
        previousDate = DateUtils.dateWithComponents(1970, 1, 1).toString();
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
        Visit visit = listVisits.get(position);

        holder.lblName.setText(visit.getPerson().getFullName());
        binderHelper.bind(holder.swipeLayout, visit.getPerson().getFullName());         // save the swiped state of the cell

        holder.lblDate.setVisibility(DateUtils.timelessDate(visit.getTimeStart()).toString().equals(previousDate) ? View.INVISIBLE : View.VISIBLE);
        holder.lblDate.setText(DateUtils.dateToString(visit.getTimeStart(), Constants.DATE_FORMATTER_PATTERN_VISIT_DATE));
        previousDate = DateUtils.timelessDate(visit.getTimeStart()).toString();

        if (visit.getTimeEnd() != null)
            holder.lblTime.setText(String.format(Locale.getDefault(), "%s - %s",
                    DateUtils.dateToString(visit.getTimeStart(), Constants.DATE_FORMATTER_PATTERN_VISIT_TIME),
                    DateUtils.dateToString(visit.getTimeEnd(), Constants.DATE_FORMATTER_PATTERN_VISIT_TIME)));
        else
            holder.lblTime.setText(DateUtils.dateToString(visit.getTimeStart(), Constants.DATE_FORMATTER_PATTERN_VISIT_TIME));

        holder.imgCheckin.setVisibility(View.GONE);
        holder.imgCheckout.setVisibility(View.GONE);
        holder.lblTimeSpent.setVisibility(View.INVISIBLE);

        if (visit.getCheckin() != null) {
            holder.lblCheckout.setText(R.string.checkOut);

            holder.imgCheckin.setVisibility(View.VISIBLE);
            holder.lblTimeSpent.setVisibility(View.VISIBLE);

            if (visit.getCheckin().getTimeEnd() != null) {
                holder.lblCheckout.setText(R.string.viewVisit);
                holder.imgCheckout.setVisibility(View.VISIBLE);
                holder.lblTimeSpent.setText(String.format(Locale.getDefault(), "%s\n%s",
                        DateUtils.dateToString(visit.getCheckin().getTimeStart(), Constants.DATE_FORMATTER_PATTERN_VISIT_TIME),
                        DateUtils.dateToString(visit.getCheckin().getTimeEnd(), Constants.DATE_FORMATTER_PATTERN_VISIT_TIME)));
            }
            else
                holder.lblTimeSpent.setText(DateUtils.dateToString(visit.getCheckin().getTimeStart(), Constants.DATE_FORMATTER_PATTERN_VISIT_TIME));
        }
        else
            holder.lblCheckout.setText(R.string.checkIn);
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

        @BindView(R.id.imgCheckin)
        View imgCheckin;

        @BindView(R.id.imgCheckout)
        View imgCheckout;

        @BindView(R.id.lblTimeSpent)
        TextView lblTimeSpent;

        @BindView(R.id.lblCheckout)
        TextView lblCheckout;

        VisitViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btnCheckOut)
        public void btnCheckOut(View v) {
            Visit visit = listVisits.get(getAdapterPosition());

            if (visit.getCheckin() == null)
                mOnVisitActionListener.doCheckin(visit);
            else if (visit.getCheckin() != null && visit.getCheckin().getTimeEnd() == null)
                mOnVisitActionListener.doCheckout(visit);
            else
                mOnVisitActionListener.viewVisit(visit);
        }

        @OnClick(R.id.btnEdit)
        public void editClicked() {

        }

        @OnClick(R.id.btnRemove)
        public void removeClicked(View v) {
            new AlertDialog.Builder(v.getContext())
                    .setMessage(R.string.deleteVisitMessage)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.OK, ((dialogInterface, which) -> {
                        mOnVisitActionListener.removeVisit(listVisits.get(getAdapterPosition()));
                    }))
            .show();
        }
    }
}
