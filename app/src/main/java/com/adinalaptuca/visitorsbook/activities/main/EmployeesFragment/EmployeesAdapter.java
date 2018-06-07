package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.model.Employee;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {

    private List<Employee> listEmployees;

    EmployeesAdapter(List<Employee> listEmployees) {
        this.listEmployees = listEmployees;
    }

    @Override
    public int getItemCount() {
        return listEmployees.size();
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmployeeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_2_items_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        holder.txtTitle.setText(listEmployees.get(position).getFirstName() + listEmployees.get(position).getLastName());
        holder.txtDetail.setText(listEmployees.get(position).getFunction());
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTitle)
        protected TextView txtTitle;

        @BindView(R.id.txtDetail)
        protected TextView txtDetail;

        EmployeeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
