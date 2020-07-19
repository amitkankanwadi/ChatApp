package com.amitkankanwadi.chatapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class EmployeeList extends ArrayAdapter<Employee> {

    private Activity context;
    private List<Employee> employeeList;
    static TextView abrText, fnameText, lnameText, desigText;

    public EmployeeList(Activity context, List<Employee> employeeList) {
        super(context, R.layout.employees_layout, employeeList);
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.employees_layout, parent, false);

//        TextView nameTextView = (TextView) listViewItem.findViewById(R.id.nameTextView);
//        TextView desigTextView = (TextView) listViewItem.findViewById(R.id.desigTextView);

        abrText = (TextView)  listViewItem.findViewById(R.id.abrText);
        fnameText = (TextView)  listViewItem.findViewById(R.id.fnameText);
        lnameText = (TextView)  listViewItem.findViewById(R.id.lnameText);
        desigText = (TextView)  listViewItem.findViewById(R.id.desigText);

        Employee employee = employeeList.get(position);
        abrText.setText(employee.getAbbreviation());
        fnameText.setText(employee.getFirstName());
        lnameText.setText(employee.getLastName());
        desigText.setText(employee.getDesignation());

        return listViewItem;
    }
}
