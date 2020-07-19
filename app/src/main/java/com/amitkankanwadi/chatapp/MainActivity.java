package com.amitkankanwadi.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static String EmployeeId = "employeeId";
    public static String EmployeeArbr = "employeeAbr";
    public static String EmployeeFname = "employeeFname";
    public static String EmployeeLname = "employeeLname";
    public static String EmployeeDesig = "employeeDesig";
    public static String EmployeeBdate = "employeeBdate";
    public static String EmployeeAdrs = "employeeAdrs";

    private ListView listView;
    private List<Employee> employeeList;
    Button addEmployeeButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        employeeList = new ArrayList<Employee>();
        addEmployeeButton = (Button) findViewById(R.id.addEmployeeButton);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Employees");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = employeeList.get(position);
                Intent intent = new Intent(MainActivity.this, EmployeeDetails.class);
                intent.putExtra(EmployeeId, employee.getEmployeeId());
                intent.putExtra(EmployeeArbr, employee.getAbbreviation());
                intent.putExtra(EmployeeFname, employee.getFirstName());
                intent.putExtra(EmployeeLname, employee.getLastName());
                intent.putExtra(EmployeeDesig, employee.getDesignation());
                intent.putExtra(EmployeeBdate, employee.getBirthday());
                intent.putExtra(EmployeeAdrs, employee.getAddress());
                startActivity(intent);
            }
        });

        // long click on data item to delete
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Employee employee = employeeList.get(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Employees").child(employee.getEmployeeId());
                                dref.removeValue();
                                Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });

        addEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmployeeDetails.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeeList.clear();
                for(DataSnapshot employeeSnapshot: dataSnapshot.getChildren()) {
                    Employee employee = employeeSnapshot.getValue(Employee.class);
                    employeeList.add(employee);
                }

                EmployeeList adapter = new EmployeeList(MainActivity.this, employeeList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
