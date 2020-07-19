package com.amitkankanwadi.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EmployeeDetails extends AppCompatActivity {

    EditText firstName, lastName, designation, address;
    Button saveButton, updateButton;
    Spinner spinner;
    CalendarView calendarView;
    TextView birthdayText;

    String abr,bdate;
    static String id;
    //String abbr,fname,lname,desig,adrs;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        databaseReference = FirebaseDatabase.getInstance().getReference("Employees");

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        designation = (EditText) findViewById(R.id.designation);
        address = (EditText) findViewById(R.id.address);

        birthdayText = (TextView) findViewById(R.id.birthdayText);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        saveButton = (Button) findViewById(R.id.saveButton);
        updateButton = (Button) findViewById(R.id.updateButton);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmployeeDetails.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.names));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                abr = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                birthdayText.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                bdate = dayOfMonth + "/" + (month+1) + "/" + year;
            }
        });

        // save the details in firebase
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });

        Intent intent = getIntent();

        id = intent.getStringExtra(MainActivity.EmployeeId);
        String abbre = intent.getStringExtra(MainActivity.EmployeeArbr);
        //spinner.setSelection(abbre.indexOf(abbre));

        String firstname = intent.getStringExtra(MainActivity.EmployeeFname);
        firstName.setText(firstname);

        String lastname = intent.getStringExtra(MainActivity.EmployeeLname);
        lastName.setText(lastname);

        String work = intent.getStringExtra(MainActivity.EmployeeDesig);
        designation.setText(work);

        String birthdate = intent.getStringExtra(MainActivity.EmployeeBdate);
        birthdayText.setText(birthdate);

        String parts[] = birthdate.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        long calendarDate = calendar.getTimeInMillis();
        calendarView.setDate(calendarDate,true,true);

        String location = intent.getStringExtra(MainActivity.EmployeeAdrs);
        address.setText(location);

        // update the details in firebase
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abbr = spinner.getSelectedItem().toString();
                String fname = firstName.getText().toString();
                String lname = lastName.getText().toString();
                String desig = designation.getText().toString();
                String adrs = address.getText().toString();

                updateEmployee(id,abbr,fname,lname,desig,bdate,adrs);
            }
        });
    }

    public void updateEmployee(String empId, String abr, String fname, String lname, String desig, String dob, String adrs) {

        if((fname=="") || (lname=="")||(desig=="")||(bdate=="")||(adrs=="")) {
            Toast.makeText(this, "All entries are compulsary", Toast.LENGTH_SHORT).show();
        }
        else {
            Employee employee = new Employee(empId,abr,fname,lname,desig,bdate,adrs);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employees").child(id);
            databaseReference.setValue(employee);

            Toast.makeText(this, "Employee details updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(EmployeeDetails.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void addEmployee() {
        String abbr = spinner.getSelectedItem().toString();
        String fname = firstName.getText().toString();
        String lname = lastName.getText().toString();
        String desig = designation.getText().toString();
        String adrs = address.getText().toString();

        if((fname.equals(""))||(lname.equals(""))||(desig.equals(""))||(adrs.equals(""))||(bdate.equals(""))) {
            Toast.makeText(this, "All entries are compulsary", Toast.LENGTH_SHORT).show();
        }
        else {
            id = databaseReference.push().getKey();
            Employee employee = new Employee(id,abbr,fname,lname,desig,bdate,adrs);
            databaseReference.child(id).setValue(employee);
            Toast.makeText(this, "Employee added", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(EmployeeDetails.this, MainActivity.class);
            startActivity(intent);
        }
    }
}