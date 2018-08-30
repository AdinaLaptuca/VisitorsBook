package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.model.Employee;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {

    private List<Employee> listEmployees;
    private List<Employee> listSelectedEmployees = new ArrayList<>();

    EmployeesAdapter(List<Employee> listEmployees, List<Employee> listSelectedEmployees) {
        this.listEmployees = listEmployees;
        this.listSelectedEmployees = listSelectedEmployees;
    }

    @Override
    public int getItemCount() {
        return listEmployees.size();
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_2_items_detail, parent, false);

        ImageView btnIndicator = ((ImageView) cell.findViewById(R.id.btnIndicator));
        btnIndicator.setColorFilter(cell.getResources().getColor(R.color.gradientEnd));
        btnIndicator.setImageResource(R.drawable.checkmark);

        return new EmployeeViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        Employee employee = listEmployees.get(position);

        holder.txtTitle.setText(employee.getPerson().getFullName());
        holder.txtDetail.setText(employee.getEmployeeRole().getRoleName());

        holder.btnIndicator.setVisibility(listSelectedEmployees.contains(employee) ? View.VISIBLE : View.INVISIBLE);
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTitle)
        protected TextView txtTitle;

        @BindView(R.id.txtDetail)
        protected TextView txtDetail;

        @BindView(R.id.btnIndicator)
        protected ImageView btnIndicator;

        EmployeeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
