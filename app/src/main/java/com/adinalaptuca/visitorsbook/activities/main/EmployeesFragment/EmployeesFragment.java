package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.custom.CustomRecyclerView;
import com.adinalaptuca.visitorsbook.custom.OnItemSelectListener;
import com.adinalaptuca.visitorsbook.model.Employee;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EmployeesFragment extends BaseToolbarFragment implements EmployeesContract.View, AdapterView.OnItemClickListener {

    private EmployeesContract.Presenter presenter;

    private SearchView searchView;
    private MenuItem searchMenuItem;

    @BindView(R.id.tblData)
    protected CustomRecyclerView tblData;
    private EmployeesAdapter adapter;
    private List<Employee> listSelectedEmployees = new ArrayList<>();

    private OnItemSelectListener<List<Employee>> mOnItemSelectListener;

    @Override
    public String getToolbarTitle() {
        return getString(R.string.addParticipant);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_employees;
    }

    @Override
    protected void initView(View v) {
        presenter = new EmployeesPresenter(this);

        tblData.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblData.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        tblData.setItemClickListener(this);
        adapter = new EmployeesAdapter(presenter.getEmployees(), listSelectedEmployees);
        tblData.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        setHasOptionsMenu(true);
        setMenuVisibility(true);
//        getActivity().invalidateOptionsMenu();
//        toolbarActivityInterface.getToolbar().inflateMenu(R.menu.employees);
//
//        Menu menu = toolbarActivityInterface.getToolbar().getMenu();
//
//        searchMenuItem = menu.findItem(R.id.action_search);
//
//        searchView = (SearchView) searchMenuItem.getActionView();
//        searchView.setQueryHint(getResources().getString(R.string.search));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Log.e("emplouess", "onQueryTextChange");
////                presenter.setSearchString(newText);
//                return true;
//            }
//        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.employees, menu);
        super.onCreateOptionsMenu(menu, inflater);

        searchMenuItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.searchEmployees(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            TransitionManager.beginDelayedTransition((ViewGroup) getActivity().findViewById(R.id.toolbar));
            item.expandActionView();
//            MenuItemCompat.expandActionView(item);
            return true;
        }
        else if (id == R.id.action_done) {
            mOnItemSelectListener.onItemSelected(this, listSelectedEmployees);
            getActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.fetchEmployees();
    }

    @Override
    public void employeesFetched() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Employee selectedEmployee = presenter.getEmployees().get(i);

        if (listSelectedEmployees.contains(selectedEmployee))
            listSelectedEmployees.remove(selectedEmployee);
        else
            listSelectedEmployees.add(selectedEmployee);

        adapter.notifyDataSetChanged();
    }

    public void setOnItemSelectListener(OnItemSelectListener<List<Employee>> listener) {
        this.mOnItemSelectListener = listener;
    }
}
