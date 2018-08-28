package com.adinalaptuca.visitorsbook.activities.main.room.RoomsFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.model.Room;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomViewHolder> {

    private List<Room> listRooms;

    RoomsAdapter(List<Room> listRooms) {
        this.listRooms = listRooms;
    }

    @Override
    public int getItemCount() {
        return listRooms.size();
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RoomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_2_items_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        holder.txtTitle.setText(listRooms.get(position).getName());
        holder.txtDetail.setText(String.format(Locale.getDefault(), "%s %d",
                holder.txtDetail.getContext().getResources().getString(R.string.floor),
                listRooms.get(position).getFloor()));
    }

    class RoomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTitle)
        protected TextView txtTitle;

        @BindView(R.id.txtDetail)
        protected TextView txtDetail;

        public RoomViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
